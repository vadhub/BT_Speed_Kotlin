package com.example.bt_speed_kotlin

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*

//класс работающий вне основного потока, для создания сокета(канала связи между МК и телефоном)
class ThreadForBTConnection(private val device: BluetoothDevice) : Thread() {
    //идентификатор службы последовательного порта блютуз
    val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    //создаём сокет
    var btSocket: BluetoothSocket? = null
    lateinit var readThread: dataThread

    //инициализируем сокет с помощью блока инициализации
    init{
        try {
            //здесь создаётся сокет и с помощью него можно производить подключение
            btSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuid))
        }catch (i:IOException){

        }
    }

    //функция будет запускаться на второстепенном потоке
    override fun run() {
        try {
            Log.d("MyLog", "Устанавливаем соединение...")
            btSocket?.connect()
            Log.d("MyLog", "Соединение установлено")
            //запускаем поток и принимаем данные
            readThread = dataThread(btSocket!!)
            readThread.start()
        }catch (i:IOException){
            Log.d("MyLog", "Не удаётся подключиться к устройству")
        }
    }

    fun delConnect(){
        try {
            btSocket?.close()
        }catch (i:IOException){

        }
    }
}