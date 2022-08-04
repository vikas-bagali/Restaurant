package com.demo.restaurant.model.menuData

import com.google.gson.annotations.SerializedName


data class MenuData (

  @SerializedName("menus" ) var menus : ArrayList<Menus> = arrayListOf()

)