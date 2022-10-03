package com.bn.taipeizoo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bn.taipeizoo.R
import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.databinding.FragmentTaipeiZooAnimalDetailBinding
import com.bumptech.glide.Glide

class TaipeiZooAnimalDetailFragment : Fragment() {
    private val args by navArgs<TaipeiZooAnimalDetailFragmentArgs>()
    private lateinit var selectedAnimal: TaipeiZooAnimal

    private var _binding: FragmentTaipeiZooAnimalDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaipeiZooAnimalDetailBinding.inflate(inflater, container, false)
        selectedAnimal = args.animal
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = selectedAnimal.chineseName
        with(binding) {
            Glide.with(this@TaipeiZooAnimalDetailFragment).load(selectedAnimal.pic1Url)
                .into(animalImage)
            chineseTitleText.text = selectedAnimal.chineseName
            englishTitleText.text = selectedAnimal.englishName
            setupInfoList()
        }
    }

    private fun FragmentTaipeiZooAnimalDetailBinding.setupInfoList() {
        infoList.adapter = InfoListAdapter(
            listOf(
                InfoListAdapter.Info(
                    getString(R.string.title_animal_also_known),
                    selectedAnimal.alsoKnown
                ),
                InfoListAdapter.Info(
                    getString(R.string.title_animal_feature),
                    selectedAnimal.feature
                ),
                InfoListAdapter.Info(
                    getString(R.string.title_animal_distribution),
                    selectedAnimal.distribution
                ),
                InfoListAdapter.Info(
                    getString(R.string.title_animal_behavior),
                    selectedAnimal.behavior
                ),
            ).filter {
                it.info.isNotEmpty()
            }
        )
    }
}