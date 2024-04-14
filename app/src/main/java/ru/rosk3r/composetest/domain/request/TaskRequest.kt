package ru.rosk3r.composetest.domain.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.rosk3r.composetest.domain.response.TaskResponse
import java.io.IOException


class TaskRequest(
    private val token: String
) {
    fun request(taskRequest: TaskRequest): List<TaskResponse>? {
        val okHttpClient = OkHttpClient()
        val tasks: List<TaskResponse>

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

            // Попытка разбора JSON-ответа и извлечения списка задач
            try {
                val itemType = object : TypeToken<List<TaskResponse>>() {}.type
                tasks = Gson().fromJson<List<TaskResponse>>(responseData, itemType)
                return tasks
            } catch (e: Exception) {
                println("Ошибка при извлечении списка задач: ${e.message}")
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
