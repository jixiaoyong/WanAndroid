package cf.android666.wanandroid.utils

import android.os.Handler
import android.os.Message
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.net.URLConnection
import kotlin.concurrent.thread

/**
 * Created by jixiaoyong on 2018/2/4.
 */
object DownloadUtil {

    fun downloadJson(urlStr: String, handler: Handler, msgWhat: Int) {

        thread {
            kotlin.run {

                var url = URL(urlStr)
                var urlConn: URLConnection = url.openConnection()
                var input: InputStream = urlConn.getInputStream()
                var bufferedReader = BufferedReader(InputStreamReader(input))

                var stringBuffer = StringBuffer()

                var line = ""

                while (true) {

                    line = bufferedReader.readLine() ?: break
                    stringBuffer.append(line)

                }

                var msg = Message()

                msg.what = msgWhat

                msg.obj = String(stringBuffer)

                handler.sendMessage(msg)
            }
        }

    }

}
