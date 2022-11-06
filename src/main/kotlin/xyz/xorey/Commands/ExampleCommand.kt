package xyz.xorey.Commands

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import onCommand
import xyz.xorey.Handler.Interfaces.Command

@Command("example", "example command", "example", "Utility", false, ["943586896947322951"], "ADMINISTRATOR")
class ExampleCommand : onCommand {
    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "text", "example option", true)
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
            channel.sendMessage("Please provide text").queue()
            return
        }

        val text = args.joinToString(" ").trim()

        channel.sendMessage("You said $text").queue()
    }

    override fun run(channel: TextChannel, member: Member?, event: SlashCommandInteractionEvent) {
        val text = event.getOption("text")?.asString

        event.reply("You said $text").setEphemeral(true).queue()
    }
}