package controllers

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.stage.Stage
import java.net.URL
import java.util.*


class MainController : Application(), Initializable {

    @FXML
    private lateinit var chatController: ChatController // fxml file must have label "chat" for it to link here

    @FXML
    private lateinit var topBarController: TopBarController // fxml file must have label "topbar" for it to link here


    override fun start(primaryStage: Stage?) {
        // Load the scene
        val loader = FXMLLoader(
            javaClass.getResource("/views/MainView.fxml")
        )
        val scene = Scene(loader.load())
        scene.root.requestFocus()

        // Set up the scene
        primaryStage!!.title = "Suki's Poker Club"
        primaryStage.scene = scene
        primaryStage.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // Link sub controllers
        chatController.mainController = this
        topBarController.mainController = this
    }

    fun startServerConnections(address: String) {
        chatController.startConnection(address)
    }
}