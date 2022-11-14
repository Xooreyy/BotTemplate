package xyz.xorey.db.Models

import org.bson.Document
import org.bson.conversions.Bson
import xyz.xorey.Manager.Bot
import xyz.xorey.db.Communication.Database

class User {
    companion object {
        fun createUser() {
            Database.createSchema("user")
        }

        fun findOrCreateUser(userId: String) {
            val coll = Database.mongoDB.getCollection("user")
            val found = coll.find(Document("userId", userId)).first()
            if (found != null) {
                return
            }
            val doc = Document("userId", userId)
                .append("globalban", false)
            coll.insertOne(doc)
        }
    }
}