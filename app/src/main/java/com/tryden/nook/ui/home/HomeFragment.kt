package com.tryden.nook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentHomeBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val controller = HomeEpoxyController(::onFolderItemSelected)
    private lateinit var noteCountTextView: AppCompatTextView
    private lateinit var addFolderImageView: AppCompatImageView
    private lateinit var addItemImageView: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomToolbarSetup()
        Log.e("HomeFragment", "onViewCreated: ${HomeFragment().id}")
        binding.homeEpoxyRecyclerView.setControllerAndBuildModels(controller)

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { priorityItemEntityList ->
            // todo
        }
    }

    private fun bottomToolbarSetup() {
        noteCountTextView = requireActivity().findViewById(R.id.countTextView)
        addFolderImageView = requireActivity().findViewById(R.id.addFolderImageView)
        addItemImageView = requireActivity().findViewById(R.id.addItemImageView)

        BottomToolbarSetup(
            getString(R.string.home_fragment_key),
            requireActivity(),
            addItemImageView,
            noteCountTextView,
            addFolderImageView
        ).bottomToolbarSetup()

        addItemImageView.setOnClickListener {
            navigateViewNavGraph(R.id.action_homeFragment_to_addPriorityFragment)
        }
    }

    private fun onFolderItemSelected() {
        findNavController().navigate(R.id.action_homeFragment_to_prioritiesFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}