package com.tryden.nook.ui.home.folders.priorities

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyTouchHelper
import com.airbnb.epoxy.EpoxyTouchHelper.SwipeCallbacks
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.FragmentPrioritiesBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet

class PrioritiesFragment : BaseFragment(), PriorityItemEntityInterface {

    private var _binding: FragmentPrioritiesBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the folder title string value needed to filter
     * display only those note items.
     *
     * This is passed in from HomeFragment() or AllNotesFoldersFragment() via
     * NavDirections.
     *
     */
    private val safeArgs: PrioritiesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrioritiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.priorities_fragment_key)
        Log.d("PrioritiesFragment()", "onViewCreated: $tag")

        // Setup Bottom Toolbar
        setupBottomToolbar()

        // Bottom Sheet Type = Note Item
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.PRIORITY)

        val controller = PrioritiesEpoxyController(this)
        binding.prioritiesEpoxyRecyclerView.setController(controller)

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<PriorityItemEntity>
            mainActivity.itemCountTextView.text = "${itemEntityList.size} Priorities"
        }

        // Setup swipe-to-delete
        swipeToDeleteSetup()
    }

    private fun setupBottomToolbar() {
        // Show correct toolbar, hide others
        mainActivity.bottomToolbarItemsList.isVisible = true
        mainActivity.bottomToolbarHome.isInvisible = true
        mainActivity.bottomToolbarEditItem.isInvisible = true

        // OnClick listeners
        mainActivity.addItemImageViewItemsList.setOnClickListener {
            Log.d(tag, "addItemImageViewItemsList clicked from PrioritiesFragment", )
            AddItemSheet().show(mainActivity.supportFragmentManager, null)
        }
    }

    override fun onBumpPriority(priorityItemEntity: PriorityItemEntity) {
        val currentPriority = priorityItemEntity.priority
        var newPriority = currentPriority + 1
        if (newPriority > 3) {
            newPriority = 1
        }

        val updatedPriorityItemEntity = priorityItemEntity.copy(priority = newPriority)
        sharedViewModel.updatePriorityItem(updatedPriorityItemEntity)
    }

    override fun onItemSelected(priorityItemEntity: PriorityItemEntity) {
        sharedViewModel.updateCurrentPriorityItemSelected(priorityItemEntity)
        sharedViewModel.updateEditMode(isEditMode = true)
        AddItemSheet().show(mainActivity.supportFragmentManager, null)
    }

    private fun swipeToDeleteSetup() {
        EpoxyTouchHelper.initSwiping(binding.prioritiesEpoxyRecyclerView)
            .right()
            .withTarget(PriorityItemEntityEpoxyModel::class.java)
            .andCallbacks(object : SwipeCallbacks<PriorityItemEntityEpoxyModel>() {

                override fun onSwipeCompleted(
                    model: PriorityItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int,
                ) {
                    val itemRemoved = model?.itemEntity ?: return

                    // Get folder associated with note entity
                    val selectedFolderEntity: FolderEntity? =
                        sharedViewModel.foldersLiveData.value?.find {
                            it.title == safeArgs.folderTitle
                        }

                    sharedViewModel.deleteItem(itemRemoved)

                    if (selectedFolderEntity != null) {
                        // Decrease folder size by 1
                        val folderEntity = selectedFolderEntity.copy(
                            title = selectedFolderEntity.title,
                            type = selectedFolderEntity.type,
                            size = if (selectedFolderEntity.size > 0) {
                                selectedFolderEntity.size - 1
                            } else {
                                0
                            }
                        )
                        sharedViewModel.updateFolder(folderEntity)
                    } else {
                        Log.d(tag, "swipeToDeleteSetup() null folder entity" )
                    }
                    Log.d(tag, "Folder ${selectedFolderEntity!!.title} size: ${selectedFolderEntity!!.size}")

                }

                override fun onSwipeProgressChanged(
                    model: PriorityItemEntityEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
//                    itemView?.findViewById<AppCompatImageView>(R.id.deleteButtonImageView)?.apply {
//                        isVisible = true
//                        translationX = -itemView.translationX
//                        alpha = 5f * swipeProgress
//                    }
                }

            })
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideKeyboard(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}