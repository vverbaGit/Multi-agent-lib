package common.interfaces

import common.message.Message


/**
 * Behaviour of agent. Functional interface for realize functionality of agent.
 *
 */
fun interface Behaviour {

    suspend fun onReceive(message: Message, agent: Agent,  mts: DirectoryFacilitator)
}