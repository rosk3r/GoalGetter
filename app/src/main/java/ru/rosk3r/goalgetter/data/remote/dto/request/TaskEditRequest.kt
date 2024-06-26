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

class TaskEditRequest(
    private val token: String,
    private val id: Long,
    private val title: String
) {
    fun request(taskEditRequest: TaskEditRequest): Task? {
        val okHttpClient = OkHttpClient()
        val task: Task

        val json = JSONObject()
        json.put("title", title)

        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .put(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("token", taskEditRequest.token)
            .url("https://pumped-tough-sunbeam.ngrok-free.app/tasks/edit/$id")
            .build()

        try {
            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                println("Response was not successful: ${response.code}")
                return null
            }

            val responseData = response.body?.string()
            println("Response body: $responseData")

            return try {
                val itemType = object : TypeToken<Task>() {}.type
                task = Gson().fromJson<Task>(responseData, itemType)
                task
            } catch (e: Exception) {
                println("Response was not successful: ${e.message}")
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}