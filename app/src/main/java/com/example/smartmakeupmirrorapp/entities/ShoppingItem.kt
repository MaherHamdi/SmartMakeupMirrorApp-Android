package com.example.smartmakeupmirrorapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @ColumnInfo(name = "item_name")
    var name: String,
    @ColumnInfo(name = "item_quantity")
    var quantity: Int,
    @ColumnInfo(name = "item_price")
    var price: Int,
    @ColumnInfo(name = "item_image")
    var image: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}