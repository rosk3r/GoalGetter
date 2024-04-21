package ru.rosk3r.goalgetter.data.remote.dto.request

import okhttp3.OkHttpClient
import okhttp3.Request

data class TaskDeleteRequest(
    val token: String,
    val id: Long,
) {
    fun request(taskDeleteRequest: TaskDeleteRequest): Boolean {
        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .delete()
            .addHeader("Content-Type", "application/json")
            .addHeader("token", taskDeleteRequest.token)
            .url("https://pumped-tough-sunbeam.ngrok-free.app/tasks/$id")
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            println("Response was not successful: ${response.code}")
            return false
        }

        val responseData = response.body?.string()
        println("Response body: $responseData")
        return true

    }
}
