package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionDataSource
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val repository = TransactionRepository(RoomTransactionDataSource(application))
    val getAllTransactionLiveData = MutableLiveData<MutableList<Transaction>>()

    fun getAllTransactions(limit_count: Int) {
        viewModelScope.launch {
            repository.getAllTransactions(limit_count).let {
                getAllTransactionLiveData.postValue(it.toMutableList().asReversed())
            }
        }
    }

}