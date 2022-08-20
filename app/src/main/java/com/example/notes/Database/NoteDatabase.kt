package com.example.notes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.Model.Note

@Database(entities = [Note::class],version=1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao():NoteDao

    companion object{
        @Volatile
        var INSTANCE : NoteDatabase?=null

        fun getNoteDatabase(context:Context):NoteDatabase{
            var instance = INSTANCE
            if(instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(
                        context,
                        NoteDatabase::class.java,
                        "note_database"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            INSTANCE=instance
            return INSTANCE!!

        }

    }
}