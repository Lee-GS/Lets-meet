package com.example.letsmeet.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "friends")
data class FriendData(
    @PrimaryKey val name: String = " "
)