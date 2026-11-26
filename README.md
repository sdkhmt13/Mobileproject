# Book Tracker

An Android app for tracking your reading. Keep your books on a shelf, mark what you want to read, are reading, or have finished, and search for new books in Open Library.

Built with Kotlin and Jetpack Compose for the CSS 216 Mobile Application Development course at SDU.

## Group members

- Azat Seidakhmet — data layer, ViewModels, Room database
- Serikbol Askar — Shelf and Search screens, Retrofit and Open Library API
- Altynay Ibrayeva — navigation, Book Detail screen, components, theme, DataStore settings

## Main features

- Shelf with filters: All / Want / Reading / Done
- Search books in Open Library by title or author and add them to the shelf
- Loading, error (with Retry) and empty states for search
- Book covers loaded with Coil
- Book Detail screen opened with a book ID argument
- Change a book's reading status, delete a book
- Shelf stored in Room: books stay after the app is closed
- Light / dark / system theme saved with DataStore

## Architecture

- **MVVM**: `data/` (Model), `viewmodel/` (ViewModel), `ui/` (View)
- **State**: `StateFlow` in ViewModels, collected in Compose with `collectAsStateWithLifecycle()`
- **Navigation**: Navigation Compose with `NavHost` and routes in a `Screen` sealed class
- **Reusable UI**: `BookCard`, `StatusChips`, `BookCover`

## Week 13: data layer

| Layer | Classes |
|---|---|
| Room (local) | `BookEntity`, `BookDao` (CRUD), `AppDatabase`, `RoomBookRepository` |
| Retrofit (remote) | `OpenLibraryApi`, `RetrofitClient`, `OpenLibraryBookSearchRepository` |
| DataStore | `SettingsRepository` (theme mode) |
| Wiring | `AppContainer` creates every repository once, `BookTrackerApplication` holds it |

ViewModels depend on the `BookRepository` and `BookSearchRepository` interfaces, so switching from in-memory data to Room and the API did not change the ViewModels or screens.

## Tests

- Unit tests: `InMemoryBookRepositoryTest`, `SearchViewModelTest`
- Instrumented tests (emulator): `BookDaoTest` for Room CRUD