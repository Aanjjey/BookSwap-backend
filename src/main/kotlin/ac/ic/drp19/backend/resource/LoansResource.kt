package ac.ic.drp19.backend.resource

import ac.ic.drp19.backend.model.Loan
import ac.ic.drp19.backend.model.LoanRequest
import ac.ic.drp19.backend.service.LoanService
import ac.ic.drp19.backend.service.RequestService
import ac.ic.drp19.backend.service.ReturnService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoansResource(
    val requestService: RequestService,
    val loanService: LoanService,
    val returnService: ReturnService
) {

    //region Requests

    @GetMapping("/loans/requests")
    fun getLoanRequests(
        @RequestParam("from", required = false) fromUserId: Long? = null,
        @RequestParam("to", required = false) toUserId: Long? = null
    ): Iterable<LoanRequest> =
        requestService.findLoanRequests(fromUserId, toUserId)

    @GetMapping("/loans/requests/{request_id}")
    fun getLoanRequest(@PathVariable("request_id") requestId: Long) =
        requestService.findLoanRequest(requestId)

    @PostMapping("/loans/requests")
    fun postRequest(@RequestBody request: LoanRequestPost) {
        requestService.postLoanRequest(
            request.fromUserId,
            request.toUserId,
            request.bookId,
            request.copies
        )
    }

    @PostMapping("/loans/requests/{request_id}")
    fun postDecision(
        @PathVariable("request_id") requestId: Long,
        @RequestBody decision: RequestDecision
    ) {
        if (decision.accept) {
            requestService.acceptLoanRequest(requestId)
        } else {
            requestService.denyLoanRequest(requestId)
        }
    }

    //endregion

    //region Loans

    @GetMapping("/loans")
    fun getLoans(
        @RequestParam("from", required = false) fromUserId: Long? = null,
        @RequestParam("to", required = false) toUserId: Long? = null
    ): Iterable<Loan> =
        loanService.findLoans(fromUserId, toUserId)

    @GetMapping("/loans/{loan_id}")
    fun getLoan(@PathVariable("loan_id") loanId: Long) = loanService.findById(loanId)

    //endregion

    //region Returns

    @GetMapping("/loans/{loan_id}/returns")
    fun getReturnsOfLoan(@PathVariable("loan_id") loanId: Long) =
        returnService.findReturnsOfLoan(loanId)

    @PostMapping("/loans/{loan_id}/returns")
    fun postReturn(
        @PathVariable("loan_id") loanId: Long,
        @RequestBody ret: ReturnPost
    ) {
        returnService.postReturn(
            loanId,
            ret.copies
        )
    }

    //endregion
}

data class RequestDecision(
    val accept: Boolean
)

data class LoanRequestPost(
    val fromUserId: Long,
    val toUserId: Long,
    val bookId: Long,
    val copies: Int
)

data class ReturnPost(
    val copies: Int
)
