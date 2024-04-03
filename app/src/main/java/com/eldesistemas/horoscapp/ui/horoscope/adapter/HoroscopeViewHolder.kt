package com.eldesistemas.horoscapp.ui.horoscope.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.eldesistemas.horoscapp.databinding.ItemHoroscopeBinding
import com.eldesistemas.horoscapp.domain.model.HoroscopeInfo

class HoroscopeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemHoroscopeBinding.bind(view)

    fun render(horoscopeInfo: HoroscopeInfo, onItemSelected: (HoroscopeInfo) -> Unit) {

        binding.ivHoroscope.setImageResource(horoscopeInfo.img)
        //binding.tvHoroscope.setText(horoscopeInfo.name)
        binding.tvHoroscope.text = binding.tvHoroscope.context.getString(horoscopeInfo.name)

        binding.itemHoroscope.setOnClickListener{
            rotationAnimation(binding.ivHoroscope, callOnItemSelected = {
                onItemSelected(horoscopeInfo)
            })
        }

    }

    private fun rotationAnimation(view: View, callOnItemSelected: () -> Unit) {

       view.animate().apply {
           duration = 500
           interpolator = LinearInterpolator()
           rotationBy(360f)
           withEndAction{ callOnItemSelected() }
           start()
       }

    }
}