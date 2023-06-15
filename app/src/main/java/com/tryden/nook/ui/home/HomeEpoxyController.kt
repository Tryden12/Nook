package com.tryden.nook.ui.home

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val priorityItemEntityInterface: PriorityItemEntityInterface
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<PriorityItemEntity>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            // todo loading state
            return
        }

        if (itemEntityList.isEmpty()) {
            // todo empty state
            return
        }

        itemEntityList.forEach { item ->

        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: PriorityItemEntity,
        val priorityItemEntityInterface: PriorityItemEntityInterface
    ): ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.title
            lastModifiedTextView.text = itemEntity.lastModified.toString() // todo revisit
            descriptionTextView.text = itemEntity.description

            priorityTextView.setOnClickListener {
                priorityItemEntityInterface.onBumpPriority(itemEntity)
            }
        }
    }
}