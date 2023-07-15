package com.tryden.nook.ui.epoxy.models

import android.widget.RadioButton
import androidx.core.view.isGone
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelBottomSheetAddItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.home.bottomsheet.OnAddItemSheetButtonSelected

data class BottomSheetAddItem(
    val bottomSheetViewType: BottomSheetViewType.Type,
    val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected,
): ViewBindingKotlinModel<ModelBottomSheetAddItemBinding>(R.layout.model_bottom_sheet_add_item) {

    override fun ModelBottomSheetAddItemBinding.bind() {
        headerTitleTextView.text = bottomSheetViewType.headerTitle
        titleEditText.setText("")
        titleEditText.requestFocus()

        if (bottomSheetViewType == BottomSheetViewType.Type.CHECKLIST_ITEM) {
            typeTextView.isGone = true
            radioGroup.isGone = true
        }


        // Dismiss bottom sheet
        cancelTextView.setOnClickListener {
            onAddItemSheetButtonSelected.onCancel()
        }

        doneTextView.setOnClickListener {
            val itemTitle = titleEditText.text.toString().trim()
            val folderName = radioGroup.checkedRadioButtonId

            var id: Int = radioGroup.checkedRadioButtonId
            var rb: RadioButton = radioGroup.findViewById(id)
            var folderType = rb.text.toString()



            if (itemTitle.isEmpty()) {
                titleTextField.error = NookApplication.context.getString(R.string.required_field)
                return@setOnClickListener
            }

            titleTextField.error = null


            when (bottomSheetViewType) {
                BottomSheetViewType.Type.NONE -> {
                    // todo error state
                }

                BottomSheetViewType.Type.FOLDER -> {
                    val item = FolderEntity(
                        title = itemTitle,
                        type = folderType, // todo revisit
                        size =  "0"
                    )
                    onAddItemSheetButtonSelected.onSaveFolder(item)
                }

                BottomSheetViewType.Type.CHECKLIST_ITEM -> {
                    val item = ChecklistItemEntity(
                        title = itemTitle,
                        categoryId = "",
                        folderName = "Checklists", // todo: revisit
                        important = false,
                        completed = false
                    )
                    onAddItemSheetButtonSelected.onSaveChecklistItem(item)
                }

                else -> return@setOnClickListener
            }
        }
    }
}