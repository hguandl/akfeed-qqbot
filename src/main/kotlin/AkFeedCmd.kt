package com.hguandl.akfeed.qqbot

import net.mamoe.mirai.console.command.*

object AkFeedCmd : CompositeCommand(
    MiraiPluginAkFeed, "akfeed",
    description = "明日方舟喂饼机"
) {
    @SubCommand
    @Description("（仅限控制台使用）添加 Bot")
    suspend fun ConsoleCommandSender.add(botId: Long) {
        AkFeedConfig.enabledBot.add(botId)
    }

    @SubCommand
    @Description("（仅限控制台使用）移除 Bot")
    suspend fun ConsoleCommandSender.remove(botId: Long) {
        AkFeedConfig.enabledBot.remove(botId)
    }

    @SubCommand
    @Description("列出本群当前订阅情况")
    suspend fun CommandSenderOnMessage<*>.list() = this.getGroupOrNull()?.let {
        val enabledSource = mutableSetOf<FeedSource>()
        AkFeedConfig.groupPref[this.getGroupOrNull()?.id]
            ?.forEach { enabledSource.add(it) }

        val disabledSource = enumValues<FeedSource>()
            .filter { !enabledSource.contains(it) }

        val msg = StringBuilder("已开启推送消息源：")
        msg.append(enabledSource.joinToString(separator = "\n", prefix = "\n") { it.displayName })

        msg.append("\n\n未开启消息源：")
        msg.append(disabledSource.joinToString(separator = "\n", prefix = "\n") { it.displayName })

        this.sendMessage(msg.toString())
    } ?: run {
        this.sendMessage("请在群聊中使用此命令")
    }
}