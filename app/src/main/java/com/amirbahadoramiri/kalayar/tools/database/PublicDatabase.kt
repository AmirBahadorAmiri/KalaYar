package com.amirbahadoramiri.kalayar.tools.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirbahadoramiri.kalayar.models.Company
import com.amirbahadoramiri.kalayar.models.Inventory
import com.amirbahadoramiri.kalayar.models.Product
import com.amirbahadoramiri.kalayar.models.Store
import com.amirbahadoramiri.kalayar.models.StoreKeeper
import com.amirbahadoramiri.kalayar.models.Transaction
import com.amirbahadoramiri.kalayar.models.TransactionItems

@Database(
    version = 1,
    exportSchema = false,
    entities = [Company::class, Inventory::class, Product::class, Store::class, StoreKeeper::class, Transaction::class, TransactionItems::class]
)
abstract class PublicDatabase : RoomDatabase() {

    companion object {

        private var publicDatabase: PublicDatabase? = null

        fun getPublicDatabase(context: Context): PublicDatabase? {
            if (publicDatabase == null) {
                publicDatabase =
                    Room.databaseBuilder(context, PublicDatabase::class.java, "public-db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration(false)
                        .build()
            }
            return publicDatabase;
        }
    }

    abstract fun getPublicDAO(): PublicDAO

}
