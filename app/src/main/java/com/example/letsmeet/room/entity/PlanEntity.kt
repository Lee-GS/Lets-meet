package com.example.letsmeet.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class PlanData(
    @PrimaryKey val date: String = " ",
)

