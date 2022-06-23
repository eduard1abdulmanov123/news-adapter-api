package abdulmanov.eduard.newsadapterapi.newsadapterapi.parser

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

inline fun NodeList.forEach(action: (Element) -> Unit) {
    for (i in 0 until length) {
        val node = item(i)
        if (node.nodeType == Node.ELEMENT_NODE) {
            action(node as Element)
        }
    }
}

fun Element.getNodeValue(tag: String): String {
    return getElementsByTagName(tag)
        .item(0)
        ?.takeIf(Node::hasChildNodes)
        ?.firstChild
        ?.takeIf { it.nodeType == Node.TEXT_NODE }
        ?.nodeValue
        ?: ""
}

fun Element.getAttribute(tag: String, attributeName: String): String {
    return getElementsByTagName(tag)
        .item(0)
        ?.takeIf(Node::hasAttributes)
        ?.let { it as Element }
        ?.getAttribute(attributeName)
        ?: ""
}