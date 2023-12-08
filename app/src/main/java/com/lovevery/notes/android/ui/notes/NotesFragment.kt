package com.lovevery.notes.android.ui.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lovevery.notes.android.MainActivityViewModel
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentNotesBinding
import com.lovevery.notes.android.extensions.getAppComponent
import com.lovevery.notes.android.ui.PostNoteState
import com.lovevery.notes.android.ui.UserNotesState
import com.lovevery.notes.android.ui.users.NotesAdapter

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    private val mainViewModel: MainActivityViewModel by viewModels {
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
            binding.editTextNoteContent.editableText.clear()
        }

        if (binding.editTextNoteContent.requestFocus()) {
            WindowCompat.getInsetsController(
                requireActivity().window,
                binding.editTextNoteContent
            ).show(ime())
        }

        setupObserver()
    }

    private fun setupObserver() {
        notesViewModel.progress
            .observe(viewLifecycleOwner, Observer(this::showLoading))

        mainViewModel.userNotes
            .observe(viewLifecycleOwner, Observer(this::handleUserNotesState))

        notesViewModel.postNoteState
            .observe(viewLifecycleOwner, Observer(this::handlePostNoteState))
    }

    private fun handleUserNotesState(userNotesState: UserNotesState) {
        when (userNotesState) {
            UserNotesState.Empty -> TODO()
            is UserNotesState.Error -> TODO()
            is UserNotesState.Success -> {
                val notes = userNotesState.notes.notes.map { it.message }
                val adapter = NotesAdapter(notes) { userSelected ->
                    Log.d(TAG, "Selected Message: $userSelected")
                }
                binding.recyclerViewUserNotes.adapter = adapter
                binding.recyclerViewUserNotes.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun handlePostNoteState(postNoteState: PostNoteState) {
        when (postNoteState) {
            is PostNoteState.Error -> TODO()
            is PostNoteState.Success -> {
                mainViewModel.refreshUserNotes()
            }
        }
    }

    private fun setEnabled(enabled: Boolean) {
        binding.btnSendNote.isEnabled = enabled
        if (enabled) {
            binding.btnSendNote.setText(R.string.send)
        } else {
            binding.btnSendNote.setText(R.string.sending)
        }
    }

    private fun showLoading(showProgress: Boolean) {
        if (showProgress) {
            setEnabled(false)
        } else {
            setEnabled(true)
        }
    }

    companion object {
        private const val TAG = "NotesFragment"
    }
}