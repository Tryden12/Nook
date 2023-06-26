package com.tryden.nook.ui.priorities

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.*

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
            EmptyStateEpoxyModel(
                NookApplication.context.getString(R.string.no_priorities),
                NookApplication.context.getString(R.string.how_to_add_priorities)
            ).id("empty-state-priorities").addTo(this)
            return
        }

        SectionHeaderTopRoundEpoxyModel().id("header-round-1").addTo(this)
        itemEntityList.forEachIndexed() { index, item ->
            if (index != 0) {
                DividerEpoxyModel().id("priority-${item.id}-divider").addTo(this)
            }
            PriorityItemEntityEpoxyModel(item, priorityItemEntityInterface).id(item.id).addTo(this)
        }
        SectionFooterRoundedEpoxyModel().id("footer-round-1").addTo(this)

    }
}