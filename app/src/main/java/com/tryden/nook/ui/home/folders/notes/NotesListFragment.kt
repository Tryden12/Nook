package com.tryden.nook.ui.home.folders.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.FragmentNotesListBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.home.OnItemSelected
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet

class NotesListFragment : BaseFragment(), OnItemSelected {

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
        setupBottomToolbar()

        // Setup Epoxy Controller, but don't build models just yet
        val controller = NotesListEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        // Set Action Bar Title
        mainActivity.supportActionBar?.title = getString(R.string.folders_title)

        sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { list ->
            val items = list.filter {
                it.folderName == safeArgs.folderName
            }
            safeArgs.folderName?.let { Log.d(tag, "Folder name: $it") }

            when (items.size) {
                1 -> {
                    mainActivity.itemCountTextView.text = "${items.size} note"
                }
                else -> {
                    mainActivity.itemCountTextView.text = "${items.size} notes"
                }
            }

            // Set itemEntityList to requestModelBuild() in controller
            controller.itemEntityList = items as ArrayList<NoteEntity>
        }

        swipeToDeleteSetup()

    }

    override fun onItemSelected(itemType: EpoxyItemsInterface) {
        if (itemType is EpoxyItemsInterface.NoteItem) {
            sharedViewModel.updateCurrentNoteSelected(itemType.item)
            sharedViewModel.updateEditMode(isEditMode = true)
            AddItemSheet().show(mainActivity.supportFragmentManager, null)
        }
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
                        it.title == safeArgs.folderName
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

    private fun setupBottomToolbar() {
        // Show correct toolbar, hide others
        mainActivity.bottomToolbarItemsList.isVisible = true
        mainActivity.bottomToolbarHome.isInvisible = true
        mainActivity.bottomToolbarEditItem.isInvisible = true

        mainActivity.addItemImageViewItemsList.setOnClickListener {
            Log.d(tag, "addItemImageViewItemsList clicked from NoteListFragment()", )
            AddItemSheet().show(mainActivity.supportFragmentManager, null)
        }
    }

    override fun onBumpPriority(priorityItemEntity: PriorityItemEntity) {
        // ignore
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}