package ch.makery.caltrackr.model

import scalafx.beans.property.{StringProperty, IntegerProperty,
  ObjectProperty}
import java.time.LocalDate
class Person ( firstNameS : String, lastNameS : String ):
  var firstName = new StringProperty(firstNameS)
  var lastName = new StringProperty(lastNameS)
  var weight = new StringProperty("some Street")
  var height = new StringProperty("some Street")