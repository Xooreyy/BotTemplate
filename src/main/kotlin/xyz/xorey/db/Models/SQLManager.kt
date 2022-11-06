package xyz.xorey.db.Models

import xyz.xorey.db.Communication.Database

class SQLManager {
    companion object {
        fun createTables() {
            //Presents Table
            Database.update("CREATE TABLE IF NOT EXISTS presents(value STRING NOT NULL, name STRING NOT NULL, day STRING NOT NULL, desc STRING, imageUrl STRING)")
            Database.update("CREATE TABLE IF NOT EXISTS guild(prefix STRING NOT NULL DEFAULT \"!\")")
        }

        fun printTables() {
            val result = Database.query("SELECT name FROM sqlite_master WHERE type='table'")
            if (result != null) {
                while (result.next()) {
                    println(result.getString("name"))
                }
            }
        }
    }
}