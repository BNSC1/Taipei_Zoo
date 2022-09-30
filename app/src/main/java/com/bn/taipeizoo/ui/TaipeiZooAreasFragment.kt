package com.bn.taipeizoo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.databinding.FragmentTaipeiZooAreasBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaipeiZooAreasFragment : Fragment() {
    private val viewModel: TaipeiZooViewModel by activityViewModels()

    private lateinit var listAdapter: TaipeiZooAreaListAdapter
    private var _binding: FragmentTaipeiZooAreasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaipeiZooAreasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupAreaList()
        }
        collectAreas {
            updateAdapterList(it)
        }
    }

    private fun FragmentTaipeiZooAreasBinding.setupAreaList() {
        with(areaList) {
            listAdapter = TaipeiZooAreaListAdapter {
                goToAreaDetail(it)
            }
            adapter = listAdapter
        }
    }

    private fun goToAreaDetail(area: TaipeiZooArea) {
        findNavController().navigate(
            TaipeiZooAreasFragmentDirections.actionToTaipeiZooAreaDetailFragment(
                area
            )
        )
    }

    private inline fun collectAreas(crossinline onCollectAction: (List<TaipeiZooArea>) -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.areas.collect {
                    onCollectAction(it)
                }
            }
        }

    private fun updateAdapterList(items: List<TaipeiZooArea>) {
        listAdapter.updateList(items)
    }
}