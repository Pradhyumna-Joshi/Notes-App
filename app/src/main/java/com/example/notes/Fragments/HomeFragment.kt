package com.example.notes.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Adapter.NoteAdapter
import com.example.notes.Adapter.OnItemClicked
import com.example.notes.MainActivity
import com.example.notes.Model.Note
import com.example.notes.R
import com.example.notes.ViewModel.NoteViewModel
import com.example.notes.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.concurrent.thread


class HomeFragment : Fragment(), OnItemClicked {

    private var binding_: FragmentHomeBinding? = null
    private val viewModel: NoteViewModel by viewModels()
    lateinit var madapter: NoteAdapter
    val binding get() = binding_!!


    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        (activity as MainActivity?)?.supportActionBar?.hide()
        binding_ = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        madapter = NoteAdapter(this@HomeFragment)
        binding.recyclerView.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = madapter
        }


        viewModel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
            binding.apply {
                recyclerView.apply {
                    madapter.filterList(notesList as ArrayList<Note>)
                }
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchNote(newText)
                }
                return true
            }

        })

        binding.apply {
            filterHigh.setOnClickListener() {
                viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->

                    binding.apply {
                        recyclerView.apply {
                            madapter.filterList(notesList as ArrayList<Note>)
                        }
                    }
                }

            }
            filterMed.setOnClickListener() {
                viewModel.getMedNotes().observe(viewLifecycleOwner) { notesList ->

                    binding.apply {
                        recyclerView.apply {
                            madapter.filterList(notesList as ArrayList<Note>)
                        }
                    }

                }

            }
            filterLow.setOnClickListener() {
                viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->

                    binding.apply {
                        recyclerView.apply {
                            madapter.filterList(notesList as ArrayList<Note>)
                        }

                    }

                }
                allNotes.setOnClickListener() {
                    viewModel.getAllNotes().observe(viewLifecycleOwner) { notesList ->


                        binding.apply {
                            recyclerView.apply {
                                madapter.filterList(notesList as ArrayList<Note>)
                            }
                        }

                    }
                }
            }


            binding.fabAdd.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_addNoteFragment)
            }


            return binding.root
        }

    }


    fun searchNote(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchNote(searchQuery).observe(viewLifecycleOwner) {
            madapter.filterList(it as ArrayList<Note>)
        }
    }

    override fun onNoteClicked(note: Note) {
        val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        bottomSheet.setContentView(R.layout.dialog_delete)
        bottomSheet.show()

        val textYes = bottomSheet.findViewById<TextView>(R.id.Yes)
        val textNo = bottomSheet.findViewById<TextView>(R.id.No)

        textYes?.setOnClickListener() {
            viewModel.deleteNote(note)
            bottomSheet.dismiss()
        }

        textNo?.setOnClickListener() {
            bottomSheet.dismiss()
        }
    }


}


