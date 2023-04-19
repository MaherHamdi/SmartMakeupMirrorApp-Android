package tn.farah.smartmakeupapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import  tn.farah.smartmakeupapp.R
import tn.farah.smartmakeupapp.data.models.Category

class CategoryAdapter(private val categories: ArrayList<Category>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>()  {


    var onItemClick : ((Category)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView :TextView= itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            textView.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    holder.itemView.setOnClickListener {
        onItemClick?.invoke(category)
    }

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}