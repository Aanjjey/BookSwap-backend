package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UsersRepository : CrudRepository<User, Long> {

    @Query("select u from User u")
    fun findUsers(): List<User>

    @Query("select u from User u where u.username = :username")
    fun findByUsername(@Param("username") username: String): User?

    @Query("select u from User u where u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Query("select u from User u where u.phone = :phone")
    fun findByPhone(@Param("phone") phone: String): User?
}
