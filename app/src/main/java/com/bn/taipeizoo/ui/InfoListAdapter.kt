package com.bn.taipeizoo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bn.taipeizoo.databinding.ItemInfoBinding

class InfoListAdapter(
    private val items: List<Info>
) : RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class InfoViewHolder(
        private val binding: ItemInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Info) {
            with(binding) {
                titleText.text = item.title
                infoText.text = item.info
            }
        }
    }

    data class Info(val title: String, val info: String)
}