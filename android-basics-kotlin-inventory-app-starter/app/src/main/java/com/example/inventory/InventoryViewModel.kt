package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(name: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = name,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt(),
        )
    }

    fun addNewItem(name: String, itemPrice: String, itemCount: String) {
        insertItem(getNewItemEntry(name, itemPrice, itemCount))
    }

    fun isEntryValid(name: String, itemPrice: String, itemCount: String): Boolean {
        if (name.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
