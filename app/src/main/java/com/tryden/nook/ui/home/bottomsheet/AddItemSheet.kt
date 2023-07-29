package com.tryden.nook.ui.home.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tryden.nook.R
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.FragmentAddItemSheetBinding
import com.tryden.nook.ui.MainActivity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.*

/**
 * This class is the primary bottom sheet used for the application.
 *
 * The layout file will be provided at a Fragment level based on the
 * bottomSheetAddItemTypeLiveData value updated and retrieved from the NookViewModel().
 */

class AddItemSheet : BottomSheetDialogFragment(), OnAddItemSheetButtonSelected {

    private var _binding: FragmentAddItemSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NookViewModel by activityViewModels()

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddItemSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.add_item_bottom_sheet_fragment_key)
        Log.d("AddItemSheet()", "onViewCreated: $tag")

        mainActivity = (activity as MainActivity)
        val epoxyController = AddItemSheetEpoxyController(this)
        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.editMode.observe(mainActivity) {
            epoxyController.editMode = it
            if (it) {
                Log.d(tag, "editMode on" )
            } else {
                Log.d(tag, "editMode off" )
            }
        }

        viewModel.currentSelectedFolderLiveData.observe(mainActivity) {
            epoxyController.currentFolderSelected = it
            Log.d(tag, "currentFolderSelected: ${it.title}" )
        }

        viewModel.currentSelectedPriorityItemLiveData.observe(mainActivity) {
            epoxyController.currentPrioritySelected = it
            Log.d(tag, "currentPrioritySelected: ${it.title}" )
        }

        viewModel.currentSelectedChecklistItemLiveData.observe(mainActivity) {
            epoxyController.currentChecklistItemSelected = it
            Log.d(tag, "currentChecklistItemSelected: ${it.title}" )
        }

        viewModel.currentNoteSelectedLiveData.observe(mainActivity) {
            epoxyController.currentNoteSelected = it
            Log.d(tag, "currentNoteSelected: ${it.title}" )
        }

        viewModel.bottomSheetAddItemTypeLiveData.observe(mainActivity) {
            epoxyController.currentBottomSheetType = it
            Log.d(tag, "currentBottomSheetType: $it" )
        }

    }

    override fun onCancel() {
        // Turn off edit mode globally, update editMode in view model
        viewModel.updateEditMode(isEditMode = false)
        mainActivity.hideKeyboard(requireView())
        dismiss()
    }

    /**
     * @param [item] can be one of the following items:
     * Folder, Priority, Checklist, or Note
     */
    override fun onInsertItem(itemType: EpoxyItemsInterface) {
        // If item is folder, insert, refresh ViewModel items, and dismiss
        // If not, then we'll need to increase the folder size of the item by 1 and then move on
        // to when statement below this one.
        when (itemType) {
            is FolderItem -> {
                viewModel.insertFolder(itemType.item)
                Log.d(tag, "onInsertItem(), folder item: ${itemType.item.title}" )
                viewModel.collectAllItems()
                dismiss()
                return
            }

            is PriorityItem -> {
                // ignore, priority items don't have folders
            }

            else -> {
                // Increase folder size by 1
                val selectedFolderEntity: FolderEntity? = viewModel.currentSelectedFolderLiveData.value
                if (selectedFolderEntity != null) {
                    val oldSize = selectedFolderEntity.size
                    val folderEntity = selectedFolderEntity.copy(
                        title = selectedFolderEntity.title,
                        type = selectedFolderEntity.type,
                        size = selectedFolderEntity.size + 1
                    )
                    viewModel.updateFolder(folderEntity)
                    viewModel.updateCurrentFolderSelected(folderEntity)
                    val newSize = folderEntity.size
                    Log.d(tag, "onInsertItem() ${selectedFolderEntity.title} from $oldSize to $newSize" )
                } else {
                    Log.d(tag, "onInsertItem() null folder entity" )
                }
            }
        }

        // Separate when case just for Priority, Checklist, and Note items
        when (itemType) {
            is PriorityItem -> {
                viewModel.insertPriorityItem(itemType.item)
                Log.d(tag, "onInsertItem(), priority item: ${itemType.item.title}" )
            }
            is NoteItem -> {
                viewModel.insertNoteEntity(itemType.item)
                Log.d(tag, "onInsertItem(), note item: ${itemType.item.title}" )
            }
            is ChecklistItem -> {
                viewModel.insertChecklistItem(itemType.item)
                Log.d(tag, "onInsertItem(), checklist item: ${itemType.item.title}" )
            }
            else -> Log.d(tag, "onInsertItem(), ELSE, itemType not found" )
        }

        dismiss()
    }

    /**
     * @param [item] can be one of the following items:
     * Folder, Priority, Checklist, or Note
     */
    override fun onUpdateItem(itemType: EpoxyItemsInterface) {
        when (itemType) {
            is PriorityItem -> {
                viewModel.updatePriorityItem(itemType.item)
                Log.d(tag, "onUpdateItem(), priority item: ${itemType.item.title}" )
            }
            is NoteItem -> {
                viewModel.updateNoteEntity(itemType.item)
                Log.d(tag, "onUpdateItem(), note item: ${itemType.item.title}" )
            }
            is ChecklistItem -> {
                viewModel.updateChecklistItem(itemType.item)
                Log.d(tag, "onUpdateItem(), checklist item: ${itemType.item.title}" )
            }
            else -> Log.d(tag, "onUpdateItem(), ELSE, itemType not found" )
        }

        // Turn off edit mode globally by updating view model and dismiss
        viewModel.updateEditMode(isEditMode = false)
        dismiss()
    }

}