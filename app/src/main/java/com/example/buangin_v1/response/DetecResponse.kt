package com.example.buangin_v1.response

import com.google.gson.annotations.SerializedName

//data class DetecResponse(
//
//	@field:SerializedName("result")
//	val result: List<ResultItem?>? = null,
//	val image: String?
//)
//
//data class ResultItem(
//
//	@field:SerializedName("ymin")
//	val ymin: Any? = null,
//
//	@field:SerializedName("xmin")
//	val xmin: Any? = null,
//
//	@field:SerializedName("ymax")
//	val ymax: Any? = null,
//
//	@field:SerializedName("xmax")
//	val xmax: Any? = null,
//
//	@field:SerializedName("confidence")
//	val confidence: Any? = null,
//
//	@field:SerializedName("name")
//	val name: String? = null,
//
//	@field:SerializedName("class")
//	val jsonMemberClass: Int? = null
//)
data class DetecResponse(
	val result: List<ResultItem>?,
	val image: String?
)

data class ResultItem(
	val name: String?
)