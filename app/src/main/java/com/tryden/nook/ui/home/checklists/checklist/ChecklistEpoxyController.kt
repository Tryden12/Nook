package com.tryden.nook.ui.home.checklists.checklist

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.*
import com.tryden.nook.ui.epoxy.models.*

class ChecklistEpoxyController : EpoxyController() {

    val context = NookApplication.context

    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<ChecklistItemEntity>()
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
                title = context.getString(R.string.no_checklists),
                subtitle = context.getString(R.string.how_to_add_checklist_item)
            ).id("empty-state-checklist-items").addTo(this)
        }

        val epoxyItems = buildEpoxyItems(itemEntityList)

        epoxyItems.forEachIndexed { index, epoxyItem ->
            when (epoxyItem) {
                is HeaderSectionTitle -> {
                    // do nothing
                }
                is SectionHeaderRounded -> {
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index")
                        .addTo(this)
                }
                is ChecklistItem -> {
                    ChecklistItemEpoxyModel(itemEntity = epoxyItem.item)
                        .id(epoxyItem.item.id).addTo(this)
                }
                DividerItem -> {
                    DividerEpoxyModel().id("divider-$index").addTo(this)
                }
                SectionFooterRounded -> {
                    SectionFooterRoundedEpoxyModel().id("section-footer-rounded-$index")
                        .addTo(this)
                }
            }
        }
    }

    private fun buildEpoxyItems(checklistItems: ArrayList<ChecklistItemEntity>): List<EpoxyItemsInterface> {
        return buildList {
            // Add rounded section topper
            add(SectionHeaderRounded)
            checklistItems.sortedBy {
                it.title
            }.forEachIndexed { index, item ->
                if (index != 0) add(DividerItem)
                add(ChecklistItem(item = item))
            }
            // Add rounded section footer
        }
    }
}