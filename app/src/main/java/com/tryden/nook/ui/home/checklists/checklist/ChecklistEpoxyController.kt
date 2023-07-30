package com.tryden.nook.ui.home.checklists.checklist

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.*
import com.tryden.nook.ui.epoxy.models.*
import com.tryden.nook.ui.home.OnItemSelected

class ChecklistEpoxyController(
    val onCheckSelected: OnCheckSelected
) : EpoxyController() {

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
        Log.d("controller", "buildModels() based on checklistItemEntitiesLiveData")

        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (itemEntityList.isEmpty()) {
            EmptyStateEpoxyModel(
                title = context.getString(R.string.no_checklist_items),
                subtitle = context.getString(R.string.how_to_add_checklist_item)
            ).id("empty-state-checklist-items").addTo(this)
            Log.d("ChecklistEpoxyController()", "buildModels() based on checklistItemEntitiesLiveData")
        }

        val epoxyItems = buildEpoxyItems(itemEntityList)

        epoxyItems.forEachIndexed { index, epoxyItem ->
            when (epoxyItem) {
                is HeaderSectionTitle -> {
                    HeadingSectionTitleEpoxyModel(title = epoxyItem.title, showDropDown = false)
                        .id(epoxyItem.title).addTo(this)
                }
                is SectionHeaderRounded -> {
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index")
                        .addTo(this)
                }
                is ChecklistItem -> {
                    ChecklistItemEpoxyModel(
                        itemEntity = epoxyItem.item,
                        onCheckSelected = onCheckSelected
                    ).id(epoxyItem.item.title).addTo(this)
                    Log.d("ChecklistEpoxyController()", "buildModel: ${epoxyItem.item.title}" )
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
            // Add Header title if list is not empty
            if (checklistItems.isNotEmpty()) {
                val headerTitle = checklistItems[0].folderName + " " +  context.getString(R.string.checklist)
                add(HeaderSectionTitle(title = headerTitle))
            }
            // Add rounded section topper
            add(SectionHeaderRounded)
            // Add Checklist Items
            checklistItems.sortedBy {
                it.title
            }.forEachIndexed { index, item ->
                if (index != 0) add(DividerItem)
                add(ChecklistItem(item = item))
            }
            // Add rounded section footer
            add(SectionFooterRounded)
        }
    }
}