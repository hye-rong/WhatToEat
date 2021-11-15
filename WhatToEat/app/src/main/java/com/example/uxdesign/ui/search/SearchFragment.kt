package com.example.uxdesign.ui.search


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.uxdesign.R
import com.example.uxdesign.databinding.FragmentSearchBinding
import com.example.uxdesign.model.data.PlaceLoc
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), OnMapReadyCallback {
    lateinit var binding: FragmentSearchBinding
    private val placeViewModel: PlaceViewModel by activityViewModels()
    val scope = CoroutineScope(Main)

    private lateinit var map: GoogleMap
    private val defaultLocation = LatLng(33.4890, 126.4983)


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 사용자 위치 서비스
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback : LocationCallback
    var startupdate = false//위치 정보 갱신 했는지

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
        private val LOCATION_PERMISSION_REQUEST_CODE = 1
        private val LOCATION_SOURCE_SETTINGS_CODE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        subscribeObservers()
        initLocation()
        initMap()
        initBtn()

    }

    private fun subscribeObservers() {
        placeViewModel.loc.observe(viewLifecycleOwner){
            // loc이 변경되면 map을 옮기고, kakao검색 실행
            if(::map.isInitialized){
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 16.0f))
            }
            // 카카오 검색 바로 실행
            placeViewModel.getPlaceList(it)
        }
        placeViewModel.placeList.observe(viewLifecycleOwner){
            addMarkerInList(it)
        }
    }
    private fun initBtn(){
        binding.editText.apply {
            /*setOnClickListener {
                placeViewModel.getLocation(binding.editText.text.toString())
                Log.d(TAG, "click" + binding.editText.text.toString())
            }*/
            setOnKeyListener { v, keyCode, event ->
                if(event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                    placeViewModel.getLocation(binding.editText.text.toString())
                    Log.d(TAG, "onKey" + binding.editText.text.toString())
                    val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.hideSoftInputFromWindow(this.windowToken, 0) //hide keyboard

                    true
                }
                else {
                    false
                }
            }
        }

    }


    override fun onMapReady(p0: GoogleMap) {
        map = p0
        if (placeViewModel.loc.value == null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 16.0f))
        }else{
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(placeViewModel.loc.value!!, 16.0f))
        }
        if (placeViewModel.placeList.value != null){
            addMarkerInList(placeViewModel.placeList.value!!)
        }
        map.setMinZoomPreference(10.0f)
        map.setMaxZoomPreference(18.0f)
        Log.d(TAG, "onMapReady")
    }
    private fun addMarkerInList(list: List<PlaceLoc>){
        if(::map.isInitialized){
            scope.launch {
                map.clear()
                for(l in list){
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(l.y.toDouble(), l.x.toDouble()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                            .title(l.place_name)
                            .snippet(l.phone)
                    )
                }
            }
        }

        //mk.showInfoWindow() // snippet 정보 바로 출력
    }

    private fun initMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Log.d(TAG, "initMap")
    }

    // 사용자의 위치 정보를 알아오기 위한 사전 작업
    private fun initLocation(){
        fusedLocationProviderClient = FusedLocationProviderClient(requireActivity()) // 현재 위치를 콜백받음
        // 매개변수 설정
        // 위치 업데이트를 위한 요청
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        // 위치 업데이트를 위한 콜백함수
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                // 위치 정보가 이용 가능하면 호출되는 함수
                if(p0.locations.size == 0) return
                val locations = p0.locations
                placeViewModel.loc.value = LatLng(
                    locations[locations.size - 1].latitude,
                    locations[locations.size - 1].longitude
                )
                stopLocationUpdate()
            }
        }

    }

    private fun startLocationUpdates(){
        // 위치 정보를 가져올 수 있는지 권한 확인
        if(ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            // 권한이 없는 경우에 권한 요청 작업 수행
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            ) //
        }
        else {
            // 위치 권한이 있는 경우에는 위치 정보 가져오는 작업 수행
            // gps가 켜져있는지 확인
            if(!checkLocationServicesStatus()){
                // gps가 꺼져있는 경우 켜는 gps 재설정
                showLocationServicesSetting()
            } else {
                startupdate = true
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )
                Log.d(TAG, "startLocationUpdates")
            }

        }
    }
    private fun stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate = false
        Log.i(TAG, "stopLocationUpdate")
    }
    private fun checkLocationServicesStatus():Boolean{ // Gps 사용가능한지 체크
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }
    private fun showLocationServicesSetting(){ // Gps 설정
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 허용하시겠습니까?"
        )
        builder.setPositiveButton("설정") { dialog, which ->
            val gpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(gpsSettingIntent, LOCATION_SOURCE_SETTINGS_CODE)
        }
        builder.setNegativeButton("취소"){ dialog, which ->
                dialog.dismiss()
                //기본 위치로..
                setCurrentLocation(defaultLocation)
            }
        builder.create().show()
    }
    private fun setCurrentLocation(location: LatLng):Boolean{
        // 카메라 이동
        if(::map.isInitialized){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f))

            return true
        }
        else{
            return false
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            LOCATION_SOURCE_SETTINGS_CODE -> {
                if (checkLocationServicesStatus()) {
                    startLocationUpdates()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocationUpdates()
            }
            else{
                // 기본 설정된 위치로
                setCurrentLocation(defaultLocation)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        if(!startupdate)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        stopLocationUpdate()
    }
}