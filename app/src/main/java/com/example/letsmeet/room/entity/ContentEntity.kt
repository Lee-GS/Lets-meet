package com.example.letsmeet.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contents3")
data class ContentData(
    @PrimaryKey val date: String = " ",
    @ColumnInfo val time: List<String>,
    @ColumnInfo val plan: List<String>
)