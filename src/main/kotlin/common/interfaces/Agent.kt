package common.interfaces

import common.message.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel

/**
 * A interface of agent.
 * An agent is a computational process that implements the autonomous, communicating functionality of an application.
 * Agents communicate using an Agent Communication Language.
 * An Agent is the fundamental actor on an AP which combines one or more service capabilities,
 * as published in a service description, into a unified and integrated execution model.
 * An agent must have at least one owner, for example,
 * based on organisational affiliation or human user ownership,
 * and an agent must support at least one notion of identity.
 * This notion of identity is the Agent Identifier (AID) that labels an agent so that it may be distinguished unambiguously within the Agent Universe.
 * An agent may be registered at a number of transport addresses at which it can be contacted.
 *
 */
interface Agent : SendChannel<Message>, CoroutineScope {

    val name: String

    val identifier: String

    val capacity: Int

    val parent: DirectoryFacilitator
}