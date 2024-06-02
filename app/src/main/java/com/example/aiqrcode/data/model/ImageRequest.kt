package com.example.aiqrcode.data.model

data class ImageRequest(
    val prompt: String,
    val negative_prompt: String = "",
    val cfg_scale: Int = 7,
    val width: Int = 512,
    val height: Int = 512,
    val sampler_name: String = "DPM++ 2M",
    val scheduler: String = "Karras",
    val steps: Int = 20,
    val enable_hr: Boolean = true,
    val denoising_strength: Float = 0.7f,
    val hr_upscaler: String = "Latent",
    val hr_second_pass_steps: Int = 20,
    val alwayson_scripts: Scripts
)

data class Scripts(
    val controlnet: ControlNet
)

data class ControlNet(
    val args: List<ControlNetArgs>
)

data class ControlNetArgs(
    val input_image: String,
    val model: String,
    val weight: Float = 1.2f,
    val resize_mode: Int = 1,
    val guidance_start: Float = 0.3f,
    val guidance_end: Float = 0.95f,
    val control_mode: Int = 0,
    val pixel_perfect: Boolean = true
)