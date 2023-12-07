package com.lovevery.notes.android.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lovevery.notes.android.databinding.FragmentNotesBinding
import com.lovevery.notes.android.extensions.getAppComponent

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSendNote.setOnClickListener {
            notesViewModel.postNote(binding.editTextNoteContent.text.toString())
        }
    }

    companion object {
        private const val TAG = "NotesFragment"
    }
}