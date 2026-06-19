package com.amirbahadoramiri.kalayar.tools.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirbahadoramiri.kalayar.core.models.Store
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.core.models.Transaction
import com.amirbahadoramiri.kalayar.core.models.TransactionItem

@Database(
    version = 1,
    exportSchema = false,
    entities = [Store::class, Product::class,
        Transaction::class, TransactionItem::class]
)
abstract class PublicDatabase : RoomDatabase() {

    companion object {

        private var publicDatabase: PublicDatabase? = null

        fun getPublicDatabase(context: Context): PublicDatabase? {
            if (publicDatabase == null) {
                publicDatabase =
                    Room.databaseBuilder(context, PublicDatabase::class.java, "public.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration(false)
                        .build()
            }
            return publicDatabase;
        }
    }

    abstract fun getPublicDAO(): PublicDAO

}
