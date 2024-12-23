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

class DailyLayoutController:
  @FXML private val caloriesLabel: Label = null
  @FXML private val proteinLabel: Label = null
  @FXML private val carbsLabel: Label = null
  @FXML private val fatLabel: Label = null

  // Breakfast table
  @FXML private val breakfastTable: TableView[FoodEntry] = null
  @FXML private val bNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val bCaloriesColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val bProteinColumn: TableColumn[FoodEntry, Double] = null

  // Lunch table
  @FXML private val lunchTable: TableView[FoodEntry] = null
  @FXML private val lNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val lCaloriesColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val lProteinColumn: TableColumn[FoodEntry, Double] = null

  // Dinner table
  @FXML private val dinnerTable: TableView[FoodEntry] = null
  @FXML private val dNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val dCaloriesColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val dProteinColumn: TableColumn[FoodEntry, Double] = null

  def initialize(): Unit =
    // Initialize breakfast table
    bNameColumn.cellValueFactory = {_.value.name}
    bCaloriesColumn.cellValueFactory = {_.value.calories}
    bProteinColumn.cellValueFactory = {_.value.protein}

    // Initialize lunch table
    lNameColumn.cellValueFactory = {_.value.name}
    lCaloriesColumn.cellValueFactory = {_.value.calories}
    lProteinColumn.cellValueFactory = {_.value.protein}

    // Initialize dinner table
    dNameColumn.cellValueFactory = {_.value.name}
    dCaloriesColumn.cellValueFactory = {_.value.calories}
    dProteinColumn.cellValueFactory = {_.value.protein}

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
    // Convert filtered list to Scala collection
    val todayEntries = MainApp.foodEntries
      .filtered(_.dateTime.value.toLocalDate == today)
      .asScala.toSeq // Convert to Scala Seq

    // Perform calculations
    val totalCals = todayEntries.map(_.calories.value).sum
    val totalProtein = todayEntries.map(_.protein.value).sum
    val totalCarbs = todayEntries.map(_.carbs.value).sum
    val totalFat = todayEntries.map(_.fat.value).sum

    // Update UI labels
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