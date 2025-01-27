package common.interfaces

/**
 * A interface of AMS. Is a system where all agents and other components works together to complete tasks.
 *
 */
interface AgentManagementSystem : DirectoryFacilitator {

    val messageTransportService: MessageTransportService
}