package com.tryden.nook.ui.home.notes.noteslist

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface.*
import com.tryden.nook.ui.epoxy.models.*

/**
 * This is the epoxy controller for the NotesListFragment.
 *
 * At the bottom of the class, we build a list of epoxy items in the buildEpoxyItems() method.
 * This simplifies the readability of the buildModels() method.
 */

class NotesListEpoxyController: EpoxyController() {

    val context = NookApplication.context

    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<NoteEntity>()
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
                title = context.getString(R.string.no_notes),
                subtitle = context.getString(R.string.how_to_add_note)
            ).id("empty-state-checklist-items").addTo(this)
        }

        val epoxyItems = buildEpoxyItems(itemEntityList)

        epoxyItems.forEachIndexed { index, epoxyItem ->
            when (epoxyItem) {
                is HeaderSectionTitle -> {
                    // todo
                }
                is SectionHeaderRounded -> {
                    SectionHeaderTopRoundEpoxyModel().id("section-header-rounded-$index")
                        .addTo(this)
                }
                is NoteItem -> {
                    // todo
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


    private fun buildEpoxyItems(notes: ArrayList<NoteEntity>): List<EpoxyItemsInterface> {
        return buildList {
            // Add rounded section topper
            add(SectionHeaderRounded)
            notes.sortedBy {
                it.title
            }.forEachIndexed { index, item ->
                if (index != 0) add(DividerItem)
                add(NoteItem(item = item))
            }
            // Add rounded section footer
            add(SectionFooterRounded)
        }
    }
}