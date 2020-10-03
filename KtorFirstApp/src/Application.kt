import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::mainModule).start(wait = true)
}

fun Application.mainModule() {
    routing {
        root()
    }
}

fun Routing.root() {
    get("/") {
        call.respondText("<h1>Hello World!</h1>", contentType = ContentType.Text.Html, status = HttpStatusCode.OK)
    }
}
