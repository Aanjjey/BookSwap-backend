package ac.ic.drp19.backend.service

import ac.ic.drp19.backend.model.Loan
import ac.ic.drp19.backend.model.LoanRequest
import ac.ic.drp19.backend.repository.LoanRepository
import ac.ic.drp19.backend.repository.LoanRequestRepository
import ac.ic.drp19.backend.repository.OwnershipRepository
import ac.ic.drp19.backend.repository.RequestRepository
import ac.ic.drp19.backend.repository.UsersRepository
import ac.ic.drp19.backend.util.Combinator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RequestService(
    val loanRequestRepository: LoanRequestRepository,
    val loanRepository: LoanRepository,
    val ownershipRepository: OwnershipRepository,
    val usersRepository: UsersRepository
) {

    fun findLoanRequest(requestId: Long): LoanRequest? =
        loanRequestRepository.findByIdOrNull(requestId)

    fun findLoanRequests(fromUserId: Long?, toUserId: Long?): Iterable<LoanRequest> =
        findRequests(loanRequestRepository, fromUserId, toUserId)

    fun postLoanRequest(
        fromUserId: Long,
        toUserId: Long,
        bookId: Long,
        copies: Int
    ) {
        val ownership = ownershipRepository.findOwnership(fromUserId, bookId)
        if (ownership != null) {
            val toUser = usersRepository.findById(toUserId)
            toUser.ifPresent { to ->
                val request = LoanRequest(
                    fromUser = ownership.owner,
                    toUser = to,
                    book = ownership.book,
                    copies = copies
                )
                loanRequestRepository.save(request)

                ownership.currentCopies -= copies
                ownershipRepository.save(ownership)
            }
        }
    }

    fun acceptLoanRequest(requestId: Long) {
        val request = loanRequestRepository.findByIdOrNull(requestId) ?: return
        val loan = Loan(request = request)
        loanRepository.save(loan)
    }

    fun denyLoanRequest(requestId: Long) {
        loanRequestRepository.deleteById(requestId)
    }

    private fun <T> findRequests(
        requestRepository: RequestRepository<T>,
        fromUserId: Long?,
        toUserId: Long?
    ): Iterable<T> {
        return Combinator
            .of(fromUserId, toUserId, requestRepository::findPendingRequests)
            .first(requestRepository::findRequestsFromUser)
            .second(requestRepository::findRequestsToUser)
            .get()
    }
}
