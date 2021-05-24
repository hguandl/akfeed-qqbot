package com.hguandl.akfeed.qqbot

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object AkFeedConfig : AutoSavePluginConfig("AkFeed") {
    val groupPref: MutableMap<Long, MutableSet<FeedSource>> by value()
}
