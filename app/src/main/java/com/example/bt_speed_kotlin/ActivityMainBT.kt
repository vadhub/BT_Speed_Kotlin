package com.example.bt_speed_kotlin

import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bt_speed_kotlin.databinding.ActivityMainBtBinding
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class ActivityMainBT : AppCompatActivity() {
    private lateinit var binding: ActivityMainBtBinding
    private lateinit var activityListLauncher: ActivityResultLauncher<Intent>
    lateinit var connectBT: ConnectBT
    private var btItem: BtItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        whatResultOfBtList()
        init()
    }

    private fun init(){
        val BT_Manager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val BT_Adapter = BT_Manager.adapter //берём адаптер
        connectBT = ConnectBT(BT_Adapter)
    }
    //передаём меню в наше активити
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //расширяем наше меню
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //обрабатываем нажатие кнопок в меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.button_listBT){
            //при нажатии на кнопку button_listBT открываем активити со списком устройств
            activityListLauncher.launch(Intent(this, ListActivityBT::class.java))
        }
        else if(item.itemId == R.id.button_connect){
            btItem.let {
                connectBT.connect(it?.BT_ItemMAC!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //функция для получения выбранного устройства БТ из одного активити в другое
    private fun whatResultOfBtList(){
        activityListLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                btItem = it.data?.getSerializableExtra(ListActivityBT.KEY_OF_THE_DEVICE) as BtItem
            }
        }
    }

    fun sendA(view: View){
        connectBT.sendMSG("A")
    }
    fun sendB(view: View){
        connectBT.sendMSG("B")
    }

    /*override fun UpdateTextView(text: String) {
        val txtView = findViewById<View>(R.id.textViewSpeed) as TextView
        txtView.text = text
    }*/
}