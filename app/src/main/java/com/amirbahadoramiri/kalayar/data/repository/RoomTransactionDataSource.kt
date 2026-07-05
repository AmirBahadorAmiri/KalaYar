package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionDataSource

class RoomTransactionDataSource(context: Context) : TransactionDataSource {

    val publicDao = PublicDatabase.getPublicDatabase(context).getPublicDAO()

    override suspend fun addTransaction(transaction: Transaction) = publicDao.addTransaction(transaction)

    override suspend fun deleteTransaction(transaction: Transaction) = publicDao.deleteTransaction(transaction)

    override suspend fun updateTransaction(transaction: Transaction) = publicDao.updateTransaction(transaction)

    override suspend fun getTransaction(id: Long) = publicDao.getTransaction(id)

    override suspend fun getAllTransactions() = publicDao.getAllTransaction()
}