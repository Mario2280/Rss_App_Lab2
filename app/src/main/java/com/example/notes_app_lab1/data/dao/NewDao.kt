package com.example.notes_app_lab1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes_app_lab1.data.entity.New

@Dao
interface NewDao {
    @Query("SELECT  * FROM news ORDER BY pubDate")
    fun getAllNotesSortedByDate(): List<New>

    @Upsert
    suspend fun inset(anew: New)

    @Delete
    suspend fun delete(aNew: New)


}