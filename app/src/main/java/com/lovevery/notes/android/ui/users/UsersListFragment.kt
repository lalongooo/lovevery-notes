package com.lovevery.notes.android.ui.users

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
import com.lovevery.notes.android.databinding.FragmentUsersListBinding
import com.lovevery.notes.android.extensions.getAppComponent

class UsersListFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_usersList_to_enterUsername)
        }
    }

    private fun setupObserver() {
        mainViewModel.notes
            .observe(viewLifecycleOwner) { handleNotes() }
    }

    private fun handleNotes() {
        val users = mainViewModel.getUsers()
        val adapter = NotesAdapter(users) { userSelected ->
            Log.d(TAG, "userSelected: $userSelected")
            mainViewModel.saveUserId(userSelected)
            navigateToSubjectList()
        }
        binding.recyclerViewNotes.adapter = adapter
    }

    private fun navigateToSubjectList() {
        findNavController().navigate(R.id.action_usersList_to_subjectsList)
    }

    companion object {
        private const val TAG = "UsersListFragment"
    }
}