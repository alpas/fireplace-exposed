package dev.alpas.fireplace

import dev.alpas.Application
import dev.alpas.ServiceProvider
import dev.alpas.console.ConsoleKernel
import dev.alpas.encryption.EncryptionServiceProvider
import dev.alpas.hashing.HashServiceProvider
import dev.alpas.logging.LoggerServiceProvider
import dev.alpas.ozone.OzoneServiceProvider
import dev.alpas.routing.RouteServiceProvider
import kotlin.reflect.KClass

@Suppress("unused")
class ConsoleKernel : ConsoleKernel() {
    override fun serviceProviders(app: Application): Iterable<KClass<out ServiceProvider>> {
        return listOf(
            LoggerServiceProvider::class,
            EncryptionServiceProvider::class,
            HashServiceProvider::class,
            RouteServiceProvider::class,
            OzoneServiceProvider::class
        )
    }
}
