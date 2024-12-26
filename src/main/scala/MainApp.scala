package ch.makery.taskmanager

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.collections.ObservableBuffer
import scalafx.Includes.*
import javafx.{scene => jfxs}
import javafx.fxml.FXMLLoader
import ch.makery.taskmanager.model.Task
import ch.makery.taskmanager.util.Database
import scalafx.scene.image.Image

object MainApp extends JFXApp3:
  // Tasks data
  val taskData = ObservableBuffer[Task]()

  // Initialize database
  Database.setupDB()

  // Root layout
  private var rootLayout: Option[jfxs.layout.BorderPane] = None

  override def start(): Unit =
    // Load existing tasks from database
    taskData ++= Task.getAllTasks

    stage = new PrimaryStage:
      title = "Task Manager"
      icons += new Image(getClass.getResource(
        "/images/icon.png").toExternalForm)
      scene = new Scene:
        root = loadRootLayout()

    showTaskOverview()

  private def loadRootLayout(): jfxs.layout.BorderPane =
    val resource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(resource)
    rootLayout = Some(loader.load[jfxs.layout.BorderPane]())
    rootLayout.get

  private def showTaskOverview(): Unit =
    val resource = getClass.getResource("view/TaskOverview.fxml")
    val loader = new FXMLLoader(resource)
    val taskOverview = loader.load[jfxs.layout.AnchorPane]()
    rootLayout.get.setCenter(taskOverview)