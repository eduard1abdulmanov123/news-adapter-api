package abdulmanov.eduard.newsadapterapi.newsadapterapi.network

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpServerErrorException
import java.io.IOException
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Component
class NetworkClient(private val client: OkHttpClient) {

    suspend fun makeRequest(url: String): InputStream {
        return Request.Builder()
            .url(url)
            .build()
            .let(client::newCall)
            .await()
    }

    private suspend fun Call.await(): InputStream = suspendCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.byteStream()
                    ?.takeIf { response.isSuccessful }
                    ?.let(continuation::resume)
                    ?: continuation.resumeWithException(createHttpServerErrorException(response.message))
            }

            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(createHttpServerErrorException(e.message))
            }
        })
    }

    private fun createHttpServerErrorException(message: String?): HttpServerErrorException {
        return HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, message ?: "")
    }
}
