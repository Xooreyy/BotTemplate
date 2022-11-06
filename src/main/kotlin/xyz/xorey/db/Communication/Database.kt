package xyz.xorey.db.Communication

import xyz.xorey.Manager.log
import java.io.File
import java.io.IOException
import java.sql.*

class Database {
    companion object {

        private var conn: Connection? = null
        private var stmt: Statement? = null

        fun connect() {
            try {
                val file: File = File("database.db")
                if (!file.exists()) {
                    file.createNewFile()
                }

                val url: String = "jdbc:sqlite:${file.path}"
                conn = DriverManager.getConnection(url)
                stmt = conn?.createStatement()

                log("Connected to database")

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun disconnect() {
            try {
                if (conn != null) {
                    conn?.close()
                    log("disconnected from database")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun update(sql: String) {
            try {
                stmt?.execute(sql)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun query(sql: String): ResultSet? {
            try {
                stmt?.executeQuery(sql)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return null
        }
    }
}