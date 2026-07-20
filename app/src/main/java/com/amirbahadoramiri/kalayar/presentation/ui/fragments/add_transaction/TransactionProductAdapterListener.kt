package com.amirbahadoramiri.kalayar.presentation.ui.fragments.add_transaction

import com.amirbahadoramiri.kalayar.domain.models.Product

interface TransactionProductAdapterListener {
    fun onCheck(checkedId: Int, isChecked: Boolean)
    fun onClickListener(product: Product, position: Int)
    fun onLongClickListener(product: Product, position: Int)
    fun onRemoveClickListener(product: Product, position: Int)
}