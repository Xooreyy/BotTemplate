package xyz.xorey.db.Models

import org.bson.Document
import org.bson.conversions.Bson
import xyz.xorey.Manager.Bot
import xyz.xorey.db.Communication.Database

class Member {
    companion object {
        fun createMember() {
            Database.createSchema("member")
        }

        fun findOrCreateMember(memberId: String, guildId: String) {
            val coll = Database.mongoDB.getCollection("member")
            val found = coll.find(Document("memberId", memberId)).first()
            if (found != null) {
                return
            }
            val doc = Document("memberId", memberId)
                .append("memberGuild", Bot.instance.jda.getGuildById(guildId)?.id)
            coll.insertOne(doc)
        }
    }
}