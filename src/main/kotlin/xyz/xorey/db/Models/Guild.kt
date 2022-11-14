package xyz.xorey.db.Models

import org.bson.Document
import org.bson.conversions.Bson
import xyz.xorey.Manager.Bot
import xyz.xorey.db.Communication.Database

class Guild {
    companion object {
        fun createGuild() {
            Database.createSchema("guild")
        }

        fun findOrCreateGuild(guildId: String) {
            val coll = Database.mongoDB.getCollection("guild")
            val found = coll.find(Document("guildId", guildId)).first()
            if (found != null) {
                return
            }
            val doc = Document("guildId", guildId)
                .append("guildName", Bot.instance.jda.getGuildById(guildId)?.name)
                .append("prefix", "!")
                .append("counterChannel", null)
                .append("logWebhook", null)
                .append("counter", 1)
                .append("tempChannels", ArrayList<String>())
            coll.insertOne(doc)
        }
    }
}