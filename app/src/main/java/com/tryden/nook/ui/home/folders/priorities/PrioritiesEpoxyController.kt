package com.tryden.nook.ui.home.folders.priorities

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.data.source.local.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.HeaderSectionTitle
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionHeaderRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionFooterRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.PriorityItem
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.DividerItem
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

        val epoxyItems = buildEpoxyList(itemEntityList)

        epoxyItems.forEachIndexed { index, epoxyItem ->
            when (epoxyItem) {
                is HeaderSectionTitle -> {
                    HeadingSectionTitleEpoxyModel(title = epoxyItem.title, showDropDown = false)
                        .id(epoxyItem.title).addTo(this)
                }
                is SectionHeaderRounded -> {
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index").addTo(this) // todo fix id?
                }
                is PriorityItem -> {
                    PriorityItemEntityEpoxyModel(
                        itemEntity = epoxyItem.item,
                        priorityItemEntityInterface = priorityItemEntityInterface
                    ).id(epoxyItem.item.id).addTo(this)
                }
                is DividerItem -> {
                    DividerEpoxyModel().id("divider-$index").addTo(this) // todo fix id?
                }
                is SectionFooterRounded -> {
                    SectionFooterRoundedEpoxyModel().id("section-footer-rounded-$index").addTo(this) // todo fix id?
                }
            }
        }
    }

    private fun getHeaderTextPriority(priority: Int) : String {
        return when (priority) {
            1 -> context.getString(R.string.low)
            2 -> context.getString(R.string.medium)
            else -> context.getString(R.string.high)
        }
    }

    private fun buildEpoxyList(priorities: ArrayList<PriorityItemEntity>): List<EpoxyItemsInterface> {
        return buildList {
            priorities.sortedByDescending {
                it.priority
            }.groupBy {
                it.priority
            }.forEach { mapEntry ->
                add(HeaderSectionTitle(title = getHeaderTextPriority(mapEntry.key)))
                add(SectionHeaderRounded)
                mapEntry.value.forEachIndexed { index, item ->
                    if (index != 0) {
                        add(DividerItem)
                    }
                    add(PriorityItem(item = item))
                }
                add(SectionFooterRounded)
            }
        }
    }
}