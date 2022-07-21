package ac.ic.drp19.backend.model

import ac.ic.drp19.backend.util.ExposeId
import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Date
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@ExposeId
class Return(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val loan: Loan,

    @NotNull
    @Min(1)
    val copies: Int,

    @JsonFormat(pattern = "yyyy-MM-dd")
    var date: Date = Date.valueOf(LocalDate.now())
)
