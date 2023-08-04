package com.tryden.nook.ui.home.priorities

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.models.*
import com.tryden.nook.ui.home.OnFolderSelectedInterface

/**
 * This is the epoxy controller for the AllNotesFragment.
 *
 * At the bottom of the class, we build a list of epoxy items in the buildEpoxyItems() method.
 * This simplifies the readability of the buildModels() method.
 */

class AllPrioritiesEpoxyController(
    private val onFolderSelected: OnFolderSelectedInterface
): EpoxyController() {

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
        // Loading state
        if (isLoading) {
            LoadingEpoxyModel().id("loading-state").addTo(this)
        }

        if (itemEntityList.isEmpty()) {
            EmptyStateEpoxyModel(
                title = context.getString(R.string.no_priorities),
                subtitle = context.getString(R.string.how_to_add_folder)
            ).id("empty-state-priorities").addTo(this)
            return
        }

        val epoxyItems = buildEpoxyList(itemEntityList)


        epoxyItems.forEachIndexed { index, epoxyItem ->
            when (epoxyItem) {
                is EpoxyItemsInterface.HeaderSectionTitle -> {
                    HeadingSectionTitleEpoxyModel(title = epoxyItem.title, showDropDown = false)
                        .id(epoxyItem.title).addTo(this)
                }
                is EpoxyItemsInterface.SectionHeaderRounded -> {
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index")
                        .addTo(this)
                }
                is EpoxyItemsInterface.FolderItem -> {
                    SectionFolderItemEpoxyModel(
                        folderEntity = epoxyItem.item,
                        icon = ContextCompat.getDrawable(context, R.drawable.ic_folder),
                        onFolderSelected = onFolderSelected
                    ).id(epoxyItem.item.id).addTo(this)
                }
                is EpoxyItemsInterface.DividerItem -> {
                    DividerFolderEpoxyModel().id("divider-$index")
                        .addTo(this)
                }
                is EpoxyItemsInterface.SectionFooterRounded -> {
                    SectionFooterRoundedEpoxyModel()
                        .id("section-footer-rounded-$index").addTo(this)
                }
            }
        }
    }

    private fun buildEpoxyList(noteFolders: ArrayList<FolderEntity>): List<EpoxyItemsInterface> {
        return buildList {
            add(EpoxyItemsInterface.HeaderSectionTitle(title = context.getString(R.string.priorities)))
            add(EpoxyItemsInterface.SectionHeaderRounded)
            noteFolders.sortedBy {
                it.title
            }.forEachIndexed { index, folderEntity ->
                if (index != 0) add(EpoxyItemsInterface.DividerItem)
                add(EpoxyItemsInterface.FolderItem(item = folderEntity))
            }
            add(EpoxyItemsInterface.SectionFooterRounded)
        }
    }
}