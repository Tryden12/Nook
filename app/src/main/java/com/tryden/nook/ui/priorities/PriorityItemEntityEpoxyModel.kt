package com.tryden.nook.ui.priorities

import com.tryden.nook.R
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class PriorityItemEntityEpoxyModel(
    val priorityItemEntity: PriorityItemEntity,
    val priorityItemEntityInterface: PriorityItemEntityInterface
): ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

    override fun ModelItemEntityBinding.bind() {
        titleTextView.text = priorityItemEntity.title
        lastModifiedTextView.text = priorityItemEntity.lastModified.toString()
        descriptionTextView.text = priorityItemEntity.description

        priorityTextView.setOnClickListener {
            priorityItemEntityInterface.onBumpPriority(priorityItemEntity)
        }
    }
}