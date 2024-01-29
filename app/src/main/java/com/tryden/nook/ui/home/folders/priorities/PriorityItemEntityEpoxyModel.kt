package com.tryden.nook.ui.home.folders.priorities

import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.data.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class PriorityItemEntityEpoxyModel(
    val itemEntity: PriorityItemEntity,
    val priorityItemEntityInterface: PriorityItemEntityInterface
): ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

    override fun ModelItemEntityBinding.bind() {
        titleTextView.text = itemEntity.title
        lastModifiedTextView.text = itemEntity.lastModifiedFormatted

        if (itemEntity.description == null) {
            descriptionTextView.isInvisible = true
        } else {
            descriptionTextView.isVisible = true
            descriptionTextView.text = itemEntity.description
        }

        priorityTextView.setOnClickListener {
            priorityItemEntityInterface.onBumpPriority(itemEntity)
        }

        val colorRes = when (itemEntity.priority) {
            1 -> android.R.color.holo_green_dark
            2 -> android.R.color.holo_orange_dark
            3 -> android.R.color.holo_red_dark
            else -> R.color.color_accent
        }

        priorityTextView.setTextColor(ContextCompat.getColor(root.context, colorRes))

        root.setOnClickListener {
            priorityItemEntityInterface.onItemSelected(itemEntity)
        }
    }
}