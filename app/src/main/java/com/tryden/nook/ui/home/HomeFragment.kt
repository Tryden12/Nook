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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentHomeBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType


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
        Log.d("HomeFragment()", "onViewCreated: $tag")

        // Support action bar title
        mainActivity.supportActionBar?.title = ""

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER)

        binding.homeEpoxyRecyclerView.setController(controller)

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { priorityItemEntityList ->
            controller.prioritiesItemCount = priorityItemEntityList.size
        }

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            val checklistsCount = folders.filter { it.type == getString(R.string.checklist) }.size
            Log.e(tag, "onViewCreated: checklist folders count: $checklistsCount" )
            val noteFoldersCount = folders.filter { it.type == getString(R.string.note) }.size
            Log.e(tag, "onViewCreated: note folders count: $noteFoldersCount" )
            controller.checklistsCount = checklistsCount
            controller.noteFoldersCount = noteFoldersCount

            controller.folders = folders as ArrayList<FolderEntity>
        }

    }

    override fun onPriorityFolderSelected() {
        findNavController().navigate(R.id.action_homeFragment_to_prioritiesFragment)
    }

    override fun onChecklistFolderSelected(selectedFolder: FolderEntity) {
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.title) {
            getString(R.string.all_checklists) -> {
                findNavController().navigate(R.id.action_homeFragment_to_checklistsFragment)
            }
            else -> {
                val navDirections =
                    HomeFragmentDirections.actionHomeFragmentToChecklistFragment(selectedFolder.title)
                navigateViewNavGraph(navDirections)
            }
        }
    }

    override fun onNoteFolderSelected(selectedFolder: FolderEntity) {
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.title) {
            getString(R.string.all_notes) -> {
                findNavController().navigate(R.id.action_homeFragment_to_allNotesFragment)
            }
            else -> {
                // todo when NoteFragment is created
//                val navDirections =
//                    HomeFragmentDirections.(selectedFolder.title)
//                navigateViewNavGraph(navDirections)
            }
        }
    }


    override fun onResume() {
        super.onResume()

        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER)
        mainActivity.hideKeyboard(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}