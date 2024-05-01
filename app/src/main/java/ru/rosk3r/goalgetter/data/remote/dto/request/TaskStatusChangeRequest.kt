package ru.rosk3r.goalgetter.data.remote.dto.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.rosk3r.goalgetter.domain.model.Task
import java.io.IOException

class TaskStatusChangeRequest(
    private val token: String,
    private val id: Long,
) {
    fun request(taskStatusChangeRequest: TaskStatusChangeRequest): Task? {
        val okHttpClient = OkHttpClient()
        val task: Task

        val json = JSONObject()

        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .put(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("token", taskStatusChangeRequest.token)
            .url("https://pumped-tough-sunbeam.ngrok-free.app/tasks/status-change/$id")
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
                val itemType = object : TypeToken<Task>() {}.type
                task = Gson().fromJson<Task>(responseData, itemType)
                return task
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