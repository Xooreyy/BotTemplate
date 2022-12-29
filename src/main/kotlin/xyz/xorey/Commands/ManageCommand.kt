package xyz.xorey.Commands

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import xyz.xorey.Handler.Interfaces.Command
import xyz.xorey.Handler.Interfaces.onCommand
import xyz.xorey.Manager.Bot
import xyz.xorey.Manager.Config
import xyz.xorey.Manager.Logger
import xyz.xorey.db.Communication.Database

@Command(name = "manage", "(can be changed. Easy Command to set the Bot in no Command mode or Stop it)", "!manage [action]", "CategoryTypes.Moderation", false, ["943586896947322951"])
class ManageCommand : onCommand {
    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "action to perform", true)
                .addChoice("stop", "stopBot")
                .addChoice("dnd", "dndBot")
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
            channel.sendMessage("Please provide an action").queue()
            return
        }

        if (!Config.developers.contains(event.member?.id)) {
            channel.sendMessage("You dont have permissions to this command, need Permission `DEVELOPER`").queue()
            return
        }

        val choice = args[0]?.toLowerCase()

        when (choice) {
            "stop" -> {
                channel.sendMessage("Stopping bot...").queue { msg ->
                    run {
                        msg.editMessage("Bot stopped").queue()
                        Logger.warn("Shutting down...")
                        Bot.instance.jda.shutdown()
                        System.exit(0)
                    }
                }
            }

            "dnd" -> {
                when {
                    !Bot.botDnd -> {
                        Bot.botDnd = true
                        channel.sendMessage("Bot is now in DND mode").queue()
                    }
                    Bot.botDnd -> {
                        Bot.botDnd = false
                        channel.sendMessage("Bot is now in normal mode").queue()
                    }
                }
            }
        }
    }

    override fun run(channel: TextChannel, member: Member?, event: SlashCommandInteractionEvent) {
        val choice = event.getOption("action")?.asString

        if (!Config.developers.contains(event.member?.id)) {
            event.reply("You dont have permissions to this command, need Permission `DEVELOPER`").setEphemeral(true).queue()
            return
        }

        when (choice) {
            "stopBot" -> {
                event.reply("Stopping bot...").setEphemeral(true).queue { msg ->
                    run {
                        msg.editOriginal("Bot stopped").queue()
                        Logger.warn("Shutting down...")
                        Bot.instance.jda.shutdown()
                        System.exit(0)
                    }
                }
            }
            "dndBot" -> {
                when {
                    !Bot.botDnd -> {
                        Bot.botDnd = true
                        event.reply("Bot is now in DND mode").setEphemeral(true).queue()
                    }
                    Bot.botDnd -> {
                        Bot.botDnd = false
                        event.reply("Bot is now in normal mode").setEphemeral(true).queue()
                    }
                }
            }
        }
    }
}