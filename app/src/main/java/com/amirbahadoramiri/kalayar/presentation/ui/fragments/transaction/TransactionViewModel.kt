package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomStoreDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionItemDataSource
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.domain.repository.store.StoreRepository
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionRepository
import com.amirbahadoramiri.kalayar.domain.repository.transactionitem.TransactionItemRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository(RoomTransactionDataSource(application))
    val transactionItemRepository = TransactionItemRepository(RoomTransactionItemDataSource(application))
    val storeRepository = StoreRepository(RoomStoreDataSource(application))

    val getAllTransactionLiveData = MutableLiveData<MutableList<Transaction>>()
    val getTransactionItemsLiveData = MutableLiveData<List<TransactionItem>>()
    val getStoreLiveData = MutableLiveData<Store?>()

    fun getAllTransactions() {
        viewModelScope.launch {
            transactionRepository.getAllTransactions().let {
                getAllTransactionLiveData.postValue(it.toMutableList().asReversed())
            }
        }
    }

    fun getTransactionItems(transaction_id: Long) {
        viewModelScope.launch {
            transactionItemRepository.getItems(transaction_id).let {
                getTransactionItemsLiveData.postValue(it)
            }
        }
    }

    fun getStore() {
        viewModelScope.launch {
            val store = storeRepository.getStore()
            getStoreLiveData.postValue(store)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transaction)
            getAllTransactionLiveData.value?.remove(transaction)
        }
    }

    fun removeTransactionItems(transaction: Transaction) {
        viewModelScope.launch {
            transactionItemRepository.deleteItems(transaction.transaction_id)
        }
    }

}