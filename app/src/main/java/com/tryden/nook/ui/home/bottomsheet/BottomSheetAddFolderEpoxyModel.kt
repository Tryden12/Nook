package com.tryden.nook.ui.home.bottomsheet

import android.widget.RadioButton
import androidx.core.view.isGone
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

/**
 * IMPORTANT: @param [folderType] provides the folder entity with the value for its folder type
 * in one of two ways..
 *
 * There are two ways the folderType value is provided:
 * 1. If the @param [folderType] IS equal to the general folder string key, then the folder
 *    entity gets the folderType value from the selected radio button label.
 * 2. Else, the folder entity folderType value is the @param [folderType] value.
 *
 * Lines 51-55.
 */

/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddFolderEpoxyModel(
    val folderType: String,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddFolderBinding>(R.layout.model_bottom_sheet_add_folder) {

    override fun ModelBottomSheetAddFolderBinding.bind() {
        // Clear the title textview and request focus
        titleEditText.setText("")
        titleEditText.requestFocus()

        // Folder (specific) bottom sheet, radio group choice is not needed
        if (folderType != NookApplication.context.getString(R.string.folder_type_general)) {
            typeTextView.isGone = true
            radioGroup.isGone = true
        }

        // Set on click for cancel (dismiss) textview
        cancelTextView.setOnClickListener {
            onAddItemSheetButtonSelected.onCancel()
        }

        // Set on click for done (save) textview
        doneTextView.setOnClickListener {
            val itemTitle = titleEditText.text.toString().trim()

            // Get radio button label of selected radio button
            val id: Int = radioGroup.checkedRadioButtonId
            val radioButton: RadioButton = radioGroup.findViewById(id)
            val folderType = if (folderType == NookApplication.context.getString(R.string.folder_type_general)) {
                radioButton.text.toString()
            } else {
                folderType
            }

            // Empty title use case
            if (itemTitle.isEmpty()) {
                titleTextField.error = NookApplication.context.getString(R.string.required_field)
                return@setOnClickListener
            }
            titleTextField.error = null


            val item = FolderEntity(
                title = itemTitle,
                type = folderType, // todo revisit?
                size =  "0" // todo: change to Int type
            )
            onAddItemSheetButtonSelected.onSaveFolder(item)
        }
    }
}