package com.amirbahadoramiri.kalayar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

@Database(
    version = 1,
    exportSchema = false,
    entities = [Store::class, Product::class,
        Transaction::class, TransactionItem::class]
)
abstract class PublicDatabase : RoomDatabase() {

    companion object {

        private var publicDatabase: PublicDatabase? = null

        fun getPublicDatabase(context: Context): PublicDatabase {
            if (publicDatabase == null) {
                publicDatabase =
                    Room.databaseBuilder(context.applicationContext, PublicDatabase::class.java, "public.db")
                        .allowMainThreadQueries()
                        .setJournalMode(JournalMode.TRUNCATE)
                        .fallbackToDestructiveMigration(false)
                        .build()
            }
            return publicDatabase!!
        }

        fun closeDatabase() {
            publicDatabase?.close()
            publicDatabase = null
        }
    }

    abstract fun getPublicDAO(): PublicDAO

}
