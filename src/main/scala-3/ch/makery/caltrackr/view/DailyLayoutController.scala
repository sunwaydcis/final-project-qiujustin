package ch.makery.caltrackr.view

import ch.makery.caltrackr.MainApp
import ch.makery.caltrackr.model.FoodEntry
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes._
import javafx.event.ActionEvent
import java.time.LocalDateTime
import scalafx.collections.ObservableBuffer
import scala.jdk.CollectionConverters._
import scalafx.beans.property.DoubleProperty
import scalafx.beans.property.StringProperty
import scalafx.beans.value.ObservableValue

class DailyLayoutController:
  @FXML private val caloriesLabel: Label = null
  @FXML private val proteinLabel: Label = null
  @FXML private val carbsLabel: Label = null
  @FXML private val fatLabel: Label = null

  // Breakfast table
  @FXML private val breakfastTable: TableView[FoodEntry] = null
  @FXML private val bNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val bCaloriesColumn: TableColumn[FoodEntry, Number] = null
  @FXML private val bProteinColumn: TableColumn[FoodEntry, Number] = null

  // Lunch table
  @FXML private val lunchTable: TableView[FoodEntry] = null
  @FXML private val lNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val lCaloriesColumn: TableColumn[FoodEntry, Number] = null
  @FXML private val lProteinColumn: TableColumn[FoodEntry, Number] = null

  // Dinner table
  @FXML private val dinnerTable: TableView[FoodEntry] = null
  @FXML private val dNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val dCaloriesColumn: TableColumn[FoodEntry, Number] = null
  @FXML private val dProteinColumn: TableColumn[FoodEntry, Number] = null

  def initialize(): Unit =
    // Initialize breakfast table
    bNameColumn.setCellValueFactory(cellData => cellData.getValue.name)
    bCaloriesColumn.setCellValueFactory(cellData =>
      cellData.getValue.calories.delegate)
    bProteinColumn.setCellValueFactory(cellData =>
      cellData.getValue.protein.delegate)

    // Initialize lunch table
    lNameColumn.setCellValueFactory(cellData => cellData.getValue.name)
    lCaloriesColumn.setCellValueFactory(cellData =>
      cellData.getValue.calories.delegate)
    lProteinColumn.setCellValueFactory(cellData =>
      cellData.getValue.protein.delegate)

    // Initialize dinner table
    dNameColumn.setCellValueFactory(cellData => cellData.getValue.name)
    dCaloriesColumn.setCellValueFactory(cellData =>
      cellData.getValue.calories.delegate)
    dProteinColumn.setCellValueFactory(cellData =>
      cellData.getValue.protein.delegate)

    updateTables()
    updateSummary()

  private def updateTables(): Unit =
    val today = LocalDateTime.now().toLocalDate
    breakfastTable.items = ObservableBuffer(
      MainApp.foodEntries.filtered(entry =>
        entry.dateTime.value.toLocalDate == today &&
          entry.mealType.value == "Breakfast"
      ).asScala.toSeq: _*
    )
    lunchTable.items = ObservableBuffer(
      MainApp.foodEntries.filtered(entry =>
        entry.dateTime.value.toLocalDate == today &&
          entry.mealType.value == "Lunch"
      ).asScala.toSeq: _*
    )
    dinnerTable.items = ObservableBuffer(
      MainApp.foodEntries.filtered(entry =>
        entry.dateTime.value.toLocalDate == today &&
          entry.mealType.value == "Dinner"
      ).asScala.toSeq: _*
    )

  private def updateSummary(): Unit =
    val today = LocalDateTime.now().toLocalDate
    val todayEntries = MainApp.foodEntries
      .filtered(_.dateTime.value.toLocalDate == today)
      .asScala.toSeq

    val totalCals = todayEntries.map(_.calories.value).sum
    val totalProtein = todayEntries.map(_.protein.value).sum
    val totalCarbs = todayEntries.map(_.carbs.value).sum
    val totalFat = todayEntries.map(_.fat.value).sum

    caloriesLabel.setText(f"$totalCals%.0f/${MainApp.dailyCalorieGoal}")
    proteinLabel.setText(f"$totalProtein%.1fg")
    carbsLabel.setText(f"$totalCarbs%.1fg")
    fatLabel.setText(f"$totalFat%.1fg")

  @FXML
  private def handleAddBreakfast(action: ActionEvent): Unit =
    MainApp.showFoodDialog("Breakfast")
    updateTables()
    updateSummary()

  @FXML
  private def handleAddLunch(action: ActionEvent): Unit =
    MainApp.showFoodDialog("Lunch")
    updateTables()
    updateSummary()

  @FXML
  private def handleAddDinner(action: ActionEvent): Unit =
    MainApp.showFoodDialog("Dinner")
    updateTables()
    updateSummary()
end DailyLayoutController