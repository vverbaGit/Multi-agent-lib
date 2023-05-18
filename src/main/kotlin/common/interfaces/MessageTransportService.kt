package common.interfaces

import common.message.Message
import kotlinx.coroutines.flow.SharedFlow

interface MessageTransportService : SharedFlow<Message>