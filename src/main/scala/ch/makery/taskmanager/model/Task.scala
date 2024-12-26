package ch.makery.taskmanager.model

import scalafx.beans.property.{StringProperty, ObjectProperty, BooleanProperty}
import ch.makery.taskmanager.util.Database
import scalikejdbc.*
import scala.util.{Try, Success, Failure}

class Task(val titleS: String) extends Database:
  var title = new StringProperty(titleS)
  var priority = new StringProperty("Normal")
  var completed = BooleanProperty(false)

  def save(): Try[Int] =
    if (!isExist) then
      Try(DB autoCommit { implicit session =>
        sql"""
        INSERT INTO task (title, priority, completed) VALUES
        (${title.value}, ${priority.value}, ${completed.value})
        """.update.apply()
      })
    else
      Try(DB autoCommit { implicit session =>
        sql"""
        UPDATE task
        SET priority = ${priority.value},
            completed = ${completed.value}
        WHERE title = ${title.value}
        """.update.apply()
      })

  def delete(): Try[Int] =
    if (isExist) then
      Try(DB autoCommit { implicit session =>
        sql"""
        DELETE FROM task WHERE title = ${title.value}
        """.update.apply()
      })
    else
      throw new Exception("Task does not exist in Database")

  def isExist: Boolean =
    DB readOnly { implicit session =>
      sql"""
      SELECT * FROM task WHERE title = ${title.value}
      """.map(rs => rs.string("title")).single.apply()
    } match
      case Some(x) => true
      case None => false

object Task extends Database:
  def apply(titleS: String, priorityS: String, completedB: Boolean): Task =
    new Task(titleS):
      priority.value = priorityS
      completed.value = completedB

  def initializeTable() =
    DB autoCommit { implicit session =>
      sql"""
      CREATE TABLE task (
        id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        title varchar(200),
        priority varchar(50),
        completed boolean
      )
      """.execute.apply()
    }

  def getAllTasks: List[Task] =
    DB readOnly { implicit session =>
      sql"SELECT * FROM task".map(rs => Task(
        rs.string("title"),
        rs.string("priority"),
        rs.boolean("completed")
      )).list.apply()
    }