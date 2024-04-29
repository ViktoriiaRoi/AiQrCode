package com.example.aiqrcode.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StableDiffusionService {
    @POST("/sdapi/v1/txt2img")
    suspend fun generateImage(@Body requestBody: RequestBody): Response<ResponseBody>
}