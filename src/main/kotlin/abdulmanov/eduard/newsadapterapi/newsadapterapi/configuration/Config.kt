package abdulmanov.eduard.newsadapterapi.newsadapterapi.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun getOkHttpClient() : OkHttpClient = OkHttpClient()
}
