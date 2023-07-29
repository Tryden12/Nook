package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
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
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import java.text.DateFormat

/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddNoteItemEpoxyModel(
    val editMode: Boolean,
    val currentFolderEntity: FolderEntity,
    val currentNoteEntity: NoteEntity,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddNoteItemBinding>(R.layout.model_bottom_sheet_add_note_item) {

    override fun ModelBottomSheetAddNoteItemBinding.bind() {
        val currentTime = DateFormat.getDateInstance(3).format(System.currentTimeMillis())

        // Check if user is editing an item, or adding a new item
        when (editMode) {
            true -> {
                // Update header title
                headerTitleTextView.text = NookApplication.context.getString(R.string.update_item)
                // Fill the title textview and request focus
                titleEditText.setText(currentNoteEntity.title)
                titleEditText.requestFocus()
                descriptionEditText.setText(currentNoteEntity.description)
                Log.e("BottomSheetAddNote", "Editing note: ${currentNoteEntity.title}" )
            }
            else -> {
                // Update header title
                headerTitleTextView.text = NookApplication.context.getString(R.string.new_note)
                // Clear the title textview and request focus
                titleEditText.setText("")
                titleEditText.requestFocus()

                descriptionEditText.setText("")
            }
        }
        lastModifiedTextView.text = currentTime

        // Set on click for cancel (dismiss) textview
        cancelTextView.setOnClickListener {
            onAddItemSheetButtonSelected.onCancel()
        }

        // Set on click for done (save) textview
        doneTextView.setOnClickListener {

            val noteTitle = if (titleEditText.text.toString().trim().isEmpty()) {
                NookApplication.context.resources.getString(R.string.new_note)
            } else {
                titleEditText.text.toString().trim()
            }

            val noteDesc = if (descriptionEditText.text.toString().trim().isEmpty()) {
                NookApplication.context.resources.getString(R.string.no_text)
            } else {
                descriptionEditText.text.toString().trim()
            }

            when (editMode) {
                true -> {
                    /**
                     * To update the entity, we choose to use copy() on the selected entity.
                     * EpoxyItemsInterface.NoteItem gives the entity a type for the param in
                     * onUpdateItem(entity), so onUpdateItem(entity) can decide to update the correct
                     * entity in the correct database table.
                     */
                    val entity = EpoxyItemsInterface.NoteItem(
                        currentNoteEntity.copy(
                            title = noteTitle,
                            description = noteDesc,
                            lastModified = System.currentTimeMillis(),
                            categoryId = "",
                            folderName = currentFolderEntity.title
                        )
                    )
                    onAddItemSheetButtonSelected.onUpdateItem(entity)
                }
                else -> {
                    /**
                     * Wrap the NoteEntity in an EpoxyItemsInterface type.
                     * At Fragment level, onInsertItem() will determine the type of item
                     * using a when statement.
                     */
                    val entity = EpoxyItemsInterface.NoteItem(
                        NoteEntity(
                            title = noteTitle,
                            description = noteDesc,
                            createdAt = System.currentTimeMillis(), /** When updating note, leave this blank **/
                            lastModified = System.currentTimeMillis(),
                            categoryId = "",
                            folderName = currentFolderEntity.title
                        )
                    )
                    // Insert Note
                    onAddItemSheetButtonSelected.onInsertItem(entity)
                }
            }
        }
    }

}