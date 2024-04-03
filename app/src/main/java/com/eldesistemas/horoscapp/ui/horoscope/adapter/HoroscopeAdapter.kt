package com.eldesistemas.horoscapp.ui.horoscope.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eldesistemas.horoscapp.R
import com.eldesistemas.horoscapp.domain.model.HoroscopeInfo

class HoroscopeAdapter(private var horoscopeList: List<HoroscopeInfo> = emptyList(),
    private val onItemSelected: (HoroscopeInfo) -> Unit):
    RecyclerView.Adapter<HoroscopeViewHolder>() {

    override fun getItemCount() = horoscopeList.size

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        holder.render(horoscopeList[position], onItemSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        return HoroscopeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_horoscope, parent, false)
        )
    }

    fun updateList(list: List<HoroscopeInfo>) {

        horoscopeList = list
        notifyDataSetChanged()

    }
}