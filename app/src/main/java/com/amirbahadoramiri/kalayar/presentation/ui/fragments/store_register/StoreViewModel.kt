package com.amirbahadoramiri.kalayar.presentation.ui.fragments.store_register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomStoreDataSource
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.repository.store.StoreRepository
import kotlinx.coroutines.launch

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    val storeIsSaved = MutableLiveData<Boolean>()
    val repository = StoreRepository(RoomStoreDataSource(application))

    fun saveUser(store: Store) {
        viewModelScope.launch {
            repository.addStore(store)
            storeIsSaved.postValue(true)
        }
    }

}