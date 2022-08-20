package com.example.notes.Database

import androidx.lifecycle.LiveData
import com.example.notes.Model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>> =noteDao.getAllNotes()

    fun getLowNotes(): LiveData<List<Note>> = noteDao.getLowNotes()

    fun getMedNotes(): LiveData<List<Note>> = noteDao.getMedNotes()

    fun getHighNotes(): LiveData<List<Note>> = noteDao.getHighNotes()

    fun searchNote(searchQuery:String):LiveData<List<Note>> = noteDao.searchNote(searchQuery)


    fun insertNote(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    fun updateNote(note:Note){
        GlobalScope.launch (Dispatchers.IO){
            noteDao.update(note)
        }
    }

    fun deleteNote(note:Note){
        GlobalScope.launch (Dispatchers.IO){
            noteDao.delete(note)
        }
    }


}