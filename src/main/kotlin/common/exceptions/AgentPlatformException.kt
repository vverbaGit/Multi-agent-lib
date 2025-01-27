package common.exceptions

import java.lang.Exception

sealed class AgentPlatformException : Exception()

open class UnrecognizedException: AgentPlatformException()
