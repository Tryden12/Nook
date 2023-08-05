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
import com.tryden.nook.ui.home.folders.FoldersFragmentDirections


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

    override fun onFolderSelected(selectedFolder: FolderEntity) {
        if (selectedFolder.title.contains("All")) {

            /** Update bottom sheet type **/
            when (selectedFolder.title) {
                getString(R.string.all_priorities) -> {
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_PRIORITY)
                }
                getString(R.string.all_checklists) -> {
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_CHECKLIST)
                }
                getString(R.string.all_notes) -> {
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_NOTE)
                }
            }
            /** Navigate to FoldersFragment() **/
            val navDirections =
                HomeFragmentDirections
                    .actionHomeFragmentToFoldersFragment(
                        folderType = selectedFolder.type
                    )
            navigateViewNavGraph(navDirections)

        } else {
            // Figure out which folder type
            // Update bottom sheet to match
            // Navigate to correct Fragment
            when (selectedFolder.type) {
                /** Priority **/
                getString(R.string.priority) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_PRIORITY)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToPrioritiesFragment(
                            folderTitle = selectedFolder.title
                        )
                    navigateViewNavGraph(navDirections)
                }
                /** Checklist **/
                getString(R.string.checklist) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_CHECKLIST)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToChecklistFragment(
                            folderTitle = selectedFolder.title
                        )
                    navigateViewNavGraph(navDirections)
                }
                /** Note **/
                getString(R.string.note) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_NOTE)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToNotesListFragment(
                            folderTitle = selectedFolder.title
                        )
                    navigateViewNavGraph(navDirections)
                }
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