package com.demo.restaurant.model

import com.google.gson.annotations.SerializedName


data class RestaurantData (

  @SerializedName("restaurants" ) var restaurants : ArrayList<Restaurants> = arrayListOf()

)