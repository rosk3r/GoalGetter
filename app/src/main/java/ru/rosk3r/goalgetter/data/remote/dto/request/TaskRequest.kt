package ru.rosk3r.goalgetter.data.remote.dto.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.rosk3r.goalgetter.domain.model.Task
import java.io.IOException


class TaskRequest(
    private val token: String
) {
    fun request(taskRequest: TaskRequest): List<Task>? {
        val okHttpClient = OkHttpClient()
        val tasks: List<Task>

        val request = Request.Builder()
            .get()
            .addHeader("Content-Type", "application/json")
            .addHeader("token", taskRequest.token)
            .url("https://pumped-tough-sunbeam.ngrok-free.app/tasks")
            .build()

        try {
            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                println("Response was not successful: ${response.code}")
                return null
            }

            val responseData = response.body?.string()
            println("Response body: $responseData")

            try {
                val itemType = object : TypeToken<List<Task>>() {}.type
                tasks = Gson().fromJson<List<Task>>(responseData, itemType)
                return tasks
            } catch (e: Exception) {
                println("Response was not successful: ${e.message}")
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
