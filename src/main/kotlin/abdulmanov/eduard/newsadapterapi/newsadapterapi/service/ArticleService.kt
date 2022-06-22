package abdulmanov.eduard.newsadapterapi.newsadapterapi.service

import abdulmanov.eduard.newsadapterapi.newsadapterapi.model.Article
import abdulmanov.eduard.newsadapterapi.newsadapterapi.network.NetworkClient
import abdulmanov.eduard.newsadapterapi.newsadapterapi.parser.ArticleParser
import org.springframework.stereotype.Service

private const val URL = "https://www.vesti.ru/vesti.rss"

@Service
class ArticleService(
    private val networkClient: NetworkClient,
    private val articleParser: ArticleParser
) {

    suspend fun getNews(): List<Article> {
        return networkClient.makeRequest(URL)
            .let(articleParser::invoke)
    }
}
