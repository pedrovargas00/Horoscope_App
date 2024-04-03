package com.eldesistemas.horoscapp.domain

import com.eldesistemas.horoscapp.domain.model.PredictionModel

interface Repository {

    suspend fun getPrediction(sign: String): PredictionModel?
}