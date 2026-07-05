package com.amirbahadoramiri.kalayar.domain.repository.transaction

import com.amirbahadoramiri.kalayar.domain.models.Transaction

class TransactionRepository(private val transactionDataSource: TransactionDataSource) {

    suspend fun addTransaction(transaction: Transaction) = transactionDataSource.addTransaction(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDataSource.deleteTransaction(transaction)
    suspend fun updateTransaction(transaction: Transaction) = transactionDataSource.updateTransaction(transaction)
    suspend fun getTransaction(id: Long) = transactionDataSource.getTransaction(id)
    suspend fun getAllTransactions() = transactionDataSource.getAllTransactions()

}