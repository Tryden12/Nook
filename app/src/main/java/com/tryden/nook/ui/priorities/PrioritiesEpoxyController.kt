package com.tryden.nook.ui.priorities

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.database.entity.PriorityItemEntity

class PrioritiesEpoxyController(
    val priorityItemEntityInterface: PriorityItemEntityInterface
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
}