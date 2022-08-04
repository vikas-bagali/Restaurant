package com.demo.restaurant.model.menuData

import com.google.gson.annotations.SerializedName


data class Categories (

  @SerializedName("id"         ) var id         : String?               = null,
  @SerializedName("name"       ) var name       : String?               = null,
  @SerializedName("menu-items" ) var menuitems : ArrayList<MenuItems> = arrayListOf()

)