package com.example.bt_speed_kotlin.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.bt_speed_kotlin.screens.main.DataViewListener
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.random.Random

/*interface Datable{
    fun UpdateTextView(text: String)
}*/

//класс для передачи и приёма данных, которые происходят во второстепенном потоке, а не в главном
//btSocket: BluetoothSocket,
class DataThread(var dataViewListener: DataViewListener) : Thread(), TimerChangedListener {

    //потоки для отправки и приёма данных
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private var isRun: Boolean = false;

    private val handler: Handler = Handler(Looper.getMainLooper())

    init {
        try {
//            inputStream = btSocket.inputStream
//            outputStream = btSocket.outputStream
        } catch (i: IOException) {
            println(i)
        }
    }

    //функция с помощью которой принимаем данные от МК
    override fun run() {
        isRun = true
        //создаём буфер данных для передачи
        val bufferData = ByteArray(1)
        val rByte: Byte = 13//байт символа возврата каретки
        val nByte: Byte = 10//байт символа переноса строки

        var fullMessage: Float = 0f

        try {
//               inputStream?.read(bufferData)
//                if (bufferData[0] == rByte || bufferData[0] == nByte) {
//                    Log.d("MyLog", "Message: $fullMessage")
//                    fullMessage=""
//                }
//                val message = String(bufferData, 0, 1)
//                fullMessage += message
            dataViewListener.speedChanged(Random.nextInt(0, 100).toFloat())
            handler.postDelayed(this, 1000)

        } catch (i: IOException) {
            handler.removeCallbacks(this)
            isRun = false
        }
    }

    //создадим функцию для отправки данных
    fun sendMsg (byteArray: ByteArray) {
        try {
//            outputStream?.write(byteArray)
        } catch (i: IOException) {
            println(i)
        }
    }

    override fun onChangedTimer(turn: Boolean) {
        if (turn) {
            Log.i("fff", turn.toString())
            if (!isRun) this.run()
        } else {
            handler.removeCallbacks(this)
            !isRun
            Log.i("fff", turn.toString())
        }
    }

    /*fun changeTextView(str: String){
        val myString:String = str
        datable.UpdateTextView(str)
    }*/
}

