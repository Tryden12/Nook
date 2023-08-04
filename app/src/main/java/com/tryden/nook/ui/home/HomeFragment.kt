package com.tryden.nook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            controller.folders = folders as ArrayList<FolderEntity>
        }
    }

    override fun onPriorityFolderSelected(selectedFolder: FolderEntity) {
//        findNavController().navigate(R.id.action_homeFragment_to_prioritiesFragment)
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.title) {
            getString(R.string.all_priorities) -> {
                findNavController().navigate(R.id.action_homeFragment_to_allPrioritiesFragment)
            }
            else -> {
                val navDirections =
                    HomeFragmentDirections
                        .actionHomeFragmentToPrioritiesFragment(
                            folderTitle = selectedFolder.title
                        )
                navigateViewNavGraph(navDirections)
            }
        }
    }

    override fun onChecklistFolderSelected(selectedFolder: FolderEntity) {
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.title) {
            getString(R.string.all_checklists) -> {
                findNavController().navigate(R.id.action_homeFragment_to_checklistsFragment)
            }
            else -> {
                val navDirections =
                    HomeFragmentDirections
                        .actionHomeFragmentToChecklistFragment(
                            folderTitle = selectedFolder.title
                        )
                navigateViewNavGraph(navDirections)
            }
        }
    }

    override fun onNoteFolderSelected(selectedFolder: FolderEntity) {
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.title) {
            getString(R.string.all_notes) -> {
                findNavController().navigate(R.id.action_homeFragment_to_allNotesFoldersFragment)
            }
            else -> {
                val navDirections =
                    HomeFragmentDirections
                        .actionHomeFragmentToNotesListFragment(
                        folderTitle = selectedFolder.title
                    )
                navigateViewNavGraph(navDirections)
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