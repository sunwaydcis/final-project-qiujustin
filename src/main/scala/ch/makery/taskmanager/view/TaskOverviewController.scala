package ch.makery.taskmanager.view

import ch.makery.taskmanager.MainApp
import ch.makery.taskmanager.model.Task
import javafx.fxml.FXML
import javafx.scene.control.{TableView, TableColumn, ComboBox}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.Includes.*
import javafx.event.ActionEvent
import scalafx.collections.ObservableBuffer
import scala.util.{Success, Failure}
import scalafx.beans.property.StringProperty
import java.util.Optional
import scala.jdk.javaapi.OptionConverters.*
import scala.jdk.OptionConverters.RichOptional

class TaskOverviewController:
  @FXML private var taskTable: TableView[Task] = null
  @FXML private var titleColumn: TableColumn[Task, String] = null
  @FXML private var priorityColumn: TableColumn[Task, String] = null
  @FXML private var completedColumn: TableColumn[Task, String] = null
  @FXML private var filterComboBox: ComboBox[String] = null

  def initialize() =
    taskTable.items = MainApp.taskData

    titleColumn.cellValueFactory = {_.value.title}
    priorityColumn.cellValueFactory = {_.value.priority}
    completedColumn.cellValueFactory = { cellData =>
      val completedValue = if (cellData.value.completed.value) "Complete" else "Pending"
      new StringProperty(completedValue)
    }

    filterComboBox.items = ObservableBuffer("All", "Pending", "Completed")
    filterComboBox.value = "All"

    filterComboBox.onAction = (_: ActionEvent) => filterTasks()

  private def filterTasks() =
    val filteredData = MainApp.taskData.filter(task =>
      filterComboBox.getValue match
        case "All" => true
        case "Pending" => !task.completed.value
        case "Completed" => task.completed.value
    )
    taskTable.setItems(filteredData)

  def handleNewTask(action: ActionEvent) =
    val dialog = new javafx.scene.control.TextInputDialog("New Task")
    dialog.setHeaderText("Enter task title")
    dialog.setContentText("Title:")

    val result: Optional[String] = dialog.showAndWait()
    result.toScala match
      case Some(taskTitle) if taskTitle.nonEmpty =>
        val task = new Task(taskTitle)
        task.save() match
          case Success(_) =>
            MainApp.taskData += task
          case Failure(e) =>
            val alert = new Alert(AlertType.Error):
              initOwner(MainApp.stage)
              title = "Database Error"
              headerText = "Could not save task"
              contentText = e.getMessage
            alert.showAndWait()
      case _ => // Do nothing if canceled or empty

  def handleDeleteTask(action: ActionEvent) =
    val selectedIndex = taskTable.selectionModel().selectedIndex.value
    if selectedIndex >= 0 then
      val task = taskTable.selectionModel().selectedItem.value
      task.delete() match
        case Success(_) =>
          MainApp.taskData.remove(selectedIndex)
        case Failure(e) =>
          val alert = new Alert(AlertType.Error):
            initOwner(MainApp.stage)
            title = "Database Error"
            headerText = "Could not delete task"
            contentText = e.getMessage
          alert.showAndWait()
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      alert.showAndWait()

  def handleMarkImportant(action: ActionEvent) =
    val selectedTasks = taskTable.selectionModel().selectedItem.value
    if selectedTasks != null then
      selectedTasks.priority.value = if selectedTasks.priority.value == "Normal" then "Important" else "Normal"
      selectedTasks.save() match
        case Success(_) =>
          taskTable.refresh()
        case Failure(e) =>
          val alert = new Alert(AlertType.Error):
            initOwner(MainApp.stage)
            title = "Database Error"
            headerText = "Could not update task"
            contentText = e.getMessage
          alert.showAndWait()
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      alert.showAndWait()

  def handleToggleComplete(action: ActionEvent) =
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if selectedTask != null then
      selectedTask.completed.value = !selectedTask.completed.value
      selectedTask.save() match
        case Success(_) =>
          taskTable.refresh()
        case Failure(e) =>
          val alert = new Alert(AlertType.Error):
            initOwner(MainApp.stage)
            title = "Database Error"
            headerText = "Could not update task"
            contentText = e.getMessage
          alert.showAndWait()
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      alert.showAndWait()