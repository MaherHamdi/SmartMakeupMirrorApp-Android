package tn.farah.smartmakeupapp.data.repo.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val  BASE_URL = "http://192.168.1.103:9090"
    public val retrofit = Retrofit.Builder()

    .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()