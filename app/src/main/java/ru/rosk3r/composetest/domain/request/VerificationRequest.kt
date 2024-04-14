package ru.rosk3r.composetest.domain.request

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import ru.rosk3r.composetest.domain.response.SessionResponse
import java.io.IOException

data class VerificationRequest(
    private val token: String,
) {
    fun request(verificationRequest: VerificationRequest): SessionResponse? {
        val okHttpClient = OkHttpClient()
        var sessionResponse: SessionResponse? = null

        val json = JSONObject()
        json.put("token", verificationRequest.token)

        // Создаем тело запроса из JSON-объекта
        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .url("https://pumped-tough-sunbeam.ngrok-free.app/sign-in/verification")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    println("Response was not successful: ${response.code}")
                    return
                }

                val responseData = response.body?.string()
                println("Response body: $responseData")

                // Попытка разбора JSON-ответа и извлечения токена
                try {
                    val response = Gson().fromJson(responseData, SessionResponse::class.java)
                    sessionResponse = SessionResponse(response.id, response.userId, response.token, response.expiredDate)
                } catch (e: Exception) {
                    println("Ошибка при извлечении токена: ${e.message}")
                }
            }
        })

        return sessionResponse
    }
}