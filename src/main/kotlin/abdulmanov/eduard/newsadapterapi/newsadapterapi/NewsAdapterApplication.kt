package abdulmanov.eduard.newsadapterapi.newsadapterapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:application.properties")
class NewsAdapterApplication

fun main(args: Array<String>) {
    runApplication<NewsAdapterApplication>(*args)
}
