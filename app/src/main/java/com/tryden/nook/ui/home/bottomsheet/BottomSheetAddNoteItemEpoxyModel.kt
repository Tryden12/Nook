package com.tryden.nook.ui.home.bottomsheet

import android.widget.RadioButton
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.databinding.ModelBottomSheetAddChecklistItemBinding
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.databinding.ModelBottomSheetAddNoteItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import java.text.DateFormat

/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddNoteItemEpoxyModel(
    val currentFolderEntity: FolderEntity,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddNoteItemBinding>(R.layout.model_bottom_sheet_add_note_item) {

    override fun ModelBottomSheetAddNoteItemBinding.bind() {
        val currentTime = DateFormat.getDateInstance(3).format(System.currentTimeMillis())
        lastModifiedTextView.text = currentTime

        // Clear the title textview and request focus
        editText.requestFocus()

        // Set on click for cancel (dismiss) textview
        cancelTextView.setOnClickListener {
            onAddItemSheetButtonSelected.onCancel()
        }

        // Set on click for done (save) textview
        doneTextView.setOnClickListener {

            val itemEntity = NoteEntity(
                title = "",
                description = "",
                createdAt = System.currentTimeMillis(), // Todo: revisit these
                lastModified = System.currentTimeMillis(),
                categoryId = "",
                folderName = currentFolderEntity.title
            )
            onAddItemSheetButtonSelected.onSaveNoteItemEntity(itemEntity)
        }
    }

}