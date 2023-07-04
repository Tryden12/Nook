package com.tryden.nook.ui.home.checklists

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionFooterRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.SectionHeaderRounded
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.HeaderSectionTitle
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.ChecklistItem
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.FolderItem
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.DividerItem
import com.tryden.nook.ui.epoxy.models.*
import com.tryden.nook.ui.home.OnFolderSelectedInterface


class ListOfChecklistsEpoxyController(
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
                    SectionFooterRoundedEpoxyModel().id("section-footer-rounded-$index").addTo(this)
                }
            }
        }
    }

    private fun buildEpoxyList(checklists: ArrayList<FolderEntity>): List<EpoxyItemsInterface> {
        return buildList {
            add(HeaderSectionTitle(title = context.getString(R.string.checklists)))
            add(SectionHeaderRounded)
            checklists.sortedBy {
                it.title
            }.forEachIndexed { index, item ->
                if (index != 0) {
                    add(DividerItem)
                }
                add(FolderItem(item = item))
            }
            add(SectionFooterRounded)
        }
    }
}