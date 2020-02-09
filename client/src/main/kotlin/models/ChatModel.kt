package models


import Shared
import controllers.MainController
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatModel(private val mainController: MainController) {

    private lateinit var chatSocket: DefaultClientWebSocketSession


    fun connectToChat() {
        val client = HttpClient {
            install(WebSockets)
        }

        GlobalScope.launch {
            client.ws(
                method = HttpMethod.Get,
                host = "127.0.0.1",
                port = 8080, path = "/chat"
            ) {
                chatSocket = this
                send(Frame.Text("CONNECT"))

                while (true) {
                    val frame = incoming.receive()
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            mainController.txtMessageBox.text += text + "\n"
                        }
                    }
                }
            }
        }
    }

    fun sayHello() {
        GlobalScope.launch {
            chatSocket.send(Frame.Text(Shared.getHello()))
        }
    }
}