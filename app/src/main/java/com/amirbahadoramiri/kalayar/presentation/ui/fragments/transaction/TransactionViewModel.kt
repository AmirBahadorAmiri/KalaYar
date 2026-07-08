package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionItemDataSource
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionRepository
import com.amirbahadoramiri.kalayar.domain.repository.transactionitem.TransactionItemRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository(RoomTransactionDataSource(application))
    val transactionItemRepository = TransactionItemRepository(RoomTransactionItemDataSource(application))

    val getAllTransactionLiveData = MutableLiveData<MutableList<Transaction>>()

    fun getAllTransactions(limit_count: Int) {
        viewModelScope.launch {
            transactionRepository.getAllTransactions(limit_count).let {
                getAllTransactionLiveData.postValue(it.toMutableList().asReversed())
            }
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