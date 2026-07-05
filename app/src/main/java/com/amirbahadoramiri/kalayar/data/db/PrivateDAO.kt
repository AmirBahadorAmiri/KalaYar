package com.amirbahadoramiri.kalayar.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amirbahadoramiri.kalayar.domain.models.User

@Dao
interface PrivateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Query("SELECT * FROM user limit 1")
    fun getUser(): User?

}
