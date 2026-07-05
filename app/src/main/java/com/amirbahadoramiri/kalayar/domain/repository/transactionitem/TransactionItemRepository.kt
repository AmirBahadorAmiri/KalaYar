package com.amirbahadoramiri.kalayar.domain.repository.transactionitem

import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

class TransactionItemRepository(private val transactionItemDataSource: TransactionItemDataSource) {

    suspend fun addItem(transactionItem: TransactionItem) = transactionItemDataSource.addItem(transactionItem)
    suspend fun addItems(transactionItems: List<TransactionItem>) = transactionItemDataSource.addItems(transactionItems)
    suspend fun deleteItem(transactionItem: TransactionItem) = transactionItemDataSource.deleteItem(transactionItem)
    suspend fun deleteItems(transactionItems: List<TransactionItem>) = transactionItemDataSource.deleteItems(transactionItems)
    suspend fun deleteItems(transaction_id: Long) = transactionItemDataSource.deleteItems(transaction_id)
    suspend fun updateItem(transactionItem: TransactionItem) = transactionItemDataSource.updateItem(transactionItem)
    suspend fun updateItems(transactionItems: List<TransactionItem>) = transactionItemDataSource.updateItems(transactionItems)
    suspend fun getItem(item_id: Long) = transactionItemDataSource.getItem(item_id)
    suspend fun getItems(transaction_id: Long) = transactionItemDataSource.getItems(transaction_id)

}