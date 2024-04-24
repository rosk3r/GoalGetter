package ru.rosk3r.goalgetter.data.remote.dto.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.rosk3r.goalgetter.data.remote.dto.response.UserStatsResponse
import java.io.IOException

class UsersStatsRequest(
) {
    fun request(usersStatsRequest: UsersStatsRequest): List<UserStatsResponse>? {
        val okHttpClient = OkHttpClient()
        val users: List<UserStatsResponse>

        val request = Request.Builder()
            .get()
            .addHeader("Content-Type", "application/json")
            .url("https://pumped-tough-sunbeam.ngrok-free.app/users/stats")
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
                val itemType = object : TypeToken<List<UserStatsResponse>>() {}.type
                users = Gson().fromJson<List<UserStatsResponse>>(responseData, itemType)
                return users
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