package dev.alpas.fireplace

import dev.alpas.auth.authRoutes
import dev.alpas.fireplace.controllers.WelcomeController
import dev.alpas.routing.Router

fun Router.addRoutes() = apply {
    webRoutes()
    authRoutes(requireEmailVerification = false)
}

private fun Router.webRoutes() {
    get("/", WelcomeController::class).name("welcome").middlewareGroup("web")
}

