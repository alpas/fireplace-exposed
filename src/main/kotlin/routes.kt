package dev.alpas.fireplace

import dev.alpas.fireplace.controllers.WelcomeController
import dev.alpas.routing.Router

fun Router.addRoutes() = apply {
    webRoutes()
}

private fun Router.webRoutes() {
    get("/", WelcomeController::class).name("welcome").middlewareGroup("web")
}

