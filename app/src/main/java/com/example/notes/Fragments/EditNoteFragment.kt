package com.example.notes.Fragments

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notes.Model.Note
import com.example.notes.R
import com.example.notes.ViewModel.NoteViewModel
import com.example.notes.databinding.FragmentEditNoteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class EditNoteFragment : Fragment() {

    private var binding_: FragmentEditNoteBinding? = null
    val binding get() = binding_!!
    val viewModel:NoteViewModel by viewModels()
    val notes by navArgs<EditNoteFragmentArgs>()
    var priority:Int=1
    var color:String="#c0392b"
    var imageURI=Uri.EMPTY
    lateinit var photoFile:File
    var webURL=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save->{
                    updateNotes()
            }
            else ->{
                Navigation.findNavController(requireView()).navigate(R.id.action_editNoteFragment2_to_homeFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding_ = FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        binding.apply {
            notes.apply {
                etTitle.setText(note.title)
                etSubTitle.setText(note.subTitle)
                etNote.setText(note.note)
                if(notes.note.imageURI.isNotEmpty()){
                    imageURI=Uri.parse(notes.note.imageURI)
                   layoutImage.visibility=View.VISIBLE
                    imageNote.setImageURI(Uri.parse(notes.note.imageURI))
                }
                if(notes.note.webURL.isNotEmpty()){
                    layoutURL.visibility=View.VISIBLE
                    textURL.text=notes.note.webURL
                }

                when(note.priority){
                    1 ->{
                        priority=1
                        prioLow.setImageResource(R.drawable.ic_save_24)
                    }
                    2->{
                        priority=2
                        prioMid.setImageResource(R.drawable.ic_save_24)
                    }
                    3->{
                        priority=3
                        prioHigh.setImageResource(R.drawable.ic_save_24)
                    }
                }
            }

            prioLow.setOnClickListener(){
                priority=1
                prioLow.setImageResource(R.drawable.ic_save_24)
                prioMid.setImageResource(0)
                prioHigh.setImageResource(0)
            }
            prioMid.setOnClickListener(){
                priority=2
                prioLow.setImageResource(0)
                prioMid.setImageResource(R.drawable.ic_save_24)
                prioHigh.setImageResource(0)

            }
            prioHigh.setOnClickListener(){
                priority=3
                prioLow.setImageResource(0)
                prioMid.setImageResource(0)
                prioHigh.setImageResource(R.drawable.ic_save_24)

            }




        }

        initMiscellaneous()



        return binding.root
    }

    private fun updateNotes() {

        binding.apply {
            val title = etTitle.text.toString()
            if(title.isEmpty()){
                Toast.makeText(requireContext(),"Title cannot be an empty field",Toast.LENGTH_LONG).show()
                return
            }
            val subtitle = etSubTitle.text.toString()
            val note = etNote.text.toString()
            val uri=imageURI.toString()
            val updatedNote = Note(title, subtitle, note, priority,color,uri,webURL, id = notes.note.id)
            viewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(),"Updated Successfully",Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireView()).navigate(R.id.action_editNoteFragment2_to_homeFragment)
        }

    }

    private fun initMiscellaneous(){
        val layout: LinearLayout=binding.root.findViewById(R.id.layoutMiscellaneous)
        val bottomSheetBehavior=BottomSheetBehavior.from(layout)

        val colorRed=layout.findViewById<ImageView>(R.id.img1)
        val colorYellow=layout.findViewById<ImageView>(R.id.img5)
        val colorPink=layout.findViewById<ImageView>(R.id.img6)
        val colorBlue=layout.findViewById<ImageView>(R.id.img2)
        val colorGreen=layout.findViewById<ImageView>(R.id.img4)
        val colorOrange=layout.findViewById<ImageView>(R.id.img3)


        layout.apply {
            findViewById<TextView>(R.id.textMiscellaneous).setOnClickListener() {
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            colorRed.setOnClickListener() {
                color = "#c0392b"
                colorRed.setImageResource(R.drawable.ic_save_24)
                colorYellow.setImageResource(0)
                colorBlue.setImageResource(0)
                colorYellow.setImageResource(0)
                colorGreen.setImageResource(0)
                colorPink.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }
            colorBlue.setOnClickListener() {
                color = "#58cced"
                colorBlue.setImageResource(R.drawable.ic_save_24)
                colorYellow.setImageResource(0)
                colorRed.setImageResource(0)
                colorYellow.setImageResource(0)
                colorGreen.setImageResource(0)
                colorPink.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }
            colorOrange.setOnClickListener() {
                color = "#e67e22"
                colorYellow.setImageResource(R.drawable.ic_save_24)
                colorYellow.setImageResource(0)
                colorBlue.setImageResource(0)
                colorRed.setImageResource(0)
                colorGreen.setImageResource(0)
                colorPink.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }
            colorPink.setOnClickListener() {
                color = "#D980FA"
                colorPink.setImageResource(R.drawable.ic_save_24)
                colorYellow.setImageResource(0)
                colorBlue.setImageResource(0)
                colorYellow.setImageResource(0)
                colorGreen.setImageResource(0)
                colorRed.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }
            colorYellow.setOnClickListener() {
                color = "#FFC312"
                colorYellow.setImageResource(R.drawable.ic_save_24)
                colorRed.setImageResource(0)
                colorBlue.setImageResource(0)
                colorYellow.setImageResource(0)
                colorGreen.setImageResource(0)
                colorPink.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }
            colorGreen.setOnClickListener() {
                color = "#C4E538"
                colorGreen.setImageResource(R.drawable.ic_save_24)
                colorYellow.setImageResource(0)
                colorBlue.setImageResource(0)
                colorYellow.setImageResource(0)
                colorRed.setImageResource(0)
                colorPink.setImageResource(0)
                binding.subtitleIndicator.setBackgroundColor(Color.parseColor(color))
            }


            val imageView = layout.findViewById<LinearLayout>(R.id.addImage)
            val addURL=layout.findViewById<LinearLayout>(R.id.addURL)

            imageView.setOnClickListener() {
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                showChooserDialog()
            }

            addURL.setOnClickListener(){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                showAddURLDialog()
            }


        }



    }

    private fun showAddURLDialog() {
        val urlDialog = Dialog(requireContext(),R.style.UrlDialog)
        urlDialog.setContentView(R.layout.layout_url)
        urlDialog.show()

        val inputURL=urlDialog.findViewById<EditText>(R.id.inputURL)
        inputURL.requestFocus()

        urlDialog.findViewById<TextView>(R.id.textAdd).setOnClickListener(){


            if(inputURL.text.toString().trim().isEmpty()){
                Toast.makeText(requireContext(),"Enter URL",Toast.LENGTH_SHORT).show()
            }
            else if(!Patterns.WEB_URL.matcher(inputURL.text.toString()).matches()){
                Toast.makeText(requireContext(),"Enter a valid URL",Toast.LENGTH_SHORT).show()

            }
            else{
                webURL=inputURL.text.toString()
                binding.textURL.text=inputURL.text.toString()
                binding.layoutURL.visibility=View.VISIBLE
                urlDialog.dismiss()
            }

        }

        urlDialog.findViewById<TextView>(R.id.textCancel).setOnClickListener(){
            urlDialog.dismiss()
        }
    }

    private fun showChooserDialog() {

        val dialog=Dialog(requireContext(),R.style.UrlDialog)
        dialog.setContentView(R.layout.layout_chooser)
        dialog.show()

        dialog.findViewById<CardView>(R.id.Camera).setOnClickListener(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        1)
                } else {

                    photoFile = createImageURI()
                    Log.d("MYDATA",photoFile.name.toString())
                    imageURI = FileProvider.getUriForFile(requireContext(),"com.example.notes.fileProvider",photoFile)
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageURI)
                    getCameraResult.launch(intent)
                    dialog.dismiss()

                }
            }
        }

        dialog.findViewById<CardView>(R.id.Gallery).setOnClickListener(){

            val intent= Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("image/*")
            getResult.launch(intent)
            dialog.dismiss()
        }



    }


    private fun createImageURI(): File {
        val imageDir=File(activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"camera_photos")

        if(!imageDir.exists()){
            imageDir.mkdirs()
        }

        val fileName = SimpleDateFormat("yyyyddMM_HH:mm:ss").format(Date())
        val file = File(imageDir.path + File.separator + fileName)

        return file



    }

    private val getResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){

            val uri = it.data?.data!!
            binding.layoutImage.visibility = View.VISIBLE
            binding.imageNote.setImageURI(uri)
            imageURI=uri

        }
    }


    private val getCameraResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        binding.layoutImage.visibility = View.VISIBLE
        binding.imageNote.setImageURI(imageURI)

    }
}






