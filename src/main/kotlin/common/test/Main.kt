package common.test

import common.implementation.AgentPlatform
import common.interfaces.BehaviourWithRepository
import common.utils.parse
import kotlin.reflect.full.primaryConstructor

fun main() {


    AgentPlatform.initialize("PC") {

        for (agentConfig in parse()) {

            df(agentConfig.name) {
                for (i in 0..agentConfig.count) {
                    val param = -15f + agentConfig.param * Math.random().toFloat()
                    val primaryConstructor = Class.forName(agentConfig.behavior).kotlin.primaryConstructor!!
                    agent(
                        name = "${System.nanoTime()} param: $param",
                        behaviour = primaryConstructor.call(param) as BehaviourWithRepository
                    )
                }
            }
        }
    }
}