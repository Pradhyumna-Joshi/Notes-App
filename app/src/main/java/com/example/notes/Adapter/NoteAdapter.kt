package com.example.notes.Adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Fragments.HomeFragment
import com.example.notes.Fragments.HomeFragmentDirections
import com.example.notes.Model.Note
import com.example.notes.R
import com.example.notes.databinding.ItemNoteBinding

class NoteAdapter(val listener: OnItemClicked): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    var arrayList = ArrayList<Note>()

    inner class NoteViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view=ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder=NoteViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote=arrayList[position]
        holder.binding.apply {
            Title.text=currentNote.title
            Note.text=currentNote.note
            date.text=currentNote.createdAtFormatted

            when(currentNote.priority){
                1 -> {
                    priority.setBackgroundResource(R.drawable.ic_green_24)
                }
                2->{
                    priority.setBackgroundResource((R.drawable.ic_yellow_24))
                }
                3->{
                    priority.setBackgroundResource(R.drawable.ic_red_24)
                }
            }
        }

        holder.binding.root.setOnClickListener(){
            val action=HomeFragmentDirections.actionHomeFragmentToEditNoteFragment2(currentNote)
            Navigation.findNavController(it!!).navigate(action)
        }

        holder.binding.root.setOnLongClickListener(){
            listener.onNoteClicked(currentNote)
            true
        }


        holder.binding.root.setCardBackgroundColor(Color.parseColor(currentNote.color))

//        holder.binding.roundImage.setImageURI(currentNote.imageURI.toUri())
//        if(currentNote.imageURI.isNotEmpty()){
//            holder.binding.roundImage.setImageURI(Uri.parse(currentNote.imageURI))
//            holder.binding.roundImage.visibility=View.VISIBLE
//        }
//        else{
//            holder.binding.roundImage.visibility=View.GONE
//        }

        holder.binding.roundImage.visibility=View.VISIBLE
        holder.binding.roundImage.setImageURI(Uri.parse(currentNote.imageURI))





    }

    fun filterList(newarrayList:ArrayList<Note>){
        arrayList.clear()
        arrayList=newarrayList
        notifyDataSetChanged()

    }

    override fun getItemCount() = arrayList.size



}


interface OnItemClicked{
    fun onNoteClicked(note: Note)
}

