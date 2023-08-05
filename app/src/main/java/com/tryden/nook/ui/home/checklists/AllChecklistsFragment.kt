package com.tryden.nook.ui.home.checklists

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentChecklistsBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.SectionFolderItemEpoxyModel
import com.tryden.nook.ui.home.OnFolderSelectedInterface

class AllChecklistsFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentChecklistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChecklistsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.checklists_fragment_key)
        Log.d("AllChecklistsFragment()", "onViewCreated: $tag")

        // Support action bar title
        mainActivity.supportActionBar?.title = getString(R.string.folders_title)

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        // Bottom Sheet Type = Folder
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_CHECKLIST)

        // Setup Epoxy Controller
        val controller = AllChecklistsEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            val itemEntityList = folders.filter {
                it.type == getString(R.string.checklist_type_key)
            }

            controller.itemEntityList = itemEntityList as ArrayList<FolderEntity>
        }

        // Setup swipe-to-delete
        swipeToDeleteSetup()
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

                    // Delete all items in folder
                    sharedViewModel.checklistItemEntitiesLiveData.observe(viewLifecycleOwner) { checklistItems ->
                        if (checklistItems.isNotEmpty()) {
                            checklistItems.filter {
                                it.folderName == folder.title
                            }.forEach { checklistItemEntity ->
                                sharedViewModel.deleteChecklistItem(checklistItemEntity)
                            }
                        }
                    }

                    // Delete folder from db
                    sharedViewModel.deleteFolder(folder)
                }

            })
    }

    override fun onFolderSelected(selectedFolder: FolderEntity) {
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
//        val navDirections =
//            AllChecklistsFragmentDirections.actionChecklistsFragmentToChecklistFragment(selectedFolder.title)
//        navigateViewNavGraph(navDirections)
    }

//    override fun onChecklistFolderSelected(selectedFolder: FolderEntity) {
//        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
//        val navDirections =
//            AllChecklistsFragmentDirections.actionChecklistsFragmentToChecklistFragment(selectedFolder.title)
//        navigateViewNavGraph(navDirections)
//    }
//
//    override fun onNoteFolderSelected(selectedFolder: FolderEntity) {
//        // todo
//    }
//
//    override fun onPriorityFolderSelected(selectedFolder: FolderEntity) {
//        // ignore
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}