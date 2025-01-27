package common.implementation

import common.interfaces.MessageTransportService
import common.message.Message
import kotlinx.coroutines.flow.SharedFlow

/**
 * Message transport service impl
 *
 * @constructor
 *
 * @param broadcastChannel
 */
class MessageTransportServiceImpl(
    broadcastChannel: SharedFlow<Message>
) : MessageTransportService, SharedFlow<Message> by broadcastChannel