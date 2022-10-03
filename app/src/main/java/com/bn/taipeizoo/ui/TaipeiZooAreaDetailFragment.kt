package com.bn.taipeizoo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.databinding.FragmentTaipeiZooAreaDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaipeiZooAreaDetailFragment : Fragment() {
    private val viewModel: TaipeiZooViewModel by activityViewModels()
    private val args by navArgs<TaipeiZooAreaDetailFragmentArgs>()
    private lateinit var selectedArea: TaipeiZooArea

    private lateinit var listAdapter: TaipeiZooAnimalListAdapter
    private var _binding: FragmentTaipeiZooAreaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaipeiZooAreaDetailBinding.inflate(inflater, container, false)
        selectedArea = args.area
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = selectedArea.name
        with(binding) {
            setupAreaDetail()
            setupAnimalList()
        }
    }

    private fun FragmentTaipeiZooAreaDetailBinding.setupAreaDetail() {
        selectedArea.picUrl.let { url ->
            if (url.isNotEmpty()) {
                Glide.with(this@TaipeiZooAreaDetailFragment).load(url).into(areaImage)
            }
        }
        infoText.text = selectedArea.info
        categoryText.text = selectedArea.category
        selectedArea.url.let { url ->
            if (url.isNotEmpty()) {
                openInBrowserBtn.setOnClickListener {
                    openInBrowser(url)
                }
            } else {
                openInBrowserBtn.visibility = View.GONE
            }
        }
    }

    private fun openInBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun FragmentTaipeiZooAreaDetailBinding.setupAnimalList() {
        getAreaAnimals(selectedArea)?.let { areaAnimals ->
            if (areaAnimals.isEmpty()) {
                animalInfoText.visibility = View.GONE
            }
            with(animalList) {
                listAdapter = TaipeiZooAnimalListAdapter {
                    goToAnimalDetail(it)
                }
                adapter = listAdapter
                updateListAnimals(areaAnimals)
            }
        }
    }

    private fun goToAnimalDetail(animal: TaipeiZooAnimal) {
        findNavController().navigate(
            TaipeiZooAreaDetailFragmentDirections.actionToTaipeiZooAnimalDetailFragment(
                animal
            )
        )
    }

    private fun getAreaAnimals(area: TaipeiZooArea) = viewModel.getAreaAnimals(area)

    private fun updateListAnimals(items: List<TaipeiZooAnimal>) {
        listAdapter.updateList(items)
    }

}