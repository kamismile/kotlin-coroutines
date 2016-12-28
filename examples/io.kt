import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.suspendCoroutine

suspend fun AsynchronousFileChannel.aRead(buf: ByteBuffer): Int =
    suspendCoroutine { c ->
        read(buf, 0L, Unit, object : CompletionHandler<Int, Unit> {
            override fun completed(bytesRead: Int, attachment: Unit) {
                c.resume(bytesRead)
            }

            override fun failed(exception: Throwable, attachment: Unit) {
                c.resumeWithException(exception)
            }
        })
    }
