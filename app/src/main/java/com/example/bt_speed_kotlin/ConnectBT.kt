package com.example.bt_speed_kotlin

import com.example.bt_speed_kotlin.model.ThreadForBTConnection
import com.example.bt_speed_kotlin.screens.main.DataViewListener

//private val adapter: BluetoothAdapter,
class ConnectBT( var dataViewListener: DataViewListener) {

    lateinit var threadForConnection: ThreadForBTConnection

    fun connect(mac: String){
        //проверяем включен ли блютуз и не пустой ли мак адрес
//        if(adapter.isEnabled && mac.isNotEmpty()) {
            //получаем мак адрес адаптера устройства с которым хотим соединиться
//            val device = adapter.getRemoteDevice(mac)
            //если не null, то продолжаем
//            device.let {it,
                threadForConnection = ThreadForBTConnection( dataViewListener)
                threadForConnection.start()
//            }
//        }
    }

    fun sendMSG(message: String){
        threadForConnection.readThread.sendMsg(message.toByteArray())
    }
}