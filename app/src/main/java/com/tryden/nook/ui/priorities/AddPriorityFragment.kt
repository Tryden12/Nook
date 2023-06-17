package com.tryden.nook.ui.priorities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentAddPriorityBinding
import com.tryden.nook.ui.BaseFragment

class AddPriorityFragment : BaseFragment() {

    private var _binding: FragmentAddPriorityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddPriorityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}