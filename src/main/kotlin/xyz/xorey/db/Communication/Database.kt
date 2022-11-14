package xyz.xorey.db.Communication

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import xyz.xorey.Manager.Config
import xyz.xorey.db.Models.Guild
import xyz.xorey.db.Models.Member
import xyz.xorey.db.Models.User

class Database {
    companion object {
        val mongoClientURI: MongoClientURI = MongoClientURI(Config.mongoUri)
        val mongoClient: MongoClient = MongoClient(mongoClientURI)
        val mongoDB: MongoDatabase = mongoClient.getDatabase("BotDB")

        fun connect() {
            Guild.createGuild()
            Member.createMember()
            User.createUser()
        }
        fun createSchema(name: String) {
            if (mongoDB.listCollectionNames().contains(name)) {
                return
            }

            mongoDB.createCollection(name)
        }
    }
}