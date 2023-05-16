package tn.farah.smartmakeupapp.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class ProductViewModel() :ViewModel(){
    private val _products= MutableLiveData<List<Product>>()
val products:LiveData<List<Product>> = _products
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val listResult = ProductRepo.apiService.getProducts()
                _products.value
            } catch (e: Exception) {
                _products.value
            }
        }
    }
}





