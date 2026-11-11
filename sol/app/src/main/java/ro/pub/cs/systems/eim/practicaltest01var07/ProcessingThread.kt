package ro.pub.cs.systems.eim.practicaltest01

import android.content.Intent
import android.content.Context
import android.util.Log
import kotlin.math.sqrt

class ProcessingThread(private val context: Context) : Thread() {
    private var isRunning = true
    private val actions = listOf(
        "action1"
    )

    fun getRandom() : Float {
        var a : Int = (1..100).random()
        var b : Int = (1..100).random()
        return a.toFloat() / b.toFloat()
    }

    override fun run() {
        while (isRunning) {
            val intent = Intent()
            intent.action = "action1"
            intent.putExtra("message", getRandom().toString() + " " + getRandom().toString() + " " + getRandom().toString() + " " + getRandom().toString())
            context.sendBroadcast(intent)

            try {
                sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun stopThread() {
        isRunning = false
    }
}
