package xyz.xorey.Manager

import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

class Logger {
    companion object {
        private val logger = LoggerFactory.getILoggerFactory() as LoggerContext
        private val loggerClass = logger.getLogger("System")

        const val ANSI_RESET = "\u001B[0m"
        const val ANSI_BLACK = "\u001B[30m"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_YELLOW = "\u001B[33m"
        const val ANSI_BLUE = "\u001B[34m"
        const val ANSI_PURPLE = "\u001B[35m"
        const val ANSI_CYAN = "\u001B[36m"
        const val ANSI_WHITE = "\u001B[37m"

        private fun log(level: String, message: String) {
            when (level) {
                "info" -> {
                    loggerClass.info(ANSI_GREEN + message + ANSI_RESET)
                }
                "error" -> {
                    loggerClass.error(ANSI_RED + message + ANSI_RESET)
                }
                "warn" -> {
                    loggerClass.warn(ANSI_YELLOW + message + ANSI_RESET)
                }
                "trace" -> {
                    loggerClass.trace(ANSI_BLUE + message + ANSI_RESET)
                }
                "debug" -> {
                    loggerClass.debug(ANSI_CYAN + message + ANSI_RESET)
                }

                "own" -> {
                    loggerClass.info(message)
                }
            }
        }

        fun info(message: String) {
            log("info", message)
        }

        fun error(message: String) {
            log("error", message)
        }

        fun warn(message: String) {
            log("warn", message)
        }

        fun trace(message: String) {
            log("trace", message)
        }

        fun debug(message: String) {
            log("debug", message)
        }

        fun own(message: String) {
            log("own", message)
        }
    }
}