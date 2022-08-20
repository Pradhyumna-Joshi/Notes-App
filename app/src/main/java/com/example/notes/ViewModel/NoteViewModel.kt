package com.example.notes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.Database.NoteDatabase
import com.example.notes.Database.NoteRepository
import com.example.notes.Model.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val repository:NoteRepository

    init{
        val noteDao=NoteDatabase.getNoteDatabase(application).getDao()
        repository= NoteRepository(noteDao)
    }

    fun insertNote(note: Note){
        repository.insertNote(note)
    }

    fun updateNote(note:Note){
        repository.updateNote(note)
    }

    fun deleteNote(note:Note){
        repository.deleteNote(note)
    }

    fun getAllNotes():LiveData<List<Note>> = repository.getAllNotes()

    fun getHighNotes():LiveData<List<Note>> = repository.getHighNotes()

    fun getMedNotes():LiveData<List<Note>> = repository.getMedNotes()

    fun getLowNotes():LiveData<List<Note>> = repository.getLowNotes()

    fun searchNote(searchQuery : String) : LiveData<List<Note>> = repository.searchNote(searchQuery)







}