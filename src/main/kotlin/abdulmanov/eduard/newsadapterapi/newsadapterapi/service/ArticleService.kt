package abdulmanov.eduard.newsadapterapi.newsadapterapi.service

import abdulmanov.eduard.newsadapterapi.newsadapterapi.model.Article
import abdulmanov.eduard.newsadapterapi.newsadapterapi.network.NetworkClient
import abdulmanov.eduard.newsadapterapi.newsadapterapi.parser.ArticleParser
import kotlinx.coroutines.flow.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val networkClient: NetworkClient,
    private val articleParser: ArticleParser
) {

    @Value("\${vesti-ru-url}")
    lateinit var url: String

    fun getNews(): Flow<Article> {
        return flow { emit(networkClient.makeRequest(url)) }
            .flatMapConcat(articleParser::invoke)
    }
}
