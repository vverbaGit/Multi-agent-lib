package common.interfaces

import common.message.Message

fun interface Behaviour {

    suspend fun onReceive(message: Message, agent: Agent,  mts: DirectoryFacilitator)
}