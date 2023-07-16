package com.tryden.nook.ui.home.bottomsheet

import android.widget.RadioButton
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelBottomSheetAddChecklistItemBinding
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddChecklistItemEpoxyModel(
    val currentFolderEntity: FolderEntity,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddChecklistItemBinding>(R.layout.model_bottom_sheet_add_checklist_item) {

    override fun ModelBottomSheetAddChecklistItemBinding.bind() {
        // Clear the title textview and request focus
        titleEditText.setText("")
        titleEditText.requestFocus()

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


            val itemEntity = ChecklistItemEntity(
                title = itemTitle,
                folderName = currentFolderEntity.title,
                completed = false
            )
            onAddItemSheetButtonSelected.onSaveChecklistItem(itemEntity)
        }
    }
}