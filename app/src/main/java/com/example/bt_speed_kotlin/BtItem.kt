package com.example.bt_speed_kotlin

import java.io.Serializable

data class BtItem(
    var BT_ItemName: String,
    var BT_ItemMAC: String
): Serializable//значит, что информация будет поступать как поток байтов