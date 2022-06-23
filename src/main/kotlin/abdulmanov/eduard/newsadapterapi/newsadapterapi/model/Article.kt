package abdulmanov.eduard.newsadapterapi.newsadapterapi.model

data class Article(
    val id: String,
    val title: String,
    val link: String,
    val description: String,
    val date: String,
    val category: String,
    val image: String,
    val fullDescription: String
)