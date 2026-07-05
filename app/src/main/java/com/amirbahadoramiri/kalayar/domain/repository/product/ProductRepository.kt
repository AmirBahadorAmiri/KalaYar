package com.amirbahadoramiri.kalayar.domain.repository.product

import com.amirbahadoramiri.kalayar.domain.models.Product

class ProductRepository(private val productDataSource: ProductDataSource) {

    suspend fun addProduct(product: Product) = productDataSource.addProduct(product)
    suspend fun getProduct(id: Long) = productDataSource.getProduct(id)
    suspend fun getAllProducts() = productDataSource.getAllProducts()
    suspend fun deleteProduct(product: Product) = productDataSource.deleteProduct(product)
    suspend fun updateProduct(product: Product) = productDataSource.updateProduct(product)
    suspend fun updateProducts(products: List<Product>) = productDataSource.updateProducts(products)

}