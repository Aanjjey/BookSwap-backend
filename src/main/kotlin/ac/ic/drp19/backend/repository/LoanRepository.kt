package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.Loan
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface LoanRepository : CrudRepository<Loan, Long> {

    @Query("select l from Loan l left join l.request r where r.fromUser.id = :from_user_id")
    fun findLoansFromUser(@Param("from_user_id") fromUser: Long): List<Loan>

    @Query("select l from Loan l left join l.request r where r.toUser.id = :to_user_id")
    fun findLoansToUser(@Param("to_user_id") toUser: Long): List<Loan>

    @Query(
        """select l from Loan l left join l.request r
            where r.fromUser.id = :from_user_id
            and r.toUser.id = :to_user_id"""
    )
    fun findLoansFromUserToUser(
        @Param("from_user_id") fromUserId: Long,
        @Param("to_user_id") toUserId: Long
    ): Iterable<Loan>
}
