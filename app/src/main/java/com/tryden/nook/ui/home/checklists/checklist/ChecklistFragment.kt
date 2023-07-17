package com.tryden.nook.ui.home.checklists.checklist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tryden.nook.R
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentChecklistBinding
import com.tryden.nook.databinding.ModelChecklistItemEntityBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.SectionFolderItemEpoxyModel

class ChecklistFragment : BaseFragment(), OnCheckSelected {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the folder title string value needed to filter
     * display only those checklist items.
     *
     * This is passed in from HomeFragment() or ChecklistFragment() via
     * NavDirections.
     */
    private val safeArgs: ChecklistFragmentArgs by navArgs()
    private val selectedFolderEntity: FolderEntity? by lazy {
        sharedViewModel.foldersLiveData.value?.find {
            it.title == safeArgs.folderTitle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.checklist_fragment_key)
        Log.d("ChecklistsFragment()", "onViewCreated: $tag")

        // Bottom Sheet Type = Checklist Item
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.CHECKLIST_ITEM)

        BottomToolbarSetup(
            fragmentKey = mainActivity.getString(R.string.checklist_fragment_key),
            activity = mainActivity
        ).bottomToolbarSetup()

        val controller = ChecklistEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        mainActivity.supportActionBar?.title = safeArgs.folderTitle

        sharedViewModel.checklistItemEntitiesLiveData.observe(viewLifecycleOwner) { list ->
            val items = list.filter { item ->
                item.folderName == safeArgs.folderTitle
            }

            when (items.size) {
                0 -> {
                    mainActivity.itemCountTextView.text = "Nothing to complete!"
                }
                1 -> {
                    mainActivity.itemCountTextView.text = "${items.size} item"
                }
                else -> {
                    mainActivity.itemCountTextView.text = "${items.size} items"
                }
            }

            controller.itemEntityList = items as ArrayList<ChecklistItemEntity>
        }

        swipeToDeleteSetup()

    }

    private fun swipeToDeleteSetup() {
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(ChecklistItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<ChecklistItemEpoxyModel>() {

                override fun onSwipeCompleted(
                    model: ChecklistItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int,
                ) {
                    val checklistItem = model?.itemEntity?: return

                    sharedViewModel.deleteChecklistItem(checklistItem)

                    // Decrease folder size by 1
                    val folderEntity = selectedFolderEntity!!.copy(
                        title = selectedFolderEntity!!.title,
                        type = selectedFolderEntity!!.type,
                        size = if (selectedFolderEntity!!.size > 0) {
                            selectedFolderEntity!!.size - 1
                        } else {
                            0
                        }
                    )
                    sharedViewModel.updateFolder(folderEntity)
                    Log.d(tag, "Folder ${selectedFolderEntity!!.title} size: ${selectedFolderEntity!!.size}")
                }

            })
    }

    override fun onCheckboxChecked(itemEntity: ChecklistItemEntity, isChecked: Boolean) {
        val checklistItemEntity = itemEntity.copy(
            id = itemEntity.id,
            title = itemEntity.title,
            folderName = itemEntity.folderName,
            important = itemEntity.important,
            completed = isChecked,
            categoryId = itemEntity.categoryId
        )
        sharedViewModel.updateChecklistItem(checklistItemEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}