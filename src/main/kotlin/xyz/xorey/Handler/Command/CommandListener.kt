package xyz.xorey.Handler.Command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        CommandHandler.run(event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        CommandHandler.run(event)
    }
}