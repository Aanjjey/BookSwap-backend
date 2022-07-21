package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.Book
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface BookRepository : CrudRepository<Book, Long> {
    @Query("select b from Book b where b.isbn = :isbn")
    fun findByIsbn(@Param("isbn") isbn: String): Book?
}
