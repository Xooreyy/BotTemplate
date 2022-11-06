package xyz.xorey.Manager

import xyz.xorey.Handler.Interfaces.Command
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import onCommand
import org.reflections8.Reflections
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object RegisterManager {

    private var loadedClasses = mapOf<String, Any>()

    @OptIn(ExperimentalTime::class)
    fun JDABuilder.registerAll() : JDABuilder {
        val reflections = Reflections("xyz.xorey")

        // Registering both ListenerAdapters and EventListeners
        val listenerTime = measureTime {
            for (clazz in (reflections.getSubTypesOf(ListenerAdapter::class.java) + reflections.getSubTypesOf(
                EventListener::class.java)).distinct()) {
                if (clazz.simpleName == "ListenerAdapter") continue

                val constructor = clazz.getDeclaredConstructor()
                constructor.trySetAccessible()

                val listener = constructor.newInstance()
                loadedClasses += clazz.simpleName to listener
                addEventListeners(listener)
                log("Registered listener: ${listener.javaClass.simpleName}")
            }
        }
        log("Registered listeners in $listenerTime")

        return this
    }

    @OptIn(ExperimentalTime::class)
    fun JDA.registerCommands(): JDA {
        val reflections = Reflections("xyz.xorey")
        val guildIds = Bot.instance.properties.getProperty("guilds", "").split(",").toTypedArray()

        // Registering commands
        val commandsTime = measureTime {
            for (clazz in reflections.getTypesAnnotatedWith(Command::class.java)) {
                val annotation = clazz.getAnnotation(Command::class.java)
                val data = Commands.slash(annotation.name, annotation.description)

                if (clazz.simpleName !in loadedClasses) {
                    val constructor = clazz.getDeclaredConstructor()
                    constructor.trySetAccessible()

                    val command = constructor.newInstance()
                    loadedClasses += clazz.simpleName to command
                    log("Registered command class: ${command.javaClass.simpleName}")
                }

                val command = loadedClasses[clazz.simpleName]

                if (command is onCommand) {
                    data.addOptions(command.getOptions())
                }

                if(annotation.globalCommand) {
                    upsertCommand(data).queue()
                    log("Registered global command: ${annotation.name}")
                } else {
                    for (guildID in annotation.guilds) {
                        getGuildById(guildID)?.let { guild ->
                            guild.upsertCommand(data).queue()
                            log("Registered command: ${annotation.name} in guild: ${guild.name}")
                        }
                    }
                }
            }
        }
        log("Registered commands in $commandsTime")

        return this
    }
}