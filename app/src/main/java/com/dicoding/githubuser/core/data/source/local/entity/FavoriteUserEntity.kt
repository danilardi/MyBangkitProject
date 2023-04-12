package com.dicoding.githubuser.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteUser")
data class FavoriteUserEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String  = "",

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = null,

    @field:ColumnInfo(name = "htmlUrl")
    val htmlUrl: String? = null
)