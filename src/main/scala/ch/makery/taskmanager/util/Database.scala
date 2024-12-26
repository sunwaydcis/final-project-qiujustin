package ch.makery.taskmanager.util

import scalikejdbc.*
import ch.makery.taskmanager.model.Task

trait Database:
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:taskDB;create=true;"

  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider
  given AutoSession = AutoSession

object Database extends Database:
  