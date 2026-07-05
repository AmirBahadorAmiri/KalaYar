package com.amirbahadoramiri.kalayar.presentation.ui.fragments.money

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomProductDataSource
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.repository.product.ProductRepository
import kotlinx.coroutines.launch

class MoneyFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ProductRepository(RoomProductDataSource(application))
    val getAllProductLiveData = MutableLiveData<MutableList<Product>>()

    fun getAllProduct() {
        viewModelScope.launch {
            repository.getAllProducts().let {
                getAllProductLiveData.postValue(it.toMutableList())
            }
        }
    }

}