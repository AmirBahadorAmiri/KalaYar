package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.repository.store.StoreDataSource

class RoomStoreDataSource(context: Context): StoreDataSource {

    val publicDao = PublicDatabase.getPublicDatabase(context).getPublicDAO()

    override suspend fun addStore(store: Store) = publicDao.addStore(store)

    override suspend fun deleteStore(store: Store) = publicDao.deleteStore(store)

    override suspend fun updateStore(store: Store) = publicDao.updateStore(store)

    override suspend fun getStore() = publicDao.getStore()

}