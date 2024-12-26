package ch.makery.taskmanager.util

import scalikejdbc.*
import ch.makery.taskmanager.model.Task

trait Database:
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:taskDB;create=true;"

  // initialization of JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  // sesh provider
  given AutoSession = AutoSession

object Database extends Database:
  def setupDB() =
    if (!hasDBInitialize) then
      Task.initializeTable()

  def hasDBInitialize: Boolean =
    DB getTable "Task" match
      case Some(x) => true
      case None => false