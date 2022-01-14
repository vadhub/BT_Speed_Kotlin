package com.example.bt_speed_kotlin

import android.bluetooth.BluetoothAdapter

class ConnectBT(private val adapter: BluetoothAdapter) {
    lateinit var threadForConnection: ThreadForBTConnection
    fun connect(mac: String){
        //проверяем включен ли блютуз и не пустой ли мак адрес
        if(adapter.isEnabled && mac.isNotEmpty()) {
            //получаем мак адрес адаптера устройства с которым хотим соединиться
            val device = adapter.getRemoteDevice(mac)
            //если не null, то продолжаем
            device.let {
                threadForConnection = ThreadForBTConnection(it)
                threadForConnection.start()
            }
        }
    }

    fun sendMSG(message: String){
        threadForConnection.readThread.sendMsg(message.toByteArray())
    }
}