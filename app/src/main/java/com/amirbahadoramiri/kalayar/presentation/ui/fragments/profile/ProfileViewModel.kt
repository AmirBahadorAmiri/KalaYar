package com.amirbahadoramiri.kalayar.presentation.ui.fragments.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomStoreDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomUserDataSource
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.User
import com.amirbahadoramiri.kalayar.domain.repository.store.StoreRepository
import com.amirbahadoramiri.kalayar.domain.repository.user.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    val storeRepository = StoreRepository(RoomStoreDataSource(application))
    val userRepository = UserRepository(RoomUserDataSource(application))

    val storeLiveData = MutableLiveData<Store>()
    val userLiveData = MutableLiveData<User>()

    fun getStore() {
        viewModelScope.launch {
            storeLiveData.postValue(storeRepository.getStore())
        }
    }

    fun updateStore(store: Store) {
        viewModelScope.launch {
            storeRepository.updateStore(store)
            storeLiveData.postValue(store)
        }
    }

    fun checkPassword() {
        viewModelScope.launch {
            userLiveData.postValue(userRepository.getUser())
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
            userLiveData.postValue(user)
        }
    }

}