package abdulmanov.eduard.newsadapterapi.newsadapterapi.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

private const val DURATION_TIMEOUT = 2L

@Configuration
class HttpClientConfiguration {

    @Bean
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(DURATION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DURATION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DURATION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
