package com.example.mobileproject.data

class FakeBookSearchRepository : BookSearchRepository {

    private val catalog = listOf(
        Book("1", "Dune", "Frank Herbert", "Sci-fi classic about Arrakis."),
        Book("2", "1984", "George Orwell", "Dystopian novel about surveillance."),
        Book("3", "The Hobbit", "J.R.R. Tolkien", "Bilbo's unexpected journey."),
        Book("4", "Atomic Habits", "James Clear", "Small habits, big results."),
        Book("5", "Clean Code", "Robert C. Martin", "A handbook of software craftsmanship.")
    )

    override suspend fun search(query: String): List<Book> =
        if (query.isBlank()) emptyList()
        else catalog.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.author.contains(query, ignoreCase = true)
        }
}