package ru.rosk3r.composetest.data.remote.dto.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.rosk3r.composetest.data.remote.dto.response.TaskResponse
import java.io.IOException

class TaskCreateRequest(
    private val token: String,
    private val title: String,
) {
    fun request(taskCreateRequest: TaskCreateRequest): List<TaskResponse>? {
        val okHttpClient = OkHttpClient()
        val tasks: List<TaskResponse>

        val json = JSONObject()
        json.put("title", title)

        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("token", taskCreateRequest.token)
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
