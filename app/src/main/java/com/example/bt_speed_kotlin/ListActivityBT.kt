package com.example.bt_speed_kotlin

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bt_speed_kotlin.databinding.ActivityListBtBinding

class ListActivityBT : AppCompatActivity(), BtAdapter.listenOfListBT {
    private var BT_Adapter: BluetoothAdapter? = null

    private lateinit var binding: ActivityListBtBinding
    private lateinit var adapter: BtAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val BT_Manager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        BT_Adapter = BT_Manager.adapter //берём адаптер
        adapter = BtAdapter(this)
        //указываем расположение элементов, то есть один под другим
        binding.btView.layoutManager = LinearLayoutManager(this)
        binding.btView.adapter = adapter
        getPairedDevices()
    }

    private fun getPairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = BT_Adapter?.bondedDevices
        val ListOfBT = ArrayList<BtItem>()//временный список
        pairedDevices?.forEach(){
            ListOfBT.add(BtItem(it.name, it.address))//заполняем список
        }
        //передаём в адаптер
        adapter.submitList(ListOfBT)
    }

    companion object{
        const val KEY_OF_THE_DEVICE = "key_of_the_device"
    }

    override fun onClick(item: BtItem) {
        val i = Intent().apply {
            putExtra(KEY_OF_THE_DEVICE, item)
        }
        setResult(RESULT_OK, i)
        finish()
    }
}