package ac.ic.drp19.backend.service

import ac.ic.drp19.backend.model.Loan
import ac.ic.drp19.backend.repository.LoanRepository
import ac.ic.drp19.backend.util.Combinator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LoanService(
    val loanRepository: LoanRepository
) {

    fun findById(loanId: Long) = loanRepository.findByIdOrNull(loanId)

    fun findLoans(fromUserId: Long?, toUserId: Long?): Iterable<Loan> {
        return Combinator
            .of(fromUserId, toUserId, loanRepository::findAll)
            .first(loanRepository::findLoansFromUser)
            .second(loanRepository::findLoansToUser)
            .both(loanRepository::findLoansFromUserToUser)
            .get()
    }
}
