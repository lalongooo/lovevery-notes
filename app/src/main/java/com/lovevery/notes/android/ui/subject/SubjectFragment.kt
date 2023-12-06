package com.lovevery.notes.android.ui.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lovevery.notes.android.R
import com.lovevery.notes.android.databinding.FragmentEnterSubjectBinding

class SubjectFragment : Fragment() {

    private var _binding: FragmentEnterSubjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextSubject.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                findNavController().navigate(R.id.action_enterSubject_to_notes)
                true
            } else {
                false
            }
        }
    }

    companion object {
        private const val TAG = "SubjectFragment"
    }
}