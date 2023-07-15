package com.tryden.nook.ui.home.checklists.checklist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tryden.nook.R
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentChecklistBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType

class ChecklistFragment : BaseFragment() {

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

        val controller = ChecklistEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.checklistItemEntitiesLiveData.observe(viewLifecycleOwner) { list ->
            val items = list.filter { item ->
                item.folderName == safeArgs.folderTitle
            }

            if (items.isEmpty()) {
                mainActivity.itemCountTextView.text = "Nothing to complete!"
            } else {
                mainActivity.itemCountTextView.text = "${items.size} items to complete!"
            }

            controller.itemEntityList = items as ArrayList<ChecklistItemEntity>
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}