package com.lovevery.notes.android.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lovevery.notes.android.MainActivityViewModel
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentSplashScreenBinding
import com.lovevery.notes.android.extensions.getAppComponent
import com.lovevery.notes.android.ui.users.UsersListFragment

class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        mainViewModel.getAllNotes()
    }

    private fun setupObserver() {
        mainViewModel.progress
            .observe(viewLifecycleOwner, Observer(this::showLoading))

        mainViewModel.notesState
            .observe(viewLifecycleOwner, Observer(this::handleNotesState))
    }

    private fun showLoading(showProgress: Boolean) {
        if (showProgress) {
            binding.progressBarIndicator.visibility = View.VISIBLE
        } else {
            binding.progressBarIndicator.visibility = View.GONE
        }
    }

    private fun handleNotesState(notesState: NotesState) {
        when (notesState) {
            NotesState.Empty ->
                findNavController().navigate(R.id.action_splashScreen_to_enterUsername)
            is NotesState.Error -> {
                // TODO
            }
            is NotesState.Success -> {
                findNavController().navigate(R.id.action_splashScreen_to_usersList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SplashScreenFragment"
    }
}
