package com.tryden.nook.ui.priorities

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.LoadingEpoxyModel
import com.tryden.nook.ui.epoxy.models.SectionFooterRoundedEpoxyModel
import com.tryden.nook.ui.epoxy.models.SectionHeaderTopRoundEpoxyModel

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
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (itemEntityList.isEmpty()) {
            // todo empty state
            return
        }

        SectionHeaderTopRoundEpoxyModel().id("header-round-1").addTo(this)
        itemEntityList.forEach { item ->
            PriorityItemEntityEpoxyModel(item, priorityItemEntityInterface).id(item.id).addTo(this)
        }
        SectionFooterRoundedEpoxyModel().id("footer-round-1").addTo(this)

    }
}