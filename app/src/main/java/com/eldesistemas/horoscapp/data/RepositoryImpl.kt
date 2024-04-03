package com.eldesistemas.horoscapp.data

import android.util.Log
import com.eldesistemas.horoscapp.data.network.HoroscopeApiService
import com.eldesistemas.horoscapp.domain.Repository
import com.eldesistemas.horoscapp.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService): Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {

        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("Error", it.message.toString()) }

        return null
    }
}