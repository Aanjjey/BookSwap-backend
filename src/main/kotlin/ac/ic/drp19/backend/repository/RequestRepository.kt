package ac.ic.drp19.backend.repository

interface RequestRepository<T> {

    fun findRequestsFromUser(fromUser: Long): List<T>

    fun findRequestsToUser(toUser: Long): List<T>

    fun findRequestsFromUserToUser(fromUser: Long, toUser: Long): Iterable<T>

    fun findPendingRequests(): List<T>
}
