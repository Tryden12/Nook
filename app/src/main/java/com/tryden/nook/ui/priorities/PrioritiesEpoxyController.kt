package com.tryden.nook.ui.priorities

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.*

class PrioritiesEpoxyController(
    val priorityItemEntityInterface: PriorityItemEntityInterface
): EpoxyController() {

    val context = NookApplication.context

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
                context.getString(R.string.no_priorities),
                context.getString(R.string.how_to_add_priorities)
            ).id("empty-state-priorities").addTo(this)
            return
        }


        var currentPriority: Int = -1

        SectionHeaderTopRoundEpoxyModel().id("header-round-1").addTo(this)
        itemEntityList.sortedByDescending {
            it.priority
        }.forEachIndexed() { index, item ->
            if (item.priority != currentPriority) {
                currentPriority = item.priority
                val text = getHeaderTextPriority(currentPriority)
                HeadingSectionTitleEpoxyModel(text, showDropDown = false)
                    .id("header-priority-$text").addTo(this)
            }
            if (index != 0) {
                DividerEpoxyModel().id("priority-${item.id}-divider").addTo(this)
            }
            PriorityItemEntityEpoxyModel(item, priorityItemEntityInterface).id(item.id).addTo(this)
        }
        SectionFooterRoundedEpoxyModel().id("footer-round-1").addTo(this)

    }

    private fun getHeaderTextPriority(priority: Int) : String {
        return when (priority) {
            1 -> context.getString(R.string.low)
            2 -> context.getString(R.string.medium)
            else -> context.getString(R.string.high)
        }
    }
}