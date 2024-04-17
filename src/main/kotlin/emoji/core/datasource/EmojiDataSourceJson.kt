package emoji.core.datasource

import emoji.core.emojifetcher.EmojiFetchCallback
import emoji.core.emojifetcher.EmojiFetcher
import emoji.core.emojifetcher.EmojiFetcherJson
import emoji.core.model.NetworkEmoji
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// translations are also available!
//"https://github.com/unicode-org/cldr-json/blob/main/cldr-json/cldr-annotations-full/annotations/en/annotations.json"
private const val JsonUrl="https://raw.githubusercontent.com/unicode-org/cldr-json/main/cldr-json/cldr-annotations-full/annotations/en/annotations.json"

public class EmojiDataSourceJson : EmojiDataSource {
    override suspend fun getAllEmojis(
        cacheFile: File?,
        isSkinToneSupported:Boolean,
    ): List<NetworkEmoji> {
        val emojiFetcher: EmojiFetcher = EmojiFetcherJson(
            cacheFile = cacheFile,
            isSkinToneSupported = isSkinToneSupported
        )
        return suspendCoroutine { continuation ->
            emojiFetcher.fetchEmojiData(
                url = JsonUrl,
                callback = object : EmojiFetchCallback {
                    override fun onFetchSuccess(
                        emojis: List<NetworkEmoji>,
                    ) {
                        continuation.resume(
                            value = emojis,
                        )
                    }

                    override fun onFetchFailure(
                        ioException: IOException,
                    ) {
                        continuation.resumeWithException(
                            exception = IOException("Fetch failed: ${ioException.message ?: "An error occurred"}"),
                        )
                    }
                },
            )
        }
    }
}
