package com.example.notes.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.Model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note:Note)

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Note>>


    @Query("SELECT * FROM note_table WHERE priority = 1")
    fun getLowNotes():LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE priority = 2")
    fun getMedNotes():LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE priority = 3")
    fun getHighNotes():LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery OR subTitle LIKE :searchQuery")
    fun searchNote(searchQuery : String):LiveData<List<Note>>

}