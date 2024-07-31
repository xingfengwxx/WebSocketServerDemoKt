package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import org.java_websocket.server.WebSocketServer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.UnknownHostException
import java.time.Duration


fun main() {
    println("startWebSocketServer---------------")
    startWebSocketServer()

//    println("startJavaWebSocketServer---------------")
//    startJavaWebSocketServer()
}

fun startWebSocketServer() {
    embeddedServer(Netty, port = 12024) {
        install(WebSockets) {
            pingPeriod = Duration.ofMinutes(1)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        routing {
            webSocket() {
                send("You are connected!")
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        println("Received: $receivedText")
                        send("You said: $receivedText")
                    }
                }
            }
        }
    }.start(wait = true)
}

fun startJavaWebSocketServer() {
    // write your code here
    val inetSocketAddress = InetSocketAddress(12024)
    val socketServer: WebSocketServer = WebSocketService(inetSocketAddress)
    socketServer.start()
    try {
        val ip = InetAddress.getLocalHost().hostAddress
        val port = socketServer.port
        println(String.format("服务已启动：%s:%d", ip, port))
    } catch (e: UnknownHostException) {
        e.printStackTrace()
    }

    val `in` = InputStreamReader(System.`in`)
    val reader = BufferedReader(`in`)
    while (true) {
        try {
            val msg = reader.readLine()
            (socketServer as WebSocketService).sendToAll(msg)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}