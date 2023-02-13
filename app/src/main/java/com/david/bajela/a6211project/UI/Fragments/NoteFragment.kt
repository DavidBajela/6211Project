package com.david.bajela.a6211project.UI.Fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.text.format.DateFormat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.david.bajela.a6211project.R
import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.databinding.FragmentNoteBinding

class NoteFragment : Fragment(R.layout.fragment_note) {

    private lateinit var binding: FragmentNoteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        loadNote()
        setOnclicktisterners()
    }

    private fun setOnclicktisterners() {

        binding.apply {
            notMletMessage.addTextChangedListener {
                Repo.note.note = notMletMessage.text.toString()
                Repo.updateTimestamp()

            }
            notTvTitle.addTextChangedListener {
                Repo.note.title = notTvTitle.text.toString()
                Repo.updateTimestamp()
            }
            btnPhoto.setOnClickListener { navtoPhoto() }
            btnDone.setOnClickListener { navtoMess() }
        }
    }

    private fun navtoPhoto() {
        this.view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_noteFragment_to_camFragment)
        }
    }

    private fun navtoMess() {
        Repo.updateNote()
        this.view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_noteFragment_to_mesageFragment)
        }
    }

    private fun loadNote() {
        val n = Repo.note
        val d: Bitmap = BitmapFactory.decodeResource(context?.resources, n.iconName)

        binding.apply {

            iVPic.visibility = View.INVISIBLE
            if (n.image != null) {
                iVPic.visibility = View.VISIBLE
                iVPic.setImageURI(Uri.parse(n.image))
            }
            ivIcon.setImageBitmap(d)
            notTvTitle.setText(n.title)
            notMletMessage.setText(n.note)
            val date = DateFormat.format(Repo.timestampformat, n.lastUpdate)
            notTvTimestamp.text = date
        }
    }
}
