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
        val enabledSource = mutableSetOf<FeedSource>()
        AkFeedConfig.groupPref[this.getGroupOrNull()?.id]
            ?.forEach { enabledSource.add(it) }

        val disabledSource = enumValues<FeedSource>()
            .filter { !enabledSource.contains(it) }

        val msg = StringBuilder("已开启推送消息源：")
        msg.append(enabledSource.map { it.displayName }.joinToString(separator = "\n", prefix = "\n"))

        msg.append("\n\n未开启消息源：")
        msg.append(disabledSource.map { it.displayName }.joinToString(separator = "\n", prefix = "\n"))

        this.sendMessage(msg.toString())
    }
}