package com.example.smartmakeupmirrorapp.repositories

import androidx.lifecycle.LiveData
import com.example.smartmakeupmirrorapp.entities.ShoppingItem

interface ShoppingInterfaces {
    fun upsert(item: ShoppingItem)
    fun delete(item: ShoppingItem)
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}