package abdulmanov.eduard.newsadapterapi.newsadapterapi.parser

import abdulmanov.eduard.newsadapterapi.newsadapterapi.model.Article
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

@Component
class ArticleParser {

    fun invoke(xml: InputStream): List<Article> {
        return createDocument(xml)
            .getElementsByTagName("item")
            .toList(::getNew)
    }

    private fun createDocument(xml: InputStream): Document {
        return DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(xml)
            .apply(Document::normalizeDocument)
    }

    private fun getNew(itemElement: Element): Article {
        return Article(
            id = parseId(itemElement.getNodeValue("link")),
            title = itemElement.getNodeValue("title"),
            link = itemElement.getNodeValue("link"),
            description = itemElement.getNodeValue("description"),
            date = itemElement.getNodeValue("pubDate"),
            category = itemElement.getNodeValue("category"),
            image = itemElement.getAttribute("enclosure", "url"),
            fullDescription = parseFullDescription(itemElement.getNodeValue("yandex:full-text"))
        )
    }

    private fun parseId(link: String): String {
        return link
            .split("/")
            .last()
    }

    private fun parseFullDescription(fullDescription: String): String {
        return StringBuilder().apply {
            fullDescription
                .split("\r\n")
                .filter { it.isNotEmpty() && it.isNotBlank() }
                .forEach { append("$it\n\n") }
        }.toString()
    }
}
