package ac.ic.drp19.backend.resource

import ac.ic.drp19.backend.model.Book
import ac.ic.drp19.backend.model.User
import ac.ic.drp19.backend.service.BookService
import ac.ic.drp19.backend.service.OwnershipService
import ac.ic.drp19.backend.util.removeQuotes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class BooksResource(
    val bookService: BookService,
    val ownershipService: OwnershipService
) {

    @GetMapping("/books")
    fun books(
        @RequestParam(name = "exceptUser") userId: Long?
    ): Iterable<Book> =
        if (userId != null) {
            ownershipService.findBooksWithOwnersExcept(userId)
        } else {
            bookService.findBooks()
        }

    @GetMapping("/books/{id}")
    fun bookId(
        @PathVariable(name = "id") bookId: Long
    ): Book? =
        bookService.findBookById(bookId)

    @GetMapping("/books/{id}/owners")
    fun bookOwners(
        @PathVariable(name = "id") bookId: Long,
        @RequestParam(name = "exceptUser") userId: Long?
    ): List<User> =
        ownershipService.findOwnersOfBook(bookId, userId)

    @GetMapping("/books/{id}/owners/count")
    fun bookOwnersCount(
        @PathVariable(name = "id") bookId: Long,
        @RequestParam(name = "exceptUser") userId: Long?
    ): Int =
        ownershipService.countOwnersOfBook(bookId, userId)

    @PostMapping("/books")
    fun postBook(@RequestBody isbn: String): ResponseEntity<Any?> {
        return try {
            val book = bookService.postBookByIsbn(removeQuotes(isbn))
            ResponseEntity(book, HttpStatus.OK)
        } catch (e: ResponseStatusException) {
            ResponseEntity(e.reason, e.status)
        }
    }
}
