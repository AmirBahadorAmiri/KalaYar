package com.amirbahadoramiri.kalayar.presentation.ui.fragments.product

import com.amirbahadoramiri.kalayar.domain.models.Product

interface ProductEventListener {

    fun onShowProduct(product: Product, position: Int)
    fun onRemoveProduct(product: Product, position: Int)
    fun onUpdateProduct(product: Product, position: Int)
    fun onAddProduct(product: Product, position: Int)

}