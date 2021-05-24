package com.hguandl.akfeed.qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.info

object MiraiPluginAkFeed : KotlinPlugin(
    JvmPluginDescription(
        id = "com.hguandl.akfeed.qqbot.mirai-plugin-akfeed",
        version = "1.0-SNAPSHOT",
        name = "akfeed"
    )
) {
    override fun onEnable() {
        AkFeedConfig.reload()
        AkFeedCmd.register()
        logger.info { "Plugin loaded" }

        val channel = GlobalEventChannel
            .filterIsInstance<BotEvent>()
            .filter { AkFeedConfig.enabledBot.contains(it.bot.id) }

        channel.subscribeAlways<NewFriendRequestEvent> { this.accept() }
        channel.subscribeAlways<BotInvitedJoinGroupRequestEvent> { this.accept() }

        channel.subscribeAlways<BotJoinGroupEvent> {
            AkFeedConfig.groupPref.putIfAbsent(this.groupId, mutableSetOf(FeedSource.AK_WEIBO, FeedSource.AK_ANNO))
            this.group.sendMessage("Hello World")
        }

        channel.subscribeAlways<BotLeaveEvent> {
            AkFeedConfig.groupPref.remove(this.groupId)
        }
    }
}