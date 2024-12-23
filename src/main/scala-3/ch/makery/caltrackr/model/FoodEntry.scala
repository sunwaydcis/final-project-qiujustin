package ch.makery.caltrackr.model

import ch.makery.caltrackr.FoodEntry
import ch.makery.caltrackr.model.FoodEntry
import scalafx.beans.property.{DoubleProperty, ObjectProperty, StringProperty}

import java.time.LocalDateTime

class FoodEntry(nameS: String, caloriesD: Double):
  var name = new StringProperty(nameS)
  var calories = new ObjectProperty[Double](caloriesD)
  var protein = new ObjectProperty[Double](0.0)
  var carbs = new ObjectProperty[Double](0.0)
  var fat = new ObjectProperty[Double](0.0)
  var servingSize = new ObjectProperty[Double](100.0) // in grams
  var dateTime = ObjectProperty[LocalDateTime](LocalDateTime.now())
  var mealType = new StringProperty("Snack") // Breakfast, Lunch, Dinner, Snack
  var notes = new StringProperty("")

  // Computed property for macronutrient distribution
  def macroRatio: String =
    val total = protein.value + carbs.value + fat.value
    if total == 0 then "0/0/0"
    else
      val pRatio = (protein.value / total * 100).round
      val cRatio = (carbs.value / total * 100).round
      val fRatio = (fat.value / total * 100).round
      s"$pRatio/$cRatio/$fRatio"

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
end FoodEntry