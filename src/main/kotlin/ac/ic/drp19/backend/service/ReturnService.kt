package ac.ic.drp19.backend.service

import ac.ic.drp19.backend.model.Return
import ac.ic.drp19.backend.repository.LoanRepository
import ac.ic.drp19.backend.repository.OwnershipRepository
import ac.ic.drp19.backend.repository.ReturnRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReturnService(
    val returnRepository: ReturnRepository,
    val loanRepository: LoanRepository,
    val ownershipRepository: OwnershipRepository
) {

    fun findReturnsOfLoan(loanId: Long) = returnRepository.findReturnsOfLoan(loanId)

    fun postReturn(loanId: Long, copies: Int) {
        val loan = loanRepository.findByIdOrNull(loanId) ?: return
        val request = loan.request
        val ownership = ownershipRepository.findOwnership(
            request.fromUser.id,
            request.book.id
        )
        assert(ownership != null)
        ownership!!
        ownership.currentCopies += copies
        ownershipRepository.save(ownership)

        val totalCopiesReturned = returnRepository.numCopiesReturned(loanId).orElse(0) + copies
        if (totalCopiesReturned >= request.copies) {
            returnRepository.deleteReturnsOfLoan(loanId)
            loanRepository.deleteById(loanId)
        } else {
            val ret = Return(
                loan = loan,
                copies = copies
            )
            returnRepository.save(ret)
        }
    }
}
