package com.amirbahadoramiri.kalayar.domain.repository.user

import com.amirbahadoramiri.kalayar.domain.models.User

class UserRepository(private val userDataSource: UserDataSource) {

    suspend fun addUser(user: User) = userDataSource.addUser(user)
    suspend fun deleteUser(user: User) = userDataSource.deleteUser(user)
    suspend fun updateUser(user: User) = userDataSource.updateUser(user)
    suspend fun getUser() = userDataSource.getUser()

}