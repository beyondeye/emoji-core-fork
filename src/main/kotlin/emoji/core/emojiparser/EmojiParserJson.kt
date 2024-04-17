package emoji.core.emojiparser

import emoji.core.model.NetworkEmoji
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
internal class DataRoot(val annotations:DataRootContent)

@Serializable
internal class DataIdentity(val language:String)

@Serializable
internal class DataRootContent(val identity:DataIdentity,val annotations:Map<String,SingleCharMetaData>)


@Serializable
internal class SingleCharMetaData(val default:List<String>,val tts:List<String>)


internal class EmojiParserJson : EmojiParser {
    override fun parseEmojiData(
        data: String,
        isSkinTonesSupported: Boolean,
    ): List<NetworkEmoji> {
        val emojis = mutableListOf<NetworkEmoji>()
        val dataRoot:DataRoot = JsonDecoder.decodeFromString(data)
        return emojis
    }
    companion object {
        val JsonDecoder by lazy {Json{ ignoreUnknownKeys = true }  }
    }
}