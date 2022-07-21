package ac.ic.drp19.backend.model

import ac.ic.drp19.backend.util.ExposeId
import com.sun.istack.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@ExposeId
class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @NotNull
    var isbn: String,

    @NotNull
    var title: String,

    var published: String
)
