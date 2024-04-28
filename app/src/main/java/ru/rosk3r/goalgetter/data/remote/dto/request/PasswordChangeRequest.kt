package ru.rosk3r.goalgetter.data.remote.dto.request

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch

data class PasswordChangeRequest(
    private val token: String,
    private val password: String,
) {
    fun request(passwordChangeRequest: PasswordChangeRequest): Boolean {
        val okHttpClient = OkHttpClient()

        val json = JSONObject()
        json.put("password", password)

        // Создаем тело запроса из JSON-объекта
        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .put(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("token", passwordChangeRequest.token)
            .url("https://pumped-tough-sunbeam.ngrok-free.app/users/password-change")
            .build()

        val latch = CountDownLatch(1)

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                latch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    println("Response was not successful: ${response.code}")
                    latch.countDown()
                    return
                }
            }
        })
        latch.await()

        return true
    }
}