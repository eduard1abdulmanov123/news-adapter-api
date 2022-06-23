package abdulmanov.eduard.newsadapterapi.newsadapterapi.parser

import abdulmanov.eduard.newsadapterapi.newsadapterapi.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

private const val ITEM_TAG = "item"
private const val LINK_TAG = "link"
private const val TITLE_TAG = "title"
private const val DESCRIPTION_TAG = "description"
private const val DATA_TAG = "pubDate"
private const val CATEGORY_TAG = "category"
private const val IMAGE_TAG = "enclosure"
private const val FULL_DESCRIPTION_TAG = "yandex:full-text"

private const val IMAGE_ATTRIBUTE_NAME = "url"

@Component
class ArticleParser {

    fun invoke(xml: InputStream): Flow<Article> {
        return flow {
            createDocument(xml)
                .getElementsByTagName(ITEM_TAG)
                .forEach { emit(getNew(it)) }
        }.flowOn(Dispatchers.Default)
    }

    private fun createDocument(xml: InputStream): Document {
        return DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(xml)
            .apply(Document::normalizeDocument)
    }

    private fun getNew(itemElement: Element): Article {
        return Article(
            id = parseId(itemElement.getNodeValue(LINK_TAG)),
            title = itemElement.getNodeValue(TITLE_TAG),
            link = itemElement.getNodeValue(LINK_TAG),
            description = itemElement.getNodeValue(DESCRIPTION_TAG),
            date = itemElement.getNodeValue(DATA_TAG),
            category = itemElement.getNodeValue(CATEGORY_TAG),
            image = itemElement.getAttribute(IMAGE_TAG, IMAGE_ATTRIBUTE_NAME),
            fullDescription = parseFullDescription(itemElement.getNodeValue(FULL_DESCRIPTION_TAG))
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
