package com.bn.taipeizoo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.databinding.ItemTaipeiZooAnimalBinding
import com.bumptech.glide.Glide

class TaipeiZooAnimalListAdapter(
    private val onItemClickAction: (TaipeiZooAnimal) -> Unit
) : RecyclerView.Adapter<TaipeiZooAnimalListAdapter.TaipeiZooAnimalViewHolder>() {
    private var items = emptyList<TaipeiZooAnimal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaipeiZooAnimalViewHolder {
        return TaipeiZooAnimalViewHolder(
            ItemTaipeiZooAnimalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClickAction
        )
    }

    override fun onBindViewHolder(holder: TaipeiZooAnimalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(items: List<TaipeiZooAnimal>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    class TaipeiZooAnimalViewHolder(
        private val binding: ItemTaipeiZooAnimalBinding,
        private val onItemClickAction: (TaipeiZooAnimal) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(item: TaipeiZooAnimal) {
            with(binding) {
                item.pic1Url.let { url ->
                    if (url.isNotEmpty()) {
                        Glide.with(root).load(url).centerCrop().into(image)
                    }
                }
                titleText.text = item.chineseName
                infoText.text = item.alsoKnown

                root.setOnClickListener {
                    onItemClickAction(item)
                }
            }
        }
    }
}