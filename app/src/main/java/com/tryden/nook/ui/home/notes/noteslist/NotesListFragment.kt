package com.tryden.nook.ui.home.notes.noteslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.databinding.FragmentNotesListBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType

class NotesListFragment : BaseFragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the folder title string value needed to filter
     * display only those note items.
     *
     * This is passed in from HomeFragment() or AllNotesFoldersFragment() via
     * NavDirections.
     *
     * todo: setup HomeFragment() NavDirections
     */
    private val safeArgs: NotesListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container,false)
        return  binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.notes_list_fragment_key)
        Log.d(tag, "onViewCreated: $tag")

        // Bottom Sheet Type = Note Item
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.NOTE_ITEM)

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = mainActivity.getString(R.string.notes_list_fragment_key),
            activity = mainActivity
        ).bottomToolbarSetup()

        // Setup Epoxy Controller
        val controller = NotesListEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        // Set Action Bar Title
        mainActivity.supportActionBar?.title = getString(R.string.folders_title)

        sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { list ->
            val items = list.filter {
                it.folderName == safeArgs.folderTitle
            }

            when (items.size) {
                1 -> {
                    mainActivity.itemCountTextView.text = "${items.size} note"
                }
                else -> {
                    mainActivity.itemCountTextView.text = "${items.size} notes"
                }
            }

            controller.itemEntityList = items as ArrayList<NoteEntity>
        }

        swipeToDeleteSetup()

    }

    private fun swipeToDeleteSetup() {
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(NoteItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<NoteItemEpoxyModel>() {

                override fun onSwipeCompleted(
                    model: NoteItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int,
                ) {
                    val noteEntity = model?.itemEntity?: return

                    // Get folder associated with note entity
                    val selectedFolderEntity: FolderEntity? =
                        sharedViewModel.foldersLiveData.value?.find {
                        it.title == safeArgs.folderTitle
                    }

                    sharedViewModel.deleteNoteEntity(noteEntity)

                    if (selectedFolderEntity != null) {
                        // Decrease folder size by 1
                        val folderEntity = selectedFolderEntity.copy(
                            title = selectedFolderEntity.title,
                            type = selectedFolderEntity.type,
                            size = if (selectedFolderEntity.size > 0) {
                                selectedFolderEntity.size - 1
                            } else {
                                0
                            }
                        )
                        sharedViewModel.updateFolder(folderEntity)
                    } else {
                        Log.d(tag, "swipeToDeleteSetup() null folder entity" )
                    }

                    Log.d(tag, "Folder ${selectedFolderEntity!!.title} size: ${selectedFolderEntity!!.size}")
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}