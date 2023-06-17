package com.tryden.nook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentHomeBinding
import com.tryden.nook.ui.BaseFragment


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val controller = HomeEpoxyController(::onFolderItemSelected)


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

        binding.homeEpoxyRecyclerView.setControllerAndBuildModels(controller)

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { priorityItemEntityList ->
            // todo
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