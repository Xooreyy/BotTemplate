package xyz.xorey.Commands

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import xyz.xorey.Handler.Interfaces.onCommand
import org.bson.Document
import xyz.xorey.Handler.Interfaces.Command
import xyz.xorey.db.Communication.Database

@Command("prefix", "changes the prefix", "prefix [PREFIX]", "CategoryTypes.Utility", false, ["943586896947322951"], "ADMINISTRATOR")
class PrefixCommand : onCommand {
    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "prefix", "new one", true)
                .setMinLength(1)
                .setMaxLength(3)
        )
    }

    override fun run(
        args: Array<String?>,
        channel: TextChannel,
        member: Member?,
        content: String,
        message: Message,
        category: Category?,
        event: MessageReceivedEvent
    ) {
        if (args.isEmpty()) {
            channel.sendMessage("no args").queue()
        }

        val prefix = args[0]

        val found = Database.mongoDB.getCollection("guild").find(Document("guildId", event.guild.id)).first()
        if (found != null) {
            val update = Document("prefix", prefix)
            Database.mongoDB.getCollection("guild").updateOne(found, Document("\$set", update))
        } else {
            val doc = Document("prefix", prefix).append("prefix", prefix)
            Database.mongoDB.getCollection("guild").insertOne(doc)
        }

        channel.sendMessage("changed the Prefix to `$prefix`").queue()
    }

    override fun run(channel: TextChannel, member: Member?, event: SlashCommandInteractionEvent) {
        val prefix = event.getOption("prefix")?.asString

        val found = Database.mongoDB.getCollection("guild").find(Document("guildId", event.guild?.id)).first()
        if (found != null) {
            val update = Document("prefix", prefix)
            Database.mongoDB.getCollection("guild").updateOne(found, Document("\$set", update))
        } else {
            val doc = Document("prefix", prefix).append("prefix", prefix)
            Database.mongoDB.getCollection("guild").insertOne(doc)
        }

        event.reply("changed the Prefix to `$prefix`").setEphemeral(true).queue()
    }
}