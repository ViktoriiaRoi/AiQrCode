package com.example.aiqrcode.data

import com.example.aiqrcode.data.model.ControlNet
import com.example.aiqrcode.data.model.ControlNetArgs
import com.example.aiqrcode.data.model.ImageRequest
import com.example.aiqrcode.data.model.ImageResponse
import com.example.aiqrcode.data.model.Scripts
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface StableDiffusionRepository {
    suspend fun generateImage(prompt: String, image: String, weight: Float): Result<String>
}

class StableDiffusionRepositoryImpl(
    private val imageService: StableDiffusionService
) : StableDiffusionRepository {

    private val gson = Gson()

    override suspend fun generateImage(prompt: String, image: String, weight: Float): Result<String> {
        val imageRequest = createImageRequest(
            prompt = prompt,
            image = image,
            weight = weight
        )
        val mediaType = "application/json".toMediaType()
        val requestBody = gson.toJson(imageRequest).toRequestBody(mediaType)

        return try {
            val response = imageService.generateImage(requestBody)
            processResponse(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun processResponse(response: Response<ResponseBody>): Result<String> {
        if (!response.isSuccessful || response.body() == null) {
            return Result.failure(Exception("Unsuccessful network call: ${response.message()}"))
        }

        val jsonString = response.body()!!.string()
        val imageResponse = gson.fromJson(jsonString, ImageResponse::class.java)

        return imageResponse.getFirstImage()?.let {
            Result.success(it)
        } ?: Result.failure(Exception("Failed to extract image data"))
    }

    private fun createImageRequest(prompt: String, image: String, weight: Float) =
        ImageRequest(
            prompt = prompt,
            negative_prompt = "nsfw",
            alwayson_scripts = Scripts(
                controlnet = ControlNet(
                    args = listOf(
                        ControlNetArgs(
                            input_image = image,
                            model = "control_v1p_sd15_qrcode_monster_v2 [5e5778cb]",
                            weight = weight
                        )
                    )
                )
            )
        )
}