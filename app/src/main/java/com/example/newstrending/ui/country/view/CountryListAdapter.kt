package com.example.newstrending.ui.country.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.databinding.NewSourceItemLayoutBinding
import com.example.newstrending.util.ItemClickListener

class CountryListAdapter(
    private val countryList: ArrayList<CountryList>
) : RecyclerView.Adapter<CountryListAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<CountryList>

    class DataViewHolder(private val binding: NewSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(countryList: CountryList,itemClickListener: ItemClickListener<CountryList>) {
            binding.tvSource.text = countryList.name

            itemView.setOnClickListener {
                itemClickListener(countryList)
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

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(countryList[position],itemClickListener)

    fun addData(list: List<CountryList>) {
        countryList.addAll(list)
    }

}