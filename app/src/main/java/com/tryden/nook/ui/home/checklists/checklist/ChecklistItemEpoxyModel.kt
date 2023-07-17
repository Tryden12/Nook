package com.tryden.nook.ui.home.checklists.checklist

import com.tryden.nook.R
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.databinding.ModelChecklistItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel


/**
 * This epoxy model provides the layout file for checklist item entity in ChecklistFragment().
 */

class ChecklistItemEpoxyModel(
    val itemEntity: ChecklistItemEntity,
    val onCheckSelected: OnCheckSelected
): ViewBindingKotlinModel<ModelChecklistItemEntityBinding>(R.layout.model_checklist_item_entity) {

    override fun ModelChecklistItemEntityBinding.bind() {
        checkbox.isChecked = itemEntity.completed
        titleTextView.text = itemEntity.title

        checkbox.setOnCheckedChangeListener { compoundButton, b ->
            val isChecked = checkbox.isChecked
            onCheckSelected.onCheckboxChecked(itemEntity, isChecked)
        }
    }

}