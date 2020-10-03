package com.firstwebsite

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
    }

    install(PartialContent) {
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        route("/login") {
            get {
                call.respond(FreeMarkerContent(template="login.ftl", null))
            }
            post {
                val post = call.receiveParameters()
                val username: String? = post["username"]
                val password: String? = post["password"]
                if (username == "") {
                    call.respond(FreeMarkerContent("login.ftl", mapOf("error" to "Username is required")))
                } else if (password == "") {
                    call.respond(FreeMarkerContent("login.ftl", mapOf("error" to "Password is required")))
                } else {
                    call.respondRedirect("/")
                }

            }
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }

        get("/html-freemarker-list") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }
    }
}

data class IndexData(val items: List<Int>)

data class MySession(val count: Int = 0)

