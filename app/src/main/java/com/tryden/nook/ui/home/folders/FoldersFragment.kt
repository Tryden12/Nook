package com.tryden.nook.ui.home.folders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.data.source.local.entity.FolderEntity
import com.tryden.nook.databinding.FragmentFoldersBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.SectionFolderItemEpoxyModel
import com.tryden.nook.ui.home.OnFolderSelectedInterface
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet


class FoldersFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentFoldersBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the section title string value needed to
     * display the correct section title: Priorities, Checklists, Notes
     *
     * This is passed in from HomeFragment() NavDirections.
     */
    private val safeArgs: FoldersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFoldersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.all_folders_fragment_key)
        Log.d(tag, "onViewCreated: $tag")

        // Support action bar title
        mainActivity.supportActionBar?.title = getString(R.string.folders_title)

        // Setup Bottom Toolbar
        setupBottomToolbar()

        // Update bottom sheet type
        updateBottomSheetType(folderType = safeArgs.folderType)

        // Setup Epoxy Controller, but don't build models just yet
        val controller = AllFoldersEpoxyController(safeArgs.folderType, this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            val itemEntityList = folders.filter {
                it.type == safeArgs.folderType
            }
            // Set folderEntitiesList to requestModelBuild()
            controller.folderEntitiesList = itemEntityList as ArrayList<FolderEntity>
        }

        swipeToDeleteSetup()
    }

    private fun setupBottomToolbar() {
        // Show correct toolbar, hide others
        mainActivity.bottomToolbarHome.isVisible = true
        mainActivity.bottomToolbarItemsList.isInvisible = true
        mainActivity.bottomToolbarEditItem.isInvisible = true

        // OnClick listeners
        mainActivity.addFolderImageView.setOnClickListener {
            AddItemSheet().show(mainActivity.supportFragmentManager, null)
        }
        mainActivity.addItemImageViewHome.isGone = true // todo: revisit
    }

    private fun updateBottomSheetType(folderType: String?) {
        val bottomSheetType = when (folderType) {
            getString(R.string.priority) -> {
                BottomSheetViewType.Type.FOLDER_PRIORITY
            }
            getString(R.string.checklist) -> {
                BottomSheetViewType.Type.FOLDER_CHECKLIST
            }
            getString(R.string.note) -> {
                BottomSheetViewType.Type.FOLDER_NOTE
            }
            else -> {
                BottomSheetViewType.Type.FOLDER
            }
        }

        // Bottom Sheet Type = Note Item
        sharedViewModel.updateBottomSheetItemType(bottomSheetType)
        Log.e(tag, "updateBottomSheetType: BottomSheetViewType is ${bottomSheetType.name}" )

    }

    override fun onFolderSelected(selectedFolder: FolderEntity) {
        // Update current folder selected
        // Figure out which fragment to navigate to
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.type) {
            /** Priority **/
            getString(R.string.priority) -> {
                Log.d(tag, "onFolderSelected: [Priority], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToPrioritiesFragment(
                    folderName = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            /** Checklist **/
            getString(R.string.checklist) -> {
                Log.d(tag, "onFolderSelected: [Checklist], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToChecklistFragment(
                    folderName = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            /** Note **/
            getString(R.string.note) -> {
                Log.d(tag, "onFolderSelected: [Note], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToNotesListFragment(
                    folderName = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            else -> {
                // Do nothing
                Log.e(tag, "onFolderSelected: ELSE case, no selectedFolder type not found")
            }
        }
    }

    private fun swipeToDeleteSetup() {
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(SectionFolderItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<SectionFolderItemEpoxyModel>() {

                override fun onSwipeCompleted(
                    model: SectionFolderItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int,
                ) {
                    val folder = model?.folderEntity ?: return

                    // Delete all items in folder first
                    deleteAllItemsInFolder(folder)

                    // Then, delete folder from database
                    sharedViewModel.deleteFolder(folder)
                }

            })
    }

    private fun deleteAllItemsInFolder(folder: FolderEntity) {
        when (folder.type) {
            getString(R.string.priority) -> {
                sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { items ->
                    if (items.isNotEmpty()) {
                        items.filter {
                            it.folderName == folder.title
                        }.forEach { item ->
                            sharedViewModel.deleteItem(item)
                        }
                    }
                }
            }

            getString(R.string.checklist) -> {
                sharedViewModel.checklistItemEntitiesLiveData.observe(viewLifecycleOwner) { items ->
                    if (items.isNotEmpty()) {
                        items.filter {
                            it.folderName == folder.title
                        }.forEach { item ->
                            sharedViewModel.deleteChecklistItem(item)
                        }
                    }
                }
            }

            getString(R.string.note) -> {
                sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { items ->
                    if (items.isNotEmpty()) {
                        items.filter {
                            it.folderName == folder.title
                        }.forEach { item ->
                            sharedViewModel.deleteNoteEntity(item)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}