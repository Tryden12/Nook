package com.tryden.nook.ui.home.folders

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.models.*
import com.tryden.nook.ui.home.OnFolderSelectedInterface

class AllFoldersEpoxyController(
    private val folderType : String?,
    private val onFolderSelected: OnFolderSelectedInterface,
): EpoxyController() {

    val context = NookApplication.context

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var folderEntitiesList = ArrayList<FolderEntity>()
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

        if (folderEntitiesList.isEmpty()) {
            EmptyStateEpoxyModel(
                title = context.getString(R.string.no_folders),
                subtitle = context.getString(R.string.how_to_add_folder)
            ).id("empty-state").addTo(this)
            return
        }

        val epoxyItems = buildEpoxyList(folderEntitiesList)

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

    private fun buildEpoxyList(folders: ArrayList<FolderEntity>): List<EpoxyItemsInterface> {

        val sectionTitle = when(folderType) {
            context.getString(R.string.priority) -> context.getString(R.string.all_priorities)
            context.getString(R.string.checklist) -> context.getString(R.string.all_notes)
            context.getString(R.string.note) -> context.getString(R.string.all_notes)
            else -> ""
        }

        return buildList {
            sectionTitle?.let {
                add(EpoxyItemsInterface.HeaderSectionTitle(title = sectionTitle))
            }
            add(EpoxyItemsInterface.SectionHeaderRounded)
            folders.sortedBy {
                it.title
            }.forEachIndexed { index, folderEntity ->
                if (index != 0) add(EpoxyItemsInterface.DividerItem)
                add(EpoxyItemsInterface.FolderItem(item = folderEntity))
            }
            add(EpoxyItemsInterface.SectionFooterRounded)
        }
    }

}