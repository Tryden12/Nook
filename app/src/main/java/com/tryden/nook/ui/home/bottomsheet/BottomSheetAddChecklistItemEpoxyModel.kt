package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import android.widget.RadioButton
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelBottomSheetAddChecklistItemBinding
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface

/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddChecklistItemEpoxyModel(
    val editMode: Boolean,
    val currentFolderEntity: FolderEntity,
    val currentChecklistItemSelected: ChecklistItemEntity,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddChecklistItemBinding>(R.layout.model_bottom_sheet_add_checklist_item) {

    override fun ModelBottomSheetAddChecklistItemBinding.bind() {

        when (editMode) {
            true -> {
                // Update header title
                headerTitleTextView.text = NookApplication.context.getString(R.string.update_item)
                // Update the title textview and request focus
                titleEditText.setText(currentChecklistItemSelected.title)
                titleEditText.requestFocus()
                Log.e("BottomSheetAddNote", "Editing checklist: ${currentChecklistItemSelected.title}" )
            }
            else -> {
                // Clear the title textview and request focus
                titleEditText.setText("")
                titleEditText.requestFocus()
            }
        }


        // Set on click for cancel (dismiss) textview
        cancelTextView.setOnClickListener {
            onAddItemSheetButtonSelected.onCancel()
        }

        // Set on click for done (save) textview
        doneTextView.setOnClickListener {
            val itemTitle = titleEditText.text.toString().trim()

            // Empty title use case
            if (itemTitle.isEmpty()) {
                titleTextField.error = NookApplication.context.getString(R.string.required_field)
                return@setOnClickListener
            }
            titleTextField.error = null


            when (editMode) {
                true -> {
                    /**
                     * To update the entity, we choose to use copy() on the selected entity.
                     * EpoxyItemsInterface.NoteItem gives the entity a type for the param in
                     * onUpdateItem(entity), so onUpdateItem(entity) can decide to update the correct
                     * entity in the correct database table.
                     */
                    val entity = EpoxyItemsInterface.ChecklistItem(
                        currentChecklistItemSelected.copy(
                            title = itemTitle,
                            folderName = currentFolderEntity.title,
                            completed = currentChecklistItemSelected.completed
                        )
                    )
                    onAddItemSheetButtonSelected.onUpdateItem(entity)
                } else -> {
                /**
                 * Wrap the ChecklistItemEntity in an EpoxyItemsInterface type.
                 * At Fragment level, onInsertItem() will determine the type of item
                 * using a when statement.
                 */
                val entity = EpoxyItemsInterface.ChecklistItem(
                    ChecklistItemEntity(
                        title = itemTitle,
                        folderName = currentFolderEntity.title,
                        completed = false
                    ))
                onAddItemSheetButtonSelected.onInsertItem(entity)
                }
            }

        }
    }
}