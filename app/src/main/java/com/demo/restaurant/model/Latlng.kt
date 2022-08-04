package com.demo.restaurant.model

import com.google.gson.annotations.SerializedName


data class Latlng (

  @SerializedName("lat" ) var lat : Double? = null,
  @SerializedName("lng" ) var lng : Double? = null

)