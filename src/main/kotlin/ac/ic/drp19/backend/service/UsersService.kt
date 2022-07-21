package ac.ic.drp19.backend.service

import ac.ic.drp19.backend.model.Ownership
import ac.ic.drp19.backend.model.User
import ac.ic.drp19.backend.repository.UsersRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UsersService(val db: UsersRepository) {

    fun findUsers(username: String? = null): List<User> = db.findUsers()

    fun findUserById(userId: Long): User? = db.findByIdOrNull(userId)

    fun findUserOwns(userId: Long): List<Ownership>? = findUserById(userId)?.owns

    fun postUser(user: User) {
        db.save(user)
    }
}
