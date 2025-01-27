package common.interfaces

import common.message.Message
import kotlinx.coroutines.flow.SharedFlow

/**
 * A Message Transport Service (MTS) is the default communication method between agents on different APs.
 *
 */
interface MessageTransportService : SharedFlow<Message>