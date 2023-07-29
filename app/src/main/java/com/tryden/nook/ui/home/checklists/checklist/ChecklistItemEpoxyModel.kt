package com.tryden.nook.ui.home.checklists.checklist

import android.graphics.Paint
import com.tryden.nook.R
import com.tryden.nook.database.entity.ChecklistItemEntity
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
        checkbox.isChecked = itemEntity.completed
        titleTextView.text = itemEntity.title

        checkbox.setOnCheckedChangeListener { checkBox, b ->
            val isChecked = checkBox.isChecked

//            titleTextView.paint.isStrikeThruText = isChecked
//
//            titleTextView.apply {
//                when (isChecked) {
//                    true -> paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
//                    else -> paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//                }
//            }

            /** Update check / unchecked status **/
            onCheckSelected.onCheckboxChecked(itemEntity, isChecked)
        }

        root.setOnClickListener {

            /** Update checklist item title **/
            onCheckSelected.onItemSelected(itemEntity)
        }
    }

}