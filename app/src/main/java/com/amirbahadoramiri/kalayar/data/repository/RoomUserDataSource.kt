package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PrivateDatabase
import com.amirbahadoramiri.kalayar.domain.models.User
import com.amirbahadoramiri.kalayar.domain.repository.user.UserDataSource

class RoomUserDataSource(context: Context): UserDataSource {

    val privateDAO = PrivateDatabase.getPrivateDatabase(context).getPrivateDAO()

    override suspend fun addUser(user: User) = privateDAO.addUser(user)

    override suspend fun deleteUser(user: User) = privateDAO.deleteUser(user)

    override suspend fun updateUser(user: User) = privateDAO.updateUser(user)

    override suspend fun getUser() = privateDAO.getUser()

}