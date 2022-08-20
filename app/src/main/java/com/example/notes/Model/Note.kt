package com.example.notes.Model

import android.net.Uri
import android.os.Parcelable
import android.webkit.WebSettings
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.text.DateFormat

@Entity(tableName = "note_table")
@Parcelize
data class Note(

    val title:String,
    val subTitle:String,
    val note:String,
    val priority:Int,
    val color:String="#c0392b",
    val imageURI: String ="",
    val webURL: String="",
    val createdAt:Long=System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    ):Parcelable
{

    val createdAtFormatted : String
    get() = DateFormat.getDateTimeInstance().format(createdAt)
}