package emoji.core.datasource

import emoji.core.emojifetcher.EmojiFetchCallback
import emoji.core.emojifetcher.EmojiFetcher
import emoji.core.emojifetcher.EmojiFetcherImpl
import emoji.core.model.NetworkEmoji
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private object EmojiFetcherConstants {
    // original file is in https://unicode.org/Public/emoji/latest/emoji-test.txt
    const val UNICODE_EMOJIS_URL = "https://makeappssimple.com/hosting/emoji_core/emoji.txt"
}


public class EmojiDataSourceImpl : EmojiDataSource {
    override suspend fun getAllEmojis(
        cacheFile: File?,
        isSkinToneSupported:Boolean,
    ): List<NetworkEmoji> {
        val emojiFetcher: EmojiFetcher = EmojiFetcherImpl(
            cacheFile = cacheFile,
            isSkinToneSupported = isSkinToneSupported
        )
        return suspendCoroutine { continuation ->
            emojiFetcher.fetchEmojiData(
                url = EmojiFetcherConstants.UNICODE_EMOJIS_URL,
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
