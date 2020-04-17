package com.alysonsantos.homes

import br.com.devsrsouza.kotlinbukkitapi.config.adapter.ChangeColor
import br.com.devsrsouza.kotlinbukkitapi.exposed.DatabaseTypeConfig

object Config {
    var database = SQLConfig
    var messages = MessagesConfig

    val default_home_name = "home"
    var limit = mapOf(
            "default" to 5,
            "vip" to 25
    )
}

object MessagesConfig {
    @ChangeColor var home_limit_arrived = "&cVocê alcançou o limite de homes. "
    @ChangeColor var only_in_game = "Comando disponível apenas in-game."
    @ChangeColor var  home_set = "&aHome $CONFIG_KEY_HOME setada com sucesso!"
    @ChangeColor var no_permission = "\n &cVocê não possui permissão. \n "
    @ChangeColor var home_deleted = "&ceA home '$CONFIG_KEY_HOME' foi deletada com sucesso!"
    @ChangeColor var home_not_found = "&cA home '$CONFIG_KEY_HOME' não existe."
    @ChangeColor var none_home = "&cVocê não possui homes."
    @ChangeColor var home_list = "&aSuas homes: §7$CONFIG_KEY_HOME_LIST"
    @ChangeColor var home_teleport = "&aVocê foi teleportado para a home '&7$CONFIG_KEY_HOME'&a com sucesso!"
    @ChangeColor var home_not_public = "§cO jogador $CONFIG_KEY_TARGET não possui uma home com este nome!"
}

object SQLConfig : DatabaseTypeConfig() {
    var table = "homes"


}