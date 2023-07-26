package com.tryden.nook.ui.home.bottomsheet

import android.widget.RadioButton
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelBottomSheetAddFolderBinding
import com.tryden.nook.databinding.ModelBottomSheetAddPriorityItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel


/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddPriorityItemEpoxyModel(
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddPriorityItemBinding>(R.layout.model_bottom_sheet_add_priority_item) {


    override fun ModelBottomSheetAddPriorityItemBinding.bind() {
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
            val itemDesc = descriptionEditText.text.toString().trim()

            // Empty title use case
            if (itemTitle.isEmpty()) {
                titleTextField.error = NookApplication.context.getString(R.string.required_field)
                return@setOnClickListener
            }
            titleTextField.error = null

            // Get priority
            val priority = when (radioGroup.checkedRadioButtonId) {
                R.id.radioButtonLow -> 1
                R.id.radioButtonMedium -> 2
                R.id.radioButtonHigh -> 3
                else -> 0
            }

            // Todo: Edit mode
//            if (isEditMode) {
//                val itemEntity = selectedItemEntity!!.copy(
//                    title = itemTitle,
//                    description = itemDesc,
//                    priority = priority
//                )
//
//                onAddItemSheetButtonSelected.updatePriorityItem(itemEntity)
//                return
//            }


            val itemEntity = PriorityItemEntity(
                title = itemTitle,
                description = itemDesc,
                priority = priority
            )
            onAddItemSheetButtonSelected.onSavePriorityItem(itemEntity)
        }
    }
}