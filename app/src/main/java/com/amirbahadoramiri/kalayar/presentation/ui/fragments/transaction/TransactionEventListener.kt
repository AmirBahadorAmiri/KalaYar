package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import com.amirbahadoramiri.kalayar.domain.models.Transaction

interface TransactionEventListener {

    fun onShowTransaction(transaction: Transaction, position: Int)
    fun onRemoveTransaction(transaction: Transaction, position: Int)
    fun onPrintTransaction(transaction: Transaction, position: Int)
    fun onUpdateTransaction(transaction: Transaction, position: Int)
    fun onAddTransaction(transaction: Transaction, position: Int)

}