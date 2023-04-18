package tn.farah.smartmakeupapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tn.farah.smartmakeupapp.data.models.Category
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.farah.smartmakeupapp.data.repo.CategoryApiService
import tn.farah.smartmakeupapp.data.repo.CategoryRepo

class CategoryViewModel(private val categoryRepository: CategoryRepo) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    /*init {
        viewModelScope.launch {
            _categories.value = categoryRepository.getCategories()
        }
    }*/
}

