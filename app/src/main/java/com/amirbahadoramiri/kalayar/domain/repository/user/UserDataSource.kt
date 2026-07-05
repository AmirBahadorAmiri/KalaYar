package com.amirbahadoramiri.kalayar.domain.repository.user

import com.amirbahadoramiri.kalayar.domain.models.User

interface UserDataSource {

    suspend fun addUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getUser(): User?

}