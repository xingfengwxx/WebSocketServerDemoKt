package org.example

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class WebSocketService(address: InetSocketAddress?) : WebSocketServer(address) {
    override fun onOpen(webSocket: WebSocket, clientHandshake: ClientHandshake) {
        val address = webSocket.remoteSocketAddress.address.hostAddress
        val message = String.format("(%s) <进入房间！>", address)
        sendToAll(message)
        println(message)
    }

    override fun onClose(webSocket: WebSocket, i: Int, s: String, b: Boolean) {
        val address = webSocket.remoteSocketAddress.address.hostAddress
        val message = String.format("(%s) <退出房间！>", address)
        sendToAll(message)
        println(message)
    }

    override fun onMessage(webSocket: WebSocket, s: String) {
        //服务端接收到消息
        val address = webSocket.remoteSocketAddress.address.hostAddress
        val message = String.format("(%s) %s", address, s)
        //将消息发送给所有客户端
        sendToAll(message)
        println(message)
    }

    override fun onError(webSocket: WebSocket, e: Exception) {
        if (null != webSocket) {
            webSocket.close(0)
        }
        e.printStackTrace()
    }

    override fun onStart() {
        println("onStart===========================================")
    }

    fun sendToAll(message: String?) {
        // 获取所有连接的客户端
        val connections: Collection<WebSocket> = connections
        //将消息发送给每一个客户端
        for (client in connections) {
            client.send(message)
        }
    }
}