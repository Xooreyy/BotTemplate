package xyz.xorey.Manager

import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

class Logger {
    companion object {
        private val logger = LoggerFactory.getILoggerFactory() as LoggerContext
        private val loggerClass = logger.getLogger("System")

        const val ANSI_RESET = "[0m"
        const val ANSI_BLACK = "[30m"
        const val ANSI_RED = "[31m"
        const val ANSI_GREEN = "[32m"
        const val ANSI_YELLOW = "[33m"
        const val ANSI_BLUE = "[34m"
        const val ANSI_PURPLE = "[35m"
        const val ANSI_CYAN = "[36m"
        const val ANSI_WHITE = "[37m"

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