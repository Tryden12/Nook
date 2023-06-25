package com.tryden.nook.ui.priorities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tryden.nook.R
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.FragmentPrioritiesBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup

class PrioritiesFragment : BaseFragment(), PriorityItemEntityInterface {

    private var _binding: FragmentPrioritiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrioritiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.priorities_fragment_key)
        Log.e("PrioritiesFragment()", "onViewCreated: $tag")

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        val controller = PrioritiesEpoxyController(this)
        binding.prioritiesEpoxyRecyclerView.setController(controller)

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<PriorityItemEntity>
        }
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideKeyboard(requireView())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDelete(priorityItemEntity: PriorityItemEntity) {
        sharedViewModel.deleteItem(priorityItemEntity)
    }

    override fun onBumpPriority(priorityItemEntity: PriorityItemEntity) {
        // todo implement me
    }

}