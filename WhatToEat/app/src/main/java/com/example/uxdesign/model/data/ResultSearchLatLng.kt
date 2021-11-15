package com.example.uxdesign.model.data

data class ResultSearchLatLng(
    var meta: PlaceMetaLoc,                // 장소 메타데이터
    var documents: List<PlaceLoc>          // 검색 결과
)

data class PlaceMetaLoc(
    var same_name: RegionInfo,          // 질의어의 지역 및 키워드 분석 정보
    var pageable_count: Int,            // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
    var total_count: Int,               // 검색어에 검색된 문서 수
    var is_end: Boolean                 // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
)

data class PlaceLoc(
    var place_name: String,             // 장소명, 업체명
    var distance: String,                // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
    var place_url: String,              // 장소 상세페이지 URL
    var category_name: String,          // 카테고리 이름
    var address_name: String,           // 전체 지번 주소
    var road_address_name: String,      // 전체 도로명 주소
    var id: String,                     // 장소 ID
    var phone: String,                  // 전화번호
    var category_group_code: String,    // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
    var category_group_name: String,    // 중요 카테고리만 그룹핑한 카테고리 그룹명
    var x: String,                      // X 좌표값 혹은 longitude
    var y: String,                      // Y 좌표값 혹은 latitude
)