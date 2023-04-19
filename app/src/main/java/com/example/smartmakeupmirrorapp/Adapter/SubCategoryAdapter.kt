package com.example.smartmakeupmirrorapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmakeupmirrorapp.Models.SubCategory
import com.example.smartmakeupmirrorapp.R

class SubCategoryAdapter(private val subCategories: ArrayList<SubCategory>): RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView : TextView = itemView.findViewById(R.id.categoryNameee)

        fun bind(subCategory: SubCategory) {
            textView.text = subCategory.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_white, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subCategory = subCategories[position]
        holder.bind(subCategory)
    }

    override fun getItemCount(): Int {
        return  subCategories.size
    }
}