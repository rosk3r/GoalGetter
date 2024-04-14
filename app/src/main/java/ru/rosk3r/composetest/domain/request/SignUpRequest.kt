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
import ru.rosk3r.composetest.domain.model.Session
import ru.rosk3r.composetest.domain.response.SessionResponse
import java.io.IOException
import java.util.concurrent.CountDownLatch

class SignUpRequest(
    private val login: String,
    private val email: String,
    private val password: String
) {
    fun request(): Session? {
        val okHttpClient = OkHttpClient()

        val json = JSONObject()
        json.put("username", login)
        json.put("email", email)
        json.put("password", password)
        json.put("code", "123")

        // Создаем тело запроса из JSON-объекта
        val requestBody: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .url("https://pumped-tough-sunbeam.ngrok-free.app/sign-up")
            .build()

        val latch = CountDownLatch(1)
        var session: Session? = null

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

                val responseData = response.body?.string()
                println("Response body: $responseData")

                // Попытка разбора JSON-ответа и извлечения токена
                try {
                    val sessionResponse = Gson().fromJson(responseData, SessionResponse::class.java)
                    session = Session(sessionResponse.token)
                } catch (e: Exception) {
                    println("Ошибка при извлечении токена: ${e.message}")
                } finally {
                    latch.countDown()
                }
            }
        })
        latch.await()

        return session
    }
}