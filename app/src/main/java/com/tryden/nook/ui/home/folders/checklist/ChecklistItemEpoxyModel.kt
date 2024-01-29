package com.tryden.nook.ui.home.folders.checklist

import android.graphics.Paint
import android.util.Log
import com.tryden.nook.R
import com.tryden.nook.data.database.entity.ChecklistItemEntity
import com.tryden.nook.databinding.ModelChecklistItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.home.OnItemSelected


/**
 * This epoxy model provides the layout file for checklist item entity in ChecklistFragment().
 */

class ChecklistItemEpoxyModel(
    val itemEntity: ChecklistItemEntity,
    val onCheckSelected: OnCheckSelected
): ViewBindingKotlinModel<ModelChecklistItemEntityBinding>(R.layout.model_checklist_item_entity) {

    override fun ModelChecklistItemEntityBinding.bind() {
        Log.e("ChecklistItemEpoxyModel", "buildmodel: ${itemEntity.title}" )
        titleTextView.text = itemEntity.title
        checkbox.isChecked = itemEntity.completed

        checkbox.setOnCheckedChangeListener { checkBox, b ->
            val isChecked = checkBox.isChecked

            /** Update check / unchecked status **/
            onCheckSelected.onCheckboxChecked(itemEntity, isChecked)
        }

        root.setOnClickListener {

            /** Update checklist item title **/
            onCheckSelected.onItemSelected(itemEntity)
        }
    }

}