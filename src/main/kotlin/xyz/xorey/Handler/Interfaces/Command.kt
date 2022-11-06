package xyz.xorey.Handler.Interfaces

annotation class Command(
    val name: String,
    val description: String,
    val usage: String,
    val category: String,
    val globalCommand: Boolean = false,
    val guilds: Array<String> = [],
    val permission: String = "",
)
