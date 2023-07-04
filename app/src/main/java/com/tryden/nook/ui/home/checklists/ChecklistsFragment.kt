package com.tryden.nook.ui.home.checklists

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tryden.nook.R
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentChecklistsBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.MainActivity
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.home.OnFolderSelectedInterface

class ChecklistsFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentChecklistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChecklistsBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.checklists_fragment_key)
        Log.e("ChecklistsFragment()", "onViewCreated: $tag")

        val mainActivity = (activity as MainActivity)

        // Bottom Sheet Type = Checklist Item
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.FOLDER)

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        val controller = ListOfChecklistsEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)


        mainActivity.viewModel.foldersLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<FolderEntity>
            mainActivity.itemCountTextView.text = "${itemEntityList.size} items to complete!"
        }
    }

    override fun onChecklistFolderSelected() {
        // todo: implement me!
    }

    override fun onNoteFolderSelected() {
        // ignore
    }
    override fun onPriorityFolderSelected() {
        // ignore
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}