package abdulmanov.eduard.newsadapterapi.newsadapterapi.controller

import abdulmanov.eduard.newsadapterapi.newsadapterapi.model.Article
import abdulmanov.eduard.newsadapterapi.newsadapterapi.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news")
class NewsController(private val articleService: ArticleService) {

    @GetMapping
    suspend fun getNews(): List<Article> {
        return articleService.getNews()
    }
}