package common.test

import common.implementation.AgentPlatform
import common.utils.parse

fun main() {

    val agentPlatform = parse().platform

    AgentPlatform.initialize("PC") {

        df("producers") {
            for (i in 0..agentPlatform.count) {
                val param = -15f + 20f * Math.random().toFloat()
                agent(
                    name = "${System.nanoTime()} param: $param",
                    behaviour = ProducerAgentBehavior(param)
                )
            }
        }

        df("consumers") {
            for (i in 0..agentPlatform.count) {
                val param = -15f + 20f * Math.random().toFloat()
                agent(
                    name = "${System.nanoTime()} param: $param",
                    behaviour = ConsumerAgentBehaviour(param)
                )
            }
        }
    }
}