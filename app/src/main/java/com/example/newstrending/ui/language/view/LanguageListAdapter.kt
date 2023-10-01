package com.example.newstrending.ui.language.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.databinding.NewSourceItemLayoutBinding
import com.example.newstrending.util.ItemClickListener

class LanguageListAdapter(
    private val languageList: ArrayList<LanguageList>
) : RecyclerView.Adapter<LanguageListAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<LanguageList>

    class DataViewHolder(private val binding: NewSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(languageList: LanguageList, itemClickListener: ItemClickListener<LanguageList>) {
            binding.tvSource.text = languageList.name

            itemView.setOnClickListener {
                itemClickListener(languageList)
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

    override fun getItemCount(): Int = languageList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(languageList[position], itemClickListener)

    fun addData(list: List<LanguageList>) {
        languageList.addAll(list)
    }

}