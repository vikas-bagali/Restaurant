package com.demo.restaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.restaurant.adapter.SearchAdapter
import com.demo.restaurant.databinding.ActivityMainBinding
import com.demo.restaurant.model.RestaurantData
import com.demo.restaurant.model.Restaurants
import com.demo.restaurant.model.menuData.MenuData
import com.demo.restaurant.utils.Helper
import com.google.gson.Gson
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private var restaurantData: RestaurantData? = null
    private var menuData: MenuData? = null

    private lateinit var recyclerview: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var recyclerViewContainer: ConstraintLayout
    private lateinit var noResultFoundContainer: ConstraintLayout
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var close: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        //init
        searchEditText = viewBinding.searchEditText
        recyclerViewContainer = viewBinding.recyclerViewContainer
        noResultFoundContainer = viewBinding.noResultContainer
        recyclerview = viewBinding.recyclerView
        close = viewBinding.close


        /*Read json files*/
        readLocalJsonData("menus.json")
        readLocalJsonData("restaurant.json")

        recyclerViewContainer.visibility = View.GONE


        /*layout Manager*/
        recyclerview.layoutManager = LinearLayoutManager(this)

        //Adapter
        val list: ArrayList<Restaurants> = ArrayList()
        searchAdapter = SearchAdapter(list)

        /*Setting the Adapter with the recyclerview*/
        recyclerview.adapter = searchAdapter


        /*Click Listeners*/
        /*Text Changes Listener*/
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(charSequence: Editable) {
                performSearch(charSequence.toString(), 1)
            }
        })

        searchEditText.setOnEditorActionListener(OnEditorActionListener { textView, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                Helper.dismissKeyboard(this)
            } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(textView.text.toString(), 1)
                Helper.dismissKeyboard(this)
                return@OnEditorActionListener true
            }
            false
        })

        close.setOnClickListener {
            recyclerViewContainer.isVisible = false
            viewBinding.searchEditText.text.clear()
        }


    }


    /*perform search for the text entered in the edittext*/

    fun performSearch(keyword: String, pos: Int) {
        recyclerViewContainer.visibility = View.VISIBLE
        if (keyword.trim { it <= ' ' }.length > pos) {
            close.visibility = View.VISIBLE
            noResultFoundContainer.visibility = View.GONE
            val mList: ArrayList<Restaurants> = ArrayList()
            if (restaurantData != null) {
                val arrayList: ArrayList<Restaurants> = restaurantData!!.restaurants
                for (res: Restaurants in arrayList) {
                    if (res.name?.lowercase()
                            ?.contains(keyword.lowercase()) == true || res.cuisineType?.lowercase()
                            ?.contains(keyword.lowercase()) == true
                    ) {
                        mList.add(res)
                    }
                }
            }
            Log.d("Test", "performSearch: after $mList")
            searchAdapter.updateSearchItems(mList)
        } else if (keyword.isEmpty()) {
            close.isVisible = false
            recyclerViewContainer.visibility = View.GONE
            //noResultFoundContainer.visibility = View.VISIBLE
        }
    }


    private fun readLocalJsonData(file: String) {
        val jsonFileString: String? = getJsonDataFromAsset(applicationContext, file)
        if (jsonFileString != null) {
            Log.i("Test", jsonFileString)
        }
        val gson = Gson()
        if (file == "menus.json") {
            menuData = gson.fromJson(jsonFileString, MenuData::class.java)
        } else {
            restaurantData = gson.fromJson(jsonFileString, RestaurantData::class.java)
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}