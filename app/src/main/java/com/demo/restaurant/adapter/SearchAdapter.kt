package com.demo.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.restaurant.R
import com.demo.restaurant.model.Restaurants
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class SearchAdapter(private var mListItems: List<Restaurants>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = mListItems[position]
        holder.textView.text = restaurant.name
        Picasso.get().load(restaurant.photograph).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return mListItems.size
    }

    fun updateSearchItems(items: List<Restaurants>) {
        mListItems = items
        notifyDataSetChanged()
    }

    // View Holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val imageView: CircularImageView = itemView.findViewById(R.id.imageView)

    }


    interface SearchItemClickListener {
        fun onClickSearchItem(item: String?)
    }

    private var listener: SearchItemClickListener? = null

    fun setOnSearchItemClickListner(listener: SearchItemClickListener?) {
        this.listener = listener
    }


}