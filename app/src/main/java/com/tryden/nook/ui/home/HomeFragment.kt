package com.tryden.nook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentHomeBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup


class HomeFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val controller = HomeEpoxyController(this)

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
        val tag = resources.getString(R.string.home_fragment_key)
        Log.e("HomeFragment()", "onViewCreated: $tag")

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { priorityItemEntityList ->
            controller.prioritiesItemCount = priorityItemEntityList.size.toString()
        }

        sharedViewModel.checklistItemEntitiesLiveData.observe(viewLifecycleOwner) { checkLists ->
            controller.checklistsCount = checkLists.size.toString()
        }

        binding.homeEpoxyRecyclerView.setControllerAndBuildModels(controller)
    }

    override fun onPriorityFolderSelected() {
        findNavController().navigate(R.id.action_homeFragment_to_prioritiesFragment)
    }

    override fun onChecklistFolderSelected() {
        findNavController().navigate(R.id.action_homeFragment_to_checklistsFragment)
    }

    override fun onNoteFolderSelected() {
        // todo revisit once note fragment created
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideKeyboard(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}