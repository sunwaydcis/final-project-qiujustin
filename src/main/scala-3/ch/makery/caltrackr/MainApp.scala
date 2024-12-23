

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.collections.ObservableBuffer
import scalafx.Includes._
import javafx.scene.{Scene => JFXScene}
import javafx.fxml.FXMLLoader

import FoodEntry
import ch.makery.address.view.DailyLayoutController

object MainApp extends JFXApp3:
  // Data
  val foodEntries = new ObservableBuffer[FoodEntry]()
  var dailyCalorieGoal = 2000

  // Scene/stage references
  private var rootScene: Scene = null
  private var dailyViewController: DailyLayoutController = null

  override def start(): Unit =
    // Load root layout
    val loader = new FXMLLoader(getClass.getResource("view/RootLayout.fxml"))
    val rootPane = loader.load[javafx.scene.layout.BorderPane]

    // Load daily view into center
    val dailyLoader = new FXMLLoader(getClass.getResource("view/DailyView.fxml"))
    rootPane.setCenter(dailyLoader.load[javafx.scene.layout.VBox])
    dailyViewController = dailyLoader.getController[DailyLayoutController]

    // Create scene
    stage = new PrimaryStage:
      title = "CalTrackr"
      scene = new Scene(rootPane)

    // Add some sample data
    addSampleData()

  private def addSampleData(): Unit =
    foodEntries += FoodEntry("Oatmeal", 150, 6, 27, 3, 100, "Breakfast")
    foodEntries += FoodEntry("Banana", 105, 1.3, 27, 0.3, 120, "Breakfast")
    foodEntries += FoodEntry("Chicken Salad", 350, 35, 10, 18, 250, "Lunch")

  def showFoodDialog(mealType: String): Unit =
    // TODO: Implement food entry dialog
    // This will be similar to PersonEditDialog from AddressApp
    println(s"Add food to $mealType")
end MainApp