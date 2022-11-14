package xyz.xorey.Manager

import xyz.xorey.Manager.RegisterManager.registerAll
import xyz.xorey.Manager.RegisterManager.registerCommands
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
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

        jda = JDABuilder.createDefault(Config.token)
            .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
            .registerAll()
            .build()
            .awaitReady()
            .registerCommands()
        log("Bot is ready! ${jda.selfUser.name} - ${jda.selfUser.id} on ${jda.guilds.size} guilds")

        CommandHandler.registerMap()
        Database.connect()

        shutdown()
    }
}

fun log(message: String) {
    println("[SYSTEM] $message")
}

fun main(args: Array<String>) {
    Bot();
}

fun shutdown() {
    while (true) {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val input = reader.readLine()
        if (input == "shutdown") {
            log("Shutting down...")
            Bot.instance.jda.shutdown()
            System.exit(0)
            break
        }
    }
}