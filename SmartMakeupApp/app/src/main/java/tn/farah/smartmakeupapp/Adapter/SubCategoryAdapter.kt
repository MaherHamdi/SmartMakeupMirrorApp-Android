package tn.farah.smartmakeupapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.farah.smartmakeupapp.R
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.SubCategory

class SubCategoryAdapter(private val subCategories: List<SubCategory>): RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView : TextView = itemView.findViewById(R.id.categoryName)

        fun bind(subCategory: SubCategory) {
            textView.text = subCategory.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category, parent, false)
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