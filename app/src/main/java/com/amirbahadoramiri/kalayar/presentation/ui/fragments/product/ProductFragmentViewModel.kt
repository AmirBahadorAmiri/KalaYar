package com.amirbahadoramiri.kalayar.presentation.ui.fragments.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomProductDataSource
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.repository.product.ProductRepository
import kotlinx.coroutines.launch

class ProductFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ProductRepository(RoomProductDataSource(application))
    val getAllProductLiveData = MutableLiveData<MutableList<Product>>()

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(product).let {
                product.product_id = it
            }
        }
    }

    fun getAllProduct() {
        viewModelScope.launch {
            repository.getAllProducts().let {
                getAllProductLiveData.postValue(it.toMutableList().asReversed())
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            getAllProductLiveData.value?.remove(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

}