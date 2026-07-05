package com.amirbahadoramiri.kalayar.domain.repository.product

import com.amirbahadoramiri.kalayar.domain.models.Product

interface ProductDataSource {

    suspend fun addProduct(product: Product): Long?
    suspend fun getProduct(id: Long): Product?
    suspend fun getAllProducts(): List<Product>
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun updateProducts(products: List<Product>)

}