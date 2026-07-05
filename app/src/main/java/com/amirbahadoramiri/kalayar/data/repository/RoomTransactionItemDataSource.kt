package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.domain.repository.transactionitem.TransactionItemDataSource

class RoomTransactionItemDataSource(context: Context) : TransactionItemDataSource {

    val publicDao = PublicDatabase.getPublicDatabase(context).getPublicDAO()

    override suspend fun addItem(transactionItem: TransactionItem) = publicDao.addTransactionItem(transactionItem)

    override suspend fun addItems(transactionItems: List<TransactionItem>) = publicDao.addTransactionItems(transactionItems)

    override suspend fun deleteItem(transactionItem: TransactionItem) = publicDao.deleteTransactionItem(transactionItem)

    override suspend fun deleteItems(transactionItems: List<TransactionItem>) = publicDao.deleteTransactionItems(transactionItems)

    override suspend fun deleteItems(transaction_id: Long) = publicDao.deleteTransactionItems(transaction_id)

    override suspend fun updateItem(transactionItem: TransactionItem) = publicDao.updateTransactionItem(transactionItem)

    override suspend fun updateItems(transactionItems: List<TransactionItem>) = publicDao.updateTransactionItems(transactionItems)

    override suspend fun getItem(item_id: Long) = publicDao.getTransactionItem(item_id)

    override suspend fun getItems(transaction_id: Long) = publicDao.getTransactionItems(transaction_id)

    override suspend fun getAllTransactionItems() = publicDao.getAllTransactionItems()

}