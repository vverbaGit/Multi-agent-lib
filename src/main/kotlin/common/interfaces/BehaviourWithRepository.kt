package common.interfaces

import common.message.Message
import java.util.Optional

fun interface BehaviourWithRepository : Behaviour {

    fun getRepository() : Optional<Repository<Message>> = Optional.empty()

    suspend fun onReceiveWithRepo(message: Message, agent: Agent, mts: DirectoryFacilitator) {
        save(message)
        onReceive(message, agent, mts)
    }

    fun save(t: Message) = getRepository().ifPresent {r -> r.save(t) }
}