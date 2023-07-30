package com.tryden.nook.ui.home.checklists

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionFooterRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionHeaderRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.HeaderSectionTitle
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.FolderItem
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.DividerItem
import com.tryden.nook.ui.epoxy.models.*
import com.tryden.nook.ui.home.OnFolderSelectedInterface


/**
 * This is the epoxy controller for the AllChecklistsFragment.
 *
 * At the bottom of the class, we build a list of epoxy items in the buildEpoxyItems() method.
 * This simplifies the readability of the buildModels() method.
 */

class AllChecklistsEpoxyController(
    private val onFolderSelected: OnFolderSelectedInterface
) : EpoxyController()  {

    val context = NookApplication.context

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<FolderEntity>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }


    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading-state").addTo(this)
            return
        }

        if (itemEntityList.isEmpty()) {
            EmptyStateEpoxyModel(
                title = context.getString(R.string.no_checklists),
                subtitle = context.getString(R.string.how_to_add_checklist)
            ).id("empty-state-checklists").addTo(this)
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
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index")
                        .addTo(this)
                }
                is FolderItem -> {
                    SectionFolderItemEpoxyModel(
                        folderEntity = epoxyItem.item,
                        icon = ContextCompat.getDrawable(context, R.drawable.ic_folder),
                        onFolderSelected = onFolderSelected
                    ).id(epoxyItem.item.id).addTo(this)
                }
                is DividerItem -> {
                    DividerFolderEpoxyModel().id("divider-$index")
                        .addTo(this)
                }
                is SectionFooterRounded -> {
                    SectionFooterRoundedEpoxyModel()
                        .id("section-footer-rounded-$index").addTo(this)
                }
            }
        }
    }

    private fun buildEpoxyList(checklists: ArrayList<FolderEntity>): List<EpoxyItemsInterface> {
        return buildList {
            // Add Header title
            add(HeaderSectionTitle(title = context.getString(R.string.checklists)))
            // Add section topper rounded
            add(SectionHeaderRounded)
            // Add Checklist folders
            checklists.sortedBy {
                it.title
            }.forEachIndexed { index, item ->
                if (index != 0) {
                    add(DividerItem)
                }
                add(FolderItem(item = item))
            }
            // Add section footer rounded
            add(SectionFooterRounded)
        }
    }
}