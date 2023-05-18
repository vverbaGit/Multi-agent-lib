@file:Suppress("UNCHECKED_CAST")

package common.test

import common.implementation.RepositoryImpl
import common.interfaces.*
import common.message.Message
import kotlinx.coroutines.delay
import java.util.*

abstract class AbstractAgentBehavior<T>(
    protected val x: T,
    private val checker: MutableList<Check<Any>> = mutableListOf()
) : BehaviourWithRepository {

    companion object {

        const val CODE_CHECK = 0b001
        const val CODE_HAND_SHAKE = 0b010
        const val CODE_SUBMIT_HAND_SHAKE = 0b100
        const val CODE_ERROR = 0b1000

    }

    private val appropriateIds = mutableListOf<String>()
    private val inappropriateIds = mutableListOf<String>()
    private val repository = RepositoryImpl<Message>()

    override fun getRepository(): Optional<Repository<Message>> {
        return Optional.of(repository);
    }

    override suspend fun onReceive(message: Message, agent: Agent, mts: DirectoryFacilitator) {
        if (appropriateIds.contains(message.senderId)) {
            return
        }
        if (inappropriateIds.contains(message.senderId)) {
            return
        }
        delay(1000)
        when (message.code) {
            CODE_CHECK -> if (check(message.data as T)) {
                println("Accepted by  ${message.receiverId}")
                mts.send(Message(agent.identifier, message.senderId, x, CODE_HAND_SHAKE))
            } else {
                mts.send(Message(agent.identifier, message.senderId, code = CODE_ERROR))
                mts.send(Message(agent.identifier, endpoint(mts), code = CODE_CHECK, data = x))
            }

            CODE_HAND_SHAKE -> if (check(message.data as T)) {
                mts.send(Message(agent.identifier, message.senderId, code = CODE_SUBMIT_HAND_SHAKE))
            } else {
                mts.send(Message(agent.identifier, message.senderId, code = CODE_ERROR))
                mts.send(Message(agent.identifier, endpoint(mts), code = CODE_CHECK, data = x))
            }

            CODE_SUBMIT_HAND_SHAKE -> {
                println("Submitted by ${message.senderId}")
                appropriateIds.add(message.senderId)
                mts.send(Message(agent.identifier, message.senderId, code = CODE_SUBMIT_HAND_SHAKE, data = x))
                mts.send(Message(agent.identifier, endpoint(mts), code = CODE_CHECK, data = x))
            }

            CODE_ERROR -> {
                println("Denied by ${message.senderId}")
                inappropriateIds.add(message.senderId)
                mts.send(Message(agent.identifier, message.senderId, code = CODE_ERROR))
                mts.send(Message(agent.identifier, endpoint(mts), code = CODE_CHECK, data = x))
            }
        }
    }

    protected abstract fun endpoint(mts: DirectoryFacilitator): String

    protected fun check(x: T): Boolean {
        return checker.stream().allMatch { check -> check.test(x as Any) }
    }

}
