package com.amirbahadoramiri.kalayar.domain.repository.transactionitem

import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

interface TransactionItemDataSource {

    suspend fun addItem(transactionItem: TransactionItem): Long?
    suspend fun addItems(transactionItems: List<TransactionItem>)
    suspend fun deleteItem(transactionItem: TransactionItem)
    suspend fun deleteItems(transactionItems: List<TransactionItem>)
    suspend fun deleteItems(transaction_id: Long)
    suspend fun updateItem(transactionItem: TransactionItem)
    suspend fun updateItems(transactionItems: List<TransactionItem>)
    suspend fun getItem(item_id: Long): TransactionItem?
    suspend fun getItems(transaction_id: Long): List<TransactionItem>
    suspend fun getAllTransactionItems(): List<TransactionItem>

}