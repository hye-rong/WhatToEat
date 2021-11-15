package com.example.uxdesign.model.data

data class ResultLocation(
    var meta: LocMeta,
    var documents: List<LocData>
)
data class LocMeta(
    var total_count: Int,               // 검색어에 검색된 문서 수
    var pageable_count: Int,            // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
    var is_end: Boolean
)

data class LocData(
    var address_name: String,
    var y: String,
    var x: String,
    var address_type: String,
    var address: Address,
    var road_address: RoadAddress
)

data class Address(
    var address_name: String,
    var region_1depth_name: String,
    var region_2depth_name:String,
    var region_3depth_name:String,
    var region_3depth_h_name:String,
    var h_code:String,
    var b_code: String,
    var mountain_yn: String,
    var main_address_no: String,
    var sub_address_no: String,
    var x: String,
    var y: String
)

data class RoadAddress(
    var address_name: String,
    var region_1depth_name: String,
    var region_2depth_name:String,
    var region_3depth_name:String,
    var road_name:String,
    var underground_yn:String,
    var main_building_no: String,
    var sub_building_no: String,
    var building_name: String,
    var zone_no: String,
    var y: String,
    var x: String
)