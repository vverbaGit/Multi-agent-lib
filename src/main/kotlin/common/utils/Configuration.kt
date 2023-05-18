package common.utils

data class Platform(val count : Int,
                    val param: Int,
                    val name: String)

data class Configuration(val platform: Platform)