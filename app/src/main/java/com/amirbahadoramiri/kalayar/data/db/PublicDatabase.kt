package com.amirbahadoramiri.kalayar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

@Database(
    version = 2,
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
//                        .setJournalMode(JournalMode.AUTOMATIC)
                        .fallbackToDestructiveMigration(false)
                        .addMigrations(object : Migration(1,2) {
                            override fun migrate(db: SupportSQLiteDatabase) {
                                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN last_value TO previous_value")
                                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN change_value TO change_amount")
                                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN new_value TO final_value")
                            }
                        })
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
