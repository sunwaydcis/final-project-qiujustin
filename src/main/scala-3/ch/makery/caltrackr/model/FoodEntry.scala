package ch.makery.caltrackr.model

import scalafx.beans.property.{StringProperty, ObjectProperty, DoubleProperty}
import java.time.LocalDateTime

class FoodEntry(nameS: String, caloriesD: Double):
  var name = StringProperty(nameS)
  var calories = DoubleProperty(caloriesD)
  var protein = DoubleProperty(0.0)
  var carbs = DoubleProperty(0.0)
  var fat = DoubleProperty(0.0)
  var servingSize = DoubleProperty(100.0)
  var dateTime = ObjectProperty(LocalDateTime.now())
  var mealType = StringProperty("Snack")
  var notes = StringProperty("")

  // Computed property for nutrient distribution
  def macroRatio: String =
    val total = protein.value + carbs.value + fat.value
    if total == 0 then "0/0/0"
    else
      val pRatio = (protein.value / total * 100).round
      val cRatio = (carbs.value / total * 100).round
      val fRatio = (fat.value / total * 100).round
      s"$pRatio/$cRatio/$fRatio"

  // Property methods for JavaFX bindings
  def nameProperty = name
  def caloriesProperty = calories
  def proteinProperty = protein
  def carbsProperty = carbs
  def fatProperty = fat
  def servingSizeProperty = servingSize
  def dateTimeProperty = dateTime
  def mealTypeProperty = mealType
  def notesProperty = notes

end FoodEntry

object FoodEntry:
  def apply(
             nameS: String,
             caloriesD: Double,
             proteinD: Double,
             carbsD: Double,
             fatD: Double,
             servingSizeD: Double,
             mealTypeS: String
           ): FoodEntry =
    new FoodEntry(nameS, caloriesD):
      protein.value = proteinD
      carbs.value = carbsD
      fat.value = fatD
      servingSize.value = servingSizeD
      mealType.value = mealTypeS

  def apply(nameS: String, caloriesD: Double): FoodEntry =
    new FoodEntry(nameS, caloriesD)
end FoodEntry
