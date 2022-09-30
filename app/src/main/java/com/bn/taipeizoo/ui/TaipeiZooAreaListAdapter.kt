package com.bn.taipeizoo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.databinding.ItemTaipeiZooAreaBinding
import com.bumptech.glide.Glide

class TaipeiZooAreaListAdapter(
    private val onItemClickAction: (TaipeiZooArea) -> Unit
) : RecyclerView.Adapter<TaipeiZooAreaListAdapter.TaipeiZooAreaViewHolder>() {
    private var items = emptyList<TaipeiZooArea>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaipeiZooAreaViewHolder {
        return TaipeiZooAreaViewHolder(
            ItemTaipeiZooAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClickAction
        )
    }

    override fun onBindViewHolder(holder: TaipeiZooAreaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(items: List<TaipeiZooArea>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    class TaipeiZooAreaViewHolder(
        private val binding: ItemTaipeiZooAreaBinding,
        private val onItemClickAction: (TaipeiZooArea) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(item: TaipeiZooArea) {
            with(binding) {
                item.picUrl.let { url ->
                    if (url.isNotEmpty()) {
                        Glide.with(root).load(url).centerCrop().into(image)
                    }
                }
                titleText.text = item.name
                infoText.text = item.info
                memoText.text = item.memo

                root.setOnClickListener {
                    onItemClickAction(item)
                }
            }
        }
    }
}