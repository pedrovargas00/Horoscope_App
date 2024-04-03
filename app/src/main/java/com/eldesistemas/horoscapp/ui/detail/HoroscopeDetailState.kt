package com.eldesistemas.horoscapp.ui.detail

import com.eldesistemas.horoscapp.domain.model.HoroscopeModel

sealed class HoroscopeDetailState {

    data object Loading: HoroscopeDetailState()
    data class Error(val error: String): HoroscopeDetailState()
    data class Success(val horoscope: String, val sign: String, val horoscopeModel: HoroscopeModel): HoroscopeDetailState()
}