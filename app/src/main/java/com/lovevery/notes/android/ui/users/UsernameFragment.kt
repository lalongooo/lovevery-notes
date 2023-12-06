package com.lovevery.notes.android.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    companion object {
        private const val TAG = "UsernameFragment"
    }
}