package emoji.core

import emoji.core.datasource.EmojiDataSource
import emoji.core.datasource.EmojiDataSourceImpl
import emoji.core.datasource.EmojiDataSourceJson
import emoji.core.model.NetworkEmoji
import kotlinx.coroutines.runBlocking

internal fun main() = runBlocking {
    val emojis:List<NetworkEmoji>
    val parseJson:Boolean=true
    if(!parseJson) {
        val emojiDataSource: EmojiDataSource = EmojiDataSourceImpl()
        emojis = emojiDataSource.getAllEmojis(null,false)
    } else {
        val emojiDataSource: EmojiDataSource = EmojiDataSourceJson()
        emojis = emojiDataSource.getAllEmojis(null,false)
    }
    println(emojis)
}
