package com.tryden.nook.ui.priorities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentPrioritiesBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup

class PrioritiesFragment : BaseFragment() {

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

        sharedViewModel.priorityItemEntitiesLiveData.observe(viewLifecycleOwner) {
            // todo

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}