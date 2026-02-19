package com.amirbahadoramiri.rasa.tools.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amirbahadoramiri.rasa.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PrivateDAO {

    @Insert
    fun insertUser(user: User): Completable

    @Delete
    fun deleteUser(user: User): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User): Completable

    @Query("SELECT * FROM user limit 1")
    fun getUser(): Single<User>

}
