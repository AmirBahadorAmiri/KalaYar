package com.amirbahadoramiri.kalayar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amirbahadoramiri.kalayar.domain.models.Contact
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

@Database(
    version = 4,
    exportSchema = false,
    entities = [Store::class, Product::class, Contact::class,
        Transaction::class, TransactionItem::class]
)
abstract class PublicDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var publicDatabase: PublicDatabase? = null

        fun getPublicDatabase(context: Context): PublicDatabase {
            return publicDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PublicDatabase::class.java,
                    "public.db"
                )
                    .fallbackToDestructiveMigration(false)
                    .addMigrations(migration_1_2, migration_2_3, migration_3_4)
                    .build()
                publicDatabase = instance
                instance
            }
        }

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN last_value TO previous_value")
                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN change_value TO change_amount")
                db.execSQL("ALTER TABLE transaction_item RENAME COLUMN new_value TO final_value")
            }
        }

        private val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `contacts` (
                `contact_name` TEXT NOT NULL,
                `contact_number` TEXT NOT NULL,
                `contact_id` INTEGER PRIMARY KEY AUTOINCREMENT
            )
        """.trimIndent()
                )
            }
        }

        private val migration_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE product ADD COLUMN product_purchase_price INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE transaction_item ADD COLUMN purchase_price INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun closeDatabase() {
            publicDatabase?.close()
            publicDatabase = null
        }
    }

    abstract fun getPublicDAO(): PublicDAO

}
