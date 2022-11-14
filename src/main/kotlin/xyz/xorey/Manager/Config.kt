package xyz.xorey.Manager

class Config {
    companion object {
        val config = this

        val loggingDisables: Boolean = false
        val mainPackage = "xyz.xorey"
        val mongoUri = ""
        val prefix: String = "!"
        val token: String = "SECRET"
        val botName: String =  "Bot"
    }
}
