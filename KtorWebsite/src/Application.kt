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
        cookie<MySession>("SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        form("login") {
            userParamName = "username"
            passwordParamName = "password"
            challenge(redirectUrl = "/login")
            validate {
                credentials -> if (credentials.name != "" && credentials.password != "") UserIdPrincipal(credentials.name) else null
            }
        }
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
            authenticate("login") {
                post {
                    val principal = call.principal<UserIdPrincipal>()
                    call.sessions.set("SESSION", MySession(name = principal?.name))
                    call.respondRedirect("/", permanent = false)
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

    }
}

data class IndexData(val items: List<Int>)

data class MySession(val name: String? = "Anonymous")

