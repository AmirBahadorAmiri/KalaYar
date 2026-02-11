package com.amirbahadoramiri.anbarplus.tools.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirbahadoramiri.anbarplus.models.Company
import com.amirbahadoramiri.anbarplus.models.Inventory
import com.amirbahadoramiri.anbarplus.models.Product
import com.amirbahadoramiri.anbarplus.models.Store
import com.amirbahadoramiri.anbarplus.models.StoreKeeper
import com.amirbahadoramiri.anbarplus.models.Transaction
import com.amirbahadoramiri.anbarplus.models.TransactionItems

@Database(version = 1, exportSchema = false, entities = [Company::class, Inventory::class, Product::class, Store::class, StoreKeeper::class, Transaction::class, TransactionItems::class])
abstract class PublicDatabase : RoomDatabase() {

    companion object {

        private lateinit var publicDatabase: PublicDatabase

        fun getPublicDatabase(context: Context) : PublicDatabase {

            if ( publicDatabase == null ) {
                publicDatabase = Room.databaseBuilder(context, PublicDatabase::class.java,"public-db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(false)
                    .build()
            }

            return publicDatabase;
        }
    }

    abstract fun getPublicDAO() : PublicDAO

}
