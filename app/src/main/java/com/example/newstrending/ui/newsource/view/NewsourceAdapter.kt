package com.example.newstrending.ui.newsource.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.databinding.NewSourceItemLayoutBinding
import com.example.newstrending.util.ItemClickListener

class NewsourceAdapter(
    private val sourceList: ArrayList<NewSource>
) : RecyclerView.Adapter<NewsourceAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<NewSource>

    class DataViewHolder(private val binding: NewSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: NewSource, itemClickListener: ItemClickListener<NewSource>) {
            binding.tvSource.text = source.name
            itemView.setOnClickListener {
                itemClickListener(source)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            NewSourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sourceList[position],itemClickListener)

    fun addData(list: List<NewSource>) {
        sourceList.addAll(list)
    }

}