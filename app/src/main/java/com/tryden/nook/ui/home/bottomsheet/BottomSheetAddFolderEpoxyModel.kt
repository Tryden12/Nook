package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import android.widget.RadioButton
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface

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

        if (folderType == NookApplication.context.getString(R.string.folder_type_general)) { /** Folder type general, display radio group  **/
            typeTextView.isVisible = true
            radioGroup.isVisible = true
        } else { /** Folder type specific, hide radio group  **/
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
            Log.d("BottomSheetAddFolderEpoxyModel()", folderType )


            // Empty title use case
            if (itemTitle.isEmpty()) {
                titleTextField.error = NookApplication.context.getString(R.string.required_field)
                return@setOnClickListener
            }
            titleTextField.error = null


            /**
             * Wrap the FolderEntity in an EpoxyItemsInterface type.
             * At Fragment level, these methods will determine the type of item using a when statement.
             */
            val entity = EpoxyItemsInterface.FolderItem(
                FolderEntity(
                    title = itemTitle,
                    type = folderType,
                    size =  0
                )
            )
            // Insert folder
            onAddItemSheetButtonSelected.onInsertItem(entity)
        }
    }
}