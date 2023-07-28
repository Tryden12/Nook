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
        Log.e("AddItemSheet()", "onViewCreated: $tag")

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

    override fun onSaveFolder(item: FolderEntity) {
        viewModel.insertFolder(item)
        viewModel.collectAllItems()
        dismiss()
    }

    override fun onSaveChecklistItemEntity(item: ChecklistItemEntity) {
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
            Log.d(tag, "onSaveChecklistItem() ${selectedFolderEntity.title} from $oldSize to $newSize" )
        } else {
            Log.d(tag, "onSaveChecklistItem() null folder entity" )
        }

        viewModel.insertChecklistItem(item)
        viewModel.getAllChecklistItems()
        dismiss()
    }

    override fun onUpdatePriorityItem(itemEntity: PriorityItemEntity) {

        viewModel.updatePriorityItem(itemEntity)
    }

    override fun onSavePriorityItem(item: PriorityItemEntity, editMode: Boolean) {
        when (editMode) {
            true -> {
                viewModel.updatePriorityItem(item)
                // Turn off edit mode globally, update view model
                viewModel.updateEditMode(isEditMode = false)
                dismiss()
            }
            else -> {
                viewModel.insertPriorityItem(item)
                viewModel.getAllPriorityEntities()
                dismiss()
            }
        }

    }

    override fun onSaveNoteItemEntity(item: NoteEntity) {
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
            Log.d(tag, "onSaveChecklistItem() ${selectedFolderEntity.title} from $oldSize to $newSize" )
        } else {
            Log.d(tag, "onSaveChecklistItem() null folder entity" )
        }

        viewModel.insertNoteEntity(item)
        viewModel.getAllNotes()
        dismiss()
    }


}