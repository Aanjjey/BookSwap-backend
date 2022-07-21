package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.LoanRequest
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface LoanRequestRepository :
    CrudRepository<LoanRequest, Long>, RequestRepository<LoanRequest> {

    @Query("select r from LoanRequest r left join r.loan l where l is null")
    override fun findPendingRequests(): List<LoanRequest>

    @Query(
        """select r from LoanRequest r left join r.loan l where l is null
            and r.fromUser.id = :from_user_id"""
    )
    override fun findRequestsFromUser(@Param("from_user_id") fromUser: Long): List<LoanRequest>

    @Query(
        """select r from LoanRequest r left join r.loan l where l is null 
            and r.toUser.id = :to_user_id"""
    )
    override fun findRequestsToUser(@Param("to_user_id") toUser: Long): List<LoanRequest>

    @Query(
        """select r from LoanRequest r left join r.loan l where l is null 
            and r.fromUser.id = :from_user_id
            and r.toUser.id = :to_user_id"""
    )
    override fun findRequestsFromUserToUser(
        @Param("from_user_id") fromUser: Long,
        @Param("to_user_id") toUser: Long
    ): Iterable<LoanRequest>
}
