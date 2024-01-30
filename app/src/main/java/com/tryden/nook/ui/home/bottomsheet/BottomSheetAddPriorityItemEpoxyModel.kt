package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.data.source.local.entity.FolderEntity
import com.tryden.nook.data.source.local.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelBottomSheetAddPriorityItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface


/**
 * This epoxy model provides the layout file for AddItemSheet() bottom sheet.
 */

class BottomSheetAddPriorityItemEpoxyModel(
    val editMode: Boolean,
    val currentPrioritySelected: PriorityItemEntity,
    val currentFolderEntity: FolderEntity,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): ViewBindingKotlinModel<ModelBottomSheetAddPriorityItemBinding>(R.layout.model_bottom_sheet_add_priority_item) {


    override fun ModelBottomSheetAddPriorityItemBinding.bind() {
        Log.d("BottomSheetAddPriorityItemEpoxyModel()", "Folder name: ${currentFolderEntity.title}")

        // Check if user is editing an item, or adding a new item
        when (editMode) {
            true -> {
                // Fill the title textview and request focus
                titleEditText.setText(currentPrioritySelected.title)
                titleEditText.requestFocus()

                // Fill the description textview and check correct priority
                descriptionEditText.setText(currentPrioritySelected.description)
                when (currentPrioritySelected.priority) {
                    1 -> radioGroup.check(R.id.radioButtonLow)
                    2 -> radioGroup.check(R.id.radioButtonMedium)
                    else -> radioGroup.check(R.id.radioButtonHigh)
                }
            }
            else -> {
                // Clear the TextViews and request focus to title TextView
                titleEditText.setText("")
                descriptionEditText.setText("")
                radioGroup.check(R.id.radioButtonLow)
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

            // Form priority entity
            val itemEntity = currentPrioritySelected.copy(
                title = itemTitle,
                description = itemDesc,
                priority = priority
            )

            when (editMode) {
                true -> {
                    /**
                     * To update the entity, we choose to use copy() on the selected entity.
                     * EpoxyItemsInterface.PriorityItem() gives the entity a type for the param in
                     * onUpdateItem(entity), so onUpdateItem(entity) can decide to update the correct
                     * entity in the correct database table.
                     */
                    val entity = EpoxyItemsInterface.PriorityItem(
                        currentPrioritySelected.copy(
                            title = itemTitle,
                            description = itemDesc,
                            priority = priority,
                            folderName = currentFolderEntity.title
                        ))
                    onAddItemSheetButtonSelected.onUpdateItem(entity)
                }
                else -> {
                    /**
                     * Wrap the PriorityItemEntity in an EpoxyItemsInterface type.
                     * At Fragment level, onInsertItem() will determine the type of item
                     * using a when statement.
                     */
                    val entity = EpoxyItemsInterface.PriorityItem(
                        PriorityItemEntity(
                            title = itemTitle,
                            description = itemDesc,
                            priority = priority,
                            folderName = currentFolderEntity.title
                        )
                    )
                    // Insert priority
                    onAddItemSheetButtonSelected.onInsertItem(entity)
                }
            }
        }
    }
}