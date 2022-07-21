package ac.ic.drp19.backend.resource

import ac.ic.drp19.backend.model.Book
import ac.ic.drp19.backend.model.Ownership
import ac.ic.drp19.backend.model.User
import ac.ic.drp19.backend.service.OwnershipService
import ac.ic.drp19.backend.service.UsersService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersResource(
    val userService: UsersService,
    val ownerService: OwnershipService
) {

    @GetMapping("/users")
    fun users(): List<User> = userService.findUsers()

    @GetMapping("/users/{user_id}")
    fun user(
        @PathVariable("user_id") userId: Long
    ): User? =
        userService.findUserById(userId)

    @GetMapping("/users/{user_id}/owns")
    fun userOwns(
        @PathVariable("user_id") userId: Long
    ): List<Ownership>? =
        userService.findUserOwns(userId)

    @GetMapping("/users/{user_id}/books")
    fun userBooks(
        @PathVariable("user_id") userId: Long
    ): List<Book>? =
        ownerService.findUserBooks(userId)

    @PostMapping("/users")
    fun postUser(@RequestBody user: User) {
        userService.postUser(user)
    }

    @PostMapping("/users/{user_id}/owns")
    fun postOwnership(
        @PathVariable("user_id") userId: Long,
        @RequestBody owns: OwnershipPost
    ) {
        ownerService.postOwnership(
            userId,
            owns.bookId,
            owns.totalCopies,
            owns.currentCopies
        )
    }
}

class OwnershipPost(
    val bookId: Long,
    val totalCopies: Int,
    val currentCopies: Int
)
