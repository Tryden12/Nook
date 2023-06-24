package com.tryden.nook.ui.priorities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentPrioritiesBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup

class PrioritiesFragment : BaseFragment() {

    private var _binding: FragmentPrioritiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteCountTextView: AppCompatTextView
    private lateinit var addFolderImageView: AppCompatImageView
    private lateinit var addItemImageView: AppCompatImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrioritiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomToolbarSetup()
        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) {
            // todo

        }
    }

    private fun bottomToolbarSetup() {
        noteCountTextView = requireActivity().findViewById(R.id.countTextView)
        addFolderImageView = requireActivity().findViewById(R.id.addFolderImageView)
        addItemImageView = requireActivity().findViewById(R.id.addItemImageView)

        BottomToolbarSetup(
            getString(R.string.priorities_fragment_key),
            requireActivity(),
            addItemImageView,
            noteCountTextView,
            addFolderImageView
        ).bottomToolbarSetup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}