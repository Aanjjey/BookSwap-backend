package ac.ic.drp19.backend

import ac.ic.drp19.backend.util.ExposeId
import org.reflections.Reflections
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.CorsRegistry

@SpringBootApplication
class BookappBackendApplication : RepositoryRestConfigurer {

    override fun configureRepositoryRestConfiguration(
        config: RepositoryRestConfiguration,
        cors: CorsRegistry
    ) {
        val ref = Reflections("ac.ic.drp19.backend.model")
        ref.getTypesAnnotatedWith(ExposeId::class.java)
            .forEach { config.exposeIdsFor(it) }
    }
}

@RestController
class IndexController {
    @GetMapping("/")
    fun index(): String {
        return "Book app in development..."
    }
}

fun main(args: Array<String>) {
    runApplication<BookappBackendApplication>(*args)
}
