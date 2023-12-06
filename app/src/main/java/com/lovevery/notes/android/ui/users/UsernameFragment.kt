package com.lovevery.notes.android.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentEnterUsernameBinding

class UsernameFragment : Fragment() {

    private var _binding: FragmentEnterUsernameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextUsername.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == IME_ACTION_DONE) {
                findNavController().navigate(R.id.action_enterUsername_to_enterSubject)
                true
            } else {
                false
            }
        }
    }

    companion object {
        private const val TAG = "UsernameFragment"
    }
}