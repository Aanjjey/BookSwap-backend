package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.Book
import ac.ic.drp19.backend.model.Ownership
import ac.ic.drp19.backend.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface OwnershipRepository : CrudRepository<Ownership, Long> {

    @Query("select o.owner from Ownership o where o.book.id = :book_id and o.currentCopies > 0")
    fun findOwnersOfBook(@Param("book_id") bookId: Long): List<User>

    @Query(
        """
            select o.owner from Ownership o 
            where o.book.id = :book_id 
            and o.owner.id <> :except_id
            and o.currentCopies > 0
        """
    )
    fun findOwnersOfBookExcept(
        @Param("book_id") bookId: Long,
        @Param("except_id") exceptUser: Long
    ): List<User>

    @Query("select count(o) from Ownership o where o.book.id = :book_id and o.currentCopies > 0")
    fun countOwnersOfBook(@Param("book_id") bookId: Long): Int

    @Query(
        """
            select count(o) from Ownership o
            where o.book.id = :book_id
            and o.owner.id <> :except_id
            and o.currentCopies > 0
        """
    )
    fun countOwnersOfBookExcept(
        @Param("book_id") bookId: Long,
        @Param("except_id") exceptUser: Long
    ): Int

    @Query("select o.book from Ownership o where o.owner.id = :user_id")
    fun findUserBooks(@Param("user_id") userId: Long): List<Book>

    @Query(
        """
            select distinct(o.book) from Ownership o
            where o.owner.id <> :user_id
            and o.currentCopies > 0
        """
    )
    fun findBooksWithOwnersExcept(@Param("user_id") userId: Long): List<Book>

    @Query("select o from Ownership o where o.owner.id = :user_id and o.book.id = :book_id")
    fun findOwnership(@Param("user_id") userId: Long, @Param("book_id") bookId: Long): Ownership?
}
