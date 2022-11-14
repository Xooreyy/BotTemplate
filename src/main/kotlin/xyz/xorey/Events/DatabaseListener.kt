package xyz.xorey.Events

import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.xorey.db.Models.Guild
import xyz.xorey.db.Models.Member
import xyz.xorey.db.Models.User

class DatabaseListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        Guild.findOrCreateGuild(event.guild.id)
        if (event.member?.user?.isBot == false) {
            Member.findOrCreateMember(event.member?.id!!, event.guild.id)
            User.findOrCreateUser(event.member?.id!!)
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        Guild.findOrCreateGuild(event.guild?.id!!)
        if (event.member?.user?.isBot == false) {
            Member.findOrCreateMember(event.member?.id!!, event.guild!!.id)
            User.findOrCreateUser(event.member?.id!!)
        }
    }

    override fun onGuildJoin(event: GuildJoinEvent) {
        Guild.findOrCreateGuild(event.guild.id)
    }
}