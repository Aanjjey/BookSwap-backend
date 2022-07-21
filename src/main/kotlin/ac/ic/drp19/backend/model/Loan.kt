package ac.ic.drp19.backend.model

import ac.ic.drp19.backend.util.ExposeId
import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.Collections.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
@ExposeId
class Loan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @NotNull
    var request: LoanRequest,

    @OneToMany(mappedBy = "loan")
    @JsonBackReference
    var returns: List<Return> = emptyList()
)
