package com.alysonsantos.homes

fun String.replaceHome(home: String) = replace(CONFIG_KEY_HOME, home, ignoreCase = true)
fun String.replaceHomeList(homes: String) = replace(CONFIG_KEY_HOME_LIST, homes, ignoreCase = true)
fun String.replaceTarget(target: String) = replace(CONFIG_KEY_TARGET, target, ignoreCase = true)
