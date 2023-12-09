package com.lovevery.notes.android.ui.subject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lovevery.notes.android.MainActivityViewModel
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentSubjectsListBinding
import com.lovevery.notes.android.extensions.getAppComponent
import com.lovevery.notes.android.ui.users.NotesAdapter

class SubjectsListFragment : Fragment() {
    private var _binding: FragmentSubjectsListBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSubjectsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        retrieveSubjects()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsList_to_enterSubject)
        }
    }

    private fun retrieveSubjects() {
        val subjects = mainViewModel.getSubjects()
        val adapter =
            NotesAdapter(subjects.toMutableList()) { selectedSubject ->
                Log.d(TAG, "Selected subject: $selectedSubject")
                mainViewModel.saveSubject(selectedSubject)
                findNavController().navigate(R.id.action_subjectsList_to_notes)
            }
        binding.recyclerViewSubjects.adapter = adapter
    }

    companion object {
        private const val TAG = "SubjectsListFragment"
    }
}
