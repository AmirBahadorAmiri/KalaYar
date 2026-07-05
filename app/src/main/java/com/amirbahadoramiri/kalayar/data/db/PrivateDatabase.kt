package com.amirbahadoramiri.kalayar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amirbahadoramiri.kalayar.domain.models.User

@Database(version = 1, exportSchema = false, entities = [User::class])
abstract class PrivateDatabase : RoomDatabase() {

    companion object {
        private var privateDB: PrivateDatabase? = null

        fun getPrivateDatabase(context: Context): PrivateDatabase {
            if (privateDB == null) {
                privateDB = Room.databaseBuilder(context, PrivateDatabase::class.java, "private.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(false)
                    .build()
            }
            return privateDB!!
        }

    }

    abstract fun getPrivateDAO(): PrivateDAO

}
