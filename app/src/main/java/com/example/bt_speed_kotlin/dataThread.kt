package com.example.bt_speed_kotlin

import android.app.Activity
import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.TextView
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/*interface Datable{
    fun UpdateTextView(text: String)
}*/

//класс для передачи и приёма данных, которые происходят во второстепенном потоке, а не в главном
class dataThread(val btSocket: BluetoothSocket) : Thread() {
    //потоки для отправки и приёма данных
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null
    init {
        try {
            inputStream = btSocket.inputStream
        }catch (i:IOException){

        }
        try {
            outputStream = btSocket.outputStream
        }catch (i:IOException){

        }
    }



    //функция с помощью которой принимаем данные от МК
    override fun run() {
        //создаём буфер данных для передачи
        val bufferData = ByteArray(1)
        val rByte: Byte = 13//байт символа возврата каретки
        val nByte: Byte = 10//байт символа переноса строки
        var fullMessage: String = ""
        while (true){
            try {
                inputStream?.read(bufferData)
                if(bufferData[0] == rByte || bufferData[0] == nByte){
                    Log.d("MyLog", "Message: $fullMessage")
                    fullMessage=""
                    continue
                }
                    val message = String(bufferData, 0, 1)
                    fullMessage = fullMessage + message
            }catch (i:IOException){
                break
            }
        }
    }

    //создадим функцию для отправки данных
    fun sendMsg(byteArray: ByteArray){
        try {
            outputStream?.write(byteArray)
        }catch (i:IOException){

        }
    }

    /*fun changeTextView(str: String){
        val myString:String = str
        datable.UpdateTextView(str)
    }*/
}

