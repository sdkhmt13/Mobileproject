# Book Tracker

An Android app for tracking your reading. Keep your books on a shelf, mark what you want to read, are reading, or have finished, and search for new books.

Built with Kotlin and Jetpack Compose for the CSS 216 Mobile Application Development course at SDU.

## Group members

- Azat Seidakhmet— data layer and ViewModels
- Serikbol Askar — Shelf and Search screens, book list with status filters,search screen
- Altynay Ibrayeva — navigation, Book Detail screen, reusable components, theme

## Main features

- Shelf with all your books and filters: All / Want / Reading / Done
- Search books by title or author and add them to the shelf
- Book Detail screen opened with a book ID argument
- Change a book's reading status
- Delete a book from the shelf
- Back navigation with a top app bar

## Architecture

- **MVVM**: `data/` (Model), `viewmodel/` (ViewModel), `ui/` (View)
- **State**: `StateFlow` in ViewModels and the repository, collected in Compose with `collectAsStateWithLifecycle()`
- **Navigation**: Navigation Compose with `NavHost` and routes in a `Screen` sealed class
- **Reusable UI**: `BookCard`, `StatusChips`
