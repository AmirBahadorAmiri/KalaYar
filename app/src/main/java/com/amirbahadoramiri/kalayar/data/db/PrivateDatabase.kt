package com.amirbahadoramiri.kalayar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amirbahadoramiri.kalayar.domain.models.User

@Database(version = 2, exportSchema = false, entities = [User::class])
abstract class PrivateDatabase : RoomDatabase() {

    companion object {
        private var privateDB: PrivateDatabase? = null

        fun getPrivateDatabase(context: Context): PrivateDatabase {
            if (privateDB == null) {
                val MIGRATIONS = arrayOf(migration_1_2)
                privateDB = Room.databaseBuilder(context, PrivateDatabase::class.java, "private.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(false)
                    .build()
            }
            return privateDB!!
        }

        val migration_1_2 = object : Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user ADD COLUMN user_password TEXT")
            }
        }

    }

    abstract fun getPrivateDAO(): PrivateDAO

}
