package com.tryden.nook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentHomeBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet


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

        setupBottomToolbar()

        // Update bottom sheet type to folder (general)
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER)

        // Set controller, but don't build models yet
        binding.homeEpoxyRecyclerView.setController(controller)

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            // Set folders in controller to requestModelBuild() in controller
            controller.folders = folders as ArrayList<FolderEntity>
        }
    }

    private fun setupBottomToolbar() {
        // Show correct toolbar, hide others
        mainActivity.bottomToolbarHome.isVisible = true
        mainActivity.bottomToolbarEditItem.isInvisible = true
        mainActivity.bottomToolbarItemsList.isInvisible = true

        // OnClick listeners
        mainActivity.addFolderImageView.setOnClickListener {
            Log.d(tag, "addFolderImageView clicked", )
            AddItemSheet().show(mainActivity.supportFragmentManager, null)

        }
        mainActivity.addItemImageViewHome.isGone = true
//        mainActivity.addItemImageViewHome.setOnClickListener {
//            Log.d(tag, "addItemImageViewHome clicked from HomeFragment", )
//        }
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
            // Update current folder selected,
            // Figure out which folder type,
            // Update bottom sheet to match,
            // Navigate to correct Fragment
            sharedViewModel.updateCurrentFolderSelected(selectedFolder)
            Log.d(tag, "currentFolderSelected: ${selectedFolder.title}", )
            when (selectedFolder.type) {
                /** Priority **/
                getString(R.string.priority) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_PRIORITY)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToPrioritiesFragment(
                            folderName = selectedFolder.title
                        )
                    navigateViewNavGraph(navDirections)
                }
                /** Checklist **/
                getString(R.string.checklist) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_CHECKLIST)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToChecklistFragment(
                            folderName = selectedFolder.title
                        )
                    navigateViewNavGraph(navDirections)
                }
                /** Note **/
                getString(R.string.note) -> {
                    // Bottom Sheet Type = Folder
                    sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_NOTE)
                    val navDirections =
                        HomeFragmentDirections.actionHomeFragmentToNotesListFragment(
                            folderName = selectedFolder.title
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