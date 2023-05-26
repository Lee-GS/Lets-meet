package com.example.letsmeet.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.letsmeet.room.entity.ContentData

interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(vararg contentData: ContentData)
    @Update
    suspend fun updateContents(vararg contentData: ContentData)
    @Delete
    suspend fun deleteContents(vararg contentData: ContentData)
}