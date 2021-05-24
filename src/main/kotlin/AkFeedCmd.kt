package com.hguandl.akfeed.qqbot

import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.command.UserCommandSender
import net.mamoe.mirai.console.command.getGroupOrNull

object AkFeedCmd : CompositeCommand(
    MiraiPluginAkFeed, "akfeed",
    description = "明日方舟喂饼机"
) {
    @SubCommand
    suspend fun ConsoleCommandSender.add(groupId: Long) {
        AkFeedConfig.groupPref.putIfAbsent(groupId, mutableSetOf(FeedSource.AK_WEIBO, FeedSource.AK_ANNO))
    }

    @SubCommand
    suspend fun UserCommandSender.list() {
        val msg = StringBuilder("已开启推送消息源：")
        AkFeedConfig.groupPref[this.getGroupOrNull()?.id]
            ?.forEach { msg.append("\n" + it.displayName) }
        this.sendMessage(msg.toString())
    }
}