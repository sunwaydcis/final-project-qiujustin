package ch.makery.caltrackr.view

import ch.makery.caltrackr.MainApp
import ch.makery.caltrackr.model.FoodEntry
import javafx.fxml.FXML
import javafx.scene.control.{TableColumn, TableView}
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

class FoodsLayoutController:
  @FXML private val foodsTable: TableView[FoodEntry] = null
  @FXML private val foodNameColumn: TableColumn[FoodEntry, String] = null
  @FXML private val foodCaloriesColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val foodProteinColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val foodCarbsColumn: TableColumn[FoodEntry, Double] = null
  @FXML private val foodFatColumn: TableColumn[FoodEntry, Double] = null

  def initialize(): Unit =
    foodNameColumn.cellValueFactory = cellData => cellData.value.name
    foodCaloriesColumn.cellValueFactory = { cellData => ObjectProperty(cellData.value.calories.value) }
    foodProteinColumn.cellValueFactory = { cellData => ObjectProperty(cellData.value.protein.value) }
    foodCarbsColumn.cellValueFactory = { cellData => ObjectProperty(cellData.value.carbs.value) }
    foodFatColumn.cellValueFactory = { cellData => ObjectProperty(cellData.value.fat.value) }

    updateFoodsTable()

  private def updateFoodsTable(): Unit =
    foodsTable.items = MainApp.foodEntries

  @FXML
  private def handleAddFood(): Unit =
    // Open food dialog or prompt to add a new food entry
    MainApp.showFoodDialog("Custom")
    updateFoodsTable()
end FoodsLayoutController