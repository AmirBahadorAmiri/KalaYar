package com.amirbahadoramiri.kalayar.domain.repository.store

import com.amirbahadoramiri.kalayar.domain.models.Store

class StoreRepository(private val storeDataSource: StoreDataSource) {

    suspend fun addStore(store: Store) = storeDataSource.addStore(store)
    suspend fun deleteStore(store: Store) = storeDataSource.deleteStore(store)
    suspend fun updateStore(store: Store) = storeDataSource.updateStore(store)
    suspend fun getStore() = storeDataSource.getStore()

}