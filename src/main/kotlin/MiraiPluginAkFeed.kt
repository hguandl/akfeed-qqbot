package com.hguandl.akfeed.qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.utils.info

object MiraiPluginAkFeed : KotlinPlugin(
    JvmPluginDescription(
        id = "com.hguandl.akfeed.qqbot.mirai-plugin-akfeed",
        version = "1.0-SNAPSHOT",
    )
) {
    override fun onEnable() {
        AkFeedConfig.reload()
        AkFeedCmd.register()
        logger.info { "Plugin loaded" }

        Bot.instances
            .filter { AkFeedConfig.groupPref.containsKey(it.id) }
            .forEach {
                it.eventChannel.subscribeAlways<NewFriendRequestEvent> { this.accept() }
                it.eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> { this.accept() }
            }
    }
}