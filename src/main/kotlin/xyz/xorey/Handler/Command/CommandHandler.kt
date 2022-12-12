package xyz.xorey.Handler.Command

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import onCommand
import org.reflections8.Reflections
import xyz.xorey.Handler.Interfaces.Command
import xyz.xorey.Manager.Bot
import xyz.xorey.Manager.Config
import xyz.xorey.Manager.Logger
import java.util.*
import java.util.regex.Pattern


class CommandHandler {
    companion object {
        val commands: HashMap<String, Pair<Command, onCommand>> = HashMap<String, Pair<Command, onCommand>>()

        fun registerMap() {
            val reflections = Reflections("${Config.mainPackage}.Commands")
            for (clazz in reflections.getSubTypesOf(onCommand::class.java)) {
                val Annontation: Command = clazz.getAnnotation(Command::class.java)
                val Interface: onCommand = clazz.cast(clazz.newInstance())
                this.commands[Annontation.name] = Pair(Annontation, Interface)
                Logger.info("Found Command: ${Annontation.name} | ${Annontation.description}")
            }
        }

        fun getValues(): MutableCollection<Pair<Command, onCommand>> {
            return commands.values
        }

        fun getKeys(): MutableSet<String> {
            return commands.keys
        }

        fun run(event: MessageReceivedEvent) {
            val message = event.message.contentRaw

            if (!message.startsWith(Config.prefix, true)) {
                return;
            }
            val split: Array<String?> = message.replaceFirst(("(?i)" + Pattern.quote(Config.prefix)).toRegex(), "")
                .split("\\s+".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()

            val command = split[0]

            if (command != "manage") {
                if (Bot.botDnd) {
                    return
                }
            }


            if (commands.containsKey(command)) {
                if (commands[command]!!.first.permission != "") {
                    val permission = Permission.valueOf(commands[command]!!.first.permission)
                    if (event.member?.permissions?.contains(permission) == false) {
                        event.channel.sendMessage("You dont have the Permission for this Command. `${commands[command]?.first?.permission}`").queue()
                        return
                    }
                }
                val args: Array<String?> = split.copyOfRange(1, split.size)

                commands[command]?.second?.run(args, event.channel.asTextChannel(), event.message.member, event.message.contentRaw, event.message, event.message.category, event)
            }
        }

        fun run(event: SlashCommandInteractionEvent) {
            val command: String = event.name

            if (command != "manage") {
                if (Bot.botDnd) {
                    return
                }
            }

            if(commands.containsKey(command)) {
                if (commands[command]!!.first.permission != "") {
                    val permission = Permission.valueOf(commands[command]!!.first.permission)
                    if (event.member?.permissions?.contains(permission) == false) {
                        event.reply("You dont have the Permission for this Command. `${commands[command]?.first?.permission}`").setEphemeral(true).queue()
                        return
                    }
                }
                commands[command]?.second?.run(event.channel.asTextChannel(), event.member, event)
            }
        }
    }
}