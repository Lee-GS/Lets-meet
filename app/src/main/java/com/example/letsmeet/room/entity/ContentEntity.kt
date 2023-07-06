package com.example.letsmeet.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contents")
data class ContentData(
    @PrimaryKey val date: String = " ",
    @ColumnInfo val time: String = " ",
    @ColumnInfo val plan: String = " "
)