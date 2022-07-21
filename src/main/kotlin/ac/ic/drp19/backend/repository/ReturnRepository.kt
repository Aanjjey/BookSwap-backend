package ac.ic.drp19.backend.repository

import ac.ic.drp19.backend.model.Return
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ReturnRepository : CrudRepository<Return, Long> {

    @Query("select r from Return r where r.loan.id = :loan_id")
    fun findReturnsOfLoan(@Param("loan_id") loanId: Long): List<Return>

    @Query("select sum(r.copies) from Return r where r.loan.id = :loan_id")
    fun numCopiesReturned(@Param("loan_id") loanId: Long): Optional<Int>

    @Transactional
    @Modifying
    @Query("delete from Return r where r.loan.id = :loan_id")
    fun deleteReturnsOfLoan(@Param("loan_id") loanId: Long)
}
