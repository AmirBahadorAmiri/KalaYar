package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.repository.product.ProductDataSource

class RoomProductDataSource(context: Context) : ProductDataSource {

    val publicDao = PublicDatabase.getPublicDatabase(context).getPublicDAO()

    override suspend fun addProduct(product: Product) = publicDao.addProduct(product)

    override suspend fun getProduct(id: Long) = publicDao.getProduct(id)

    override suspend fun getAllProducts() = publicDao.getAllProduct()

    override suspend fun deleteProduct(product: Product) = publicDao.deleteProduct(product)

    override suspend fun updateProduct(product: Product) = publicDao.updateProduct(product)

    override suspend fun updateProducts(products: List<Product>) = publicDao.updateProducts(products)
}