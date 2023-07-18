package com.tryden.nook.ui.home.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentAllNotesBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.SectionFolderItemEpoxyModel
import com.tryden.nook.ui.home.OnFolderSelectedInterface
import com.tryden.nook.ui.home.checklists.AllChecklistsFragmentDirections


class AllNotesFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAllNotesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.all_notes_fragment_key)
        Log.d("AllNotesFragment()", "onViewCreated: $tag")

        // Support action bar title
        mainActivity.supportActionBar?.title = ""

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity
        ).bottomToolbarSetup()

        val controller = AllNotesEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            val itemEntityList = folders.filter {
                it.type == getString(R.string.note_type_key)
            }
            controller.itemEntityList = itemEntityList as ArrayList<FolderEntity>
        }

        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER_NOTE)

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
                    sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { notes ->
                        if (notes.isNotEmpty()) {
                            notes.filter {
                                it.folderName == folder.title
                            }.forEach { noteEntity ->
                                sharedViewModel.deleteNoteEntity(noteEntity)
                            }
                        }
                    }

                    // Delete folder from db
                    sharedViewModel.deleteFolder(folder)
                }

            })
    }


    override fun onPriorityFolderSelected() {
        // ignore
    }

    override fun onChecklistFolderSelected(selectedFolder: FolderEntity) {
        // ignore
    }

    override fun onNoteFolderSelected(selectedFolder: FolderEntity) {
        // todo revisit once NoteFragment is created
//        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
//        val navDirections =
//            AllChecklistsFragmentDirections.(selectedFolder.title)
//        navigateViewNavGraph(navDirections)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}