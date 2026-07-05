package com.amirbahadoramiri.kalayar.domain.repository.transaction

import com.amirbahadoramiri.kalayar.domain.models.Transaction

interface TransactionDataSource {

    suspend fun addTransaction(transaction: Transaction): Long?
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun getTransaction(id: Long): Transaction?
    suspend fun getAllTransactions(limit_count: Int): List<Transaction>
    suspend fun getAllTransactions(): List<Transaction>

}