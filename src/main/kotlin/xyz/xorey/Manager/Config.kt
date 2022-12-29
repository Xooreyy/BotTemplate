package xyz.xorey.Manager

class Config {
    companion object {
        val config = this

        val loggingDisables: Boolean = true
        val mongoUri: String = "YOUR MONGOURI HERE"
        val mainPackage: String = "xyz.xorey"
        val token: String = "YOUR TOKEN HERE"
        val botName: String =  "Bot"
        val developers: List<String> = listOf("YOUR ID HERE")
    }
}
