package com.amirbahadoramiri.kalayar.domain.repository.store

import com.amirbahadoramiri.kalayar.domain.models.Store

interface StoreDataSource {

    suspend fun addStore(store: Store): Long?
    suspend fun deleteStore(store: Store)
    suspend fun updateStore(store: Store)
    suspend fun getStore(): Store?

}