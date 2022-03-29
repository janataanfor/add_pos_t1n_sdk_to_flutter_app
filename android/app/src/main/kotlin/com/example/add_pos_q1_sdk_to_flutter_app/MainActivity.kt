package com.example.add_pos_q1_sdk_to_flutter_app

import android.graphics.BitmapFactory
import androidx.annotation.NonNull
import com.dm.commonlib.ThreadPoolManager
import com.dm.commonlib.Utils
import com.dm.minilib.DMPrinterManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlin.Exception

class MainActivity: FlutterActivity() {
    private val CHANNEL = "osama.flutter.dev/testT1NPrint"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "printOnT1n") {

                var printResult = 0

                printResult = testPrint()

                if (printResult==1) {
                    result.success(1)
                } else {
                    result.error("faild", "somthing went wront.", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        DMPrinterManager.init(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DMPrinterManager.printerStop()
    }

    private fun testPrint():Int {

        val map: MutableMap<String, Int> = java.util.HashMap()
            map[Utils.KEY_ALIGN] = 1
            map[Utils.KEY_TYPEFACE] = 1
            map[Utils.KEY_TEXTSIZE] = "30".toInt()

        return try {
            DMPrinterManager.printTextApi("test printing",map)
            1
        }catch (e: Exception){
            -1
        }
    }

}
