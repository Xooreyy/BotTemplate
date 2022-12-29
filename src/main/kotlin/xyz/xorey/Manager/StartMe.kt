package xyz.xorey.Manager

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import xyz.xorey.Manager.RegisterManager.registerAll
import xyz.xorey.Manager.RegisterManager.registerCommands
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory
import xyz.xorey.Handler.Command.CommandHandler
import xyz.xorey.db.Communication.Database
import java.io.BufferedReader
import java.io.InputStreamReader

class Bot {

    companion object {
        var botDnd: Boolean = false
        lateinit var instance: Bot
    }

    val jda: JDA

    init {
        instance = this

        if (Config.loggingDisables) {
            val mongodbLogger = LoggerFactory.getILoggerFactory() as LoggerContext
            mongodbLogger.getLogger("org.mongodb.driver").level = Level.ERROR

            val jdaLogger = LoggerFactory.getILoggerFactory() as LoggerContext
            jdaLogger.getLogger("net.dv8tion.jda").level = Level.ERROR

            val reflectionLogger = LoggerFactory.getILoggerFactory() as LoggerContext
            reflectionLogger.getLogger("org.reflections8").level = Level.ERROR
        }

        jda = JDABuilder.createDefault(Config.token)
            .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
            .registerAll()
            .build()
            .awaitReady()
            .registerCommands()
        Logger.info("Bot is ready! ${jda.selfUser.name} - ${jda.selfUser.id} on ${jda.guilds.size} guilds")

        CommandHandler.registerMap()
        Database.connect()
        shutdown()
    }
}

fun main(args: Array<String>) {
    Bot();
}

fun shutdown() {
    while (true) {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val stoppers = arrayOf("stop", "exit", "shutdown", "quit")
        val input = reader.readLine()
        if (stoppers.contains(input.toLowerCase())) {
            Logger.info("Shutting down...")
            Bot.instance.jda.shutdown()
            System.exit(0)
            break
        }
    }
}