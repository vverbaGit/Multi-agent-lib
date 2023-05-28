package common.interfaces

import common.message.Message
import java.util.Optional

/**
 * Behaviour of agent. Functional interface for realize functionality of agent.
 * Also added repository, that save data before onReceive method.
 */
fun interface BehaviourWithRepository : Behaviour {

    fun getRepository() : Optional<Repository<Message>> = Optional.empty()

    suspend fun onReceiveWithRepo(message: Message, agent: Agent, mts: DirectoryFacilitator) {
        save(message)
        onReceive(message, agent, mts)
    }

    fun save(t: Message) = getRepository().ifPresent {r -> r.save(t) }
}