package com.lovevery.notes.android.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lovevery.notes.android.MainActivityViewModel
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentEnterUsernameBinding
import com.lovevery.notes.android.extensions.getAppComponent
import com.lovevery.notes.android.utility.isDoneButtonPressed

class UsernameFragment : Fragment() {
    private var _binding: FragmentEnterUsernameBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by viewModels {
        requireContext().getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEnterUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextUsername.setOnEditorActionListener { _, actionId: Int, _ ->
            if (isDoneButtonPressed(actionId)) {
                handleEnteredText()
                findNavController().navigate(R.id.action_enterUsername_to_enterSubject)
                true
            } else {
                false
            }
        }

        if (binding.editTextUsername.requestFocus()) {
            WindowCompat.getInsetsController(
                requireActivity().window,
                binding.editTextUsername,
            ).show(ime())
        }
    }

    private fun handleEnteredText() {
        val enteredUserId = binding.editTextUsername.text.toString()
        mainViewModel.saveUserId(enteredUserId)
        binding.editTextUsername.editableText.clear()
    }

    companion object {
        private const val TAG = "UsernameFragment"
    }
}
