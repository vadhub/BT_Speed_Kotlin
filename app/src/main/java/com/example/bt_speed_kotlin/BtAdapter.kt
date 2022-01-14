package com.example.bt_speed_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bt_speed_kotlin.databinding.ListitemBinding

class BtAdapter(private val listener: listenOfListBT) : ListAdapter<BtItem, BtAdapter.Holder_Item>(Comparator_Item()) {

    //этот класс будет содержать все шаблоны списка
    class Holder_Item(view: View) : RecyclerView.ViewHolder(view){
        //возьмём все элементы из view и поместим в наш класс, чтобы не искать по айди
        val binding = ListitemBinding.bind(view)

        fun setData(item: BtItem, listener: listenOfListBT) = with(binding){
            textViewName.text = item.BT_ItemName
            textViewMac.text = item.BT_ItemMAC
            itemView.setOnClickListener{
                listener.onClick(item)
            }
        }

        companion object{
            //функция которая для каждого элемента создаёт свой Holder_Item, но с разными ссылками
            fun create(parent: ViewGroup): Holder_Item{
                return Holder_Item(LayoutInflater.from(parent.context).inflate(R.layout.listitem,
                    parent, false))
            }
        }
    }

    //класс, который просто сравнивает элементы, есть ли у нас уже такой в списке или нет
    class Comparator_Item : DiffUtil.ItemCallback<BtItem>(){
        //сравнивает мак адрес
        override fun areItemsTheSame(oldItem: BtItem, newItem: BtItem): Boolean {
            return oldItem.BT_ItemMAC == newItem.BT_ItemMAC
        }
        //сравнивает имя
        override fun areContentsTheSame(oldItem: BtItem, newItem: BtItem): Boolean {
            return oldItem == newItem
        }

    }

    //создаём поля для каждого элемента, то есть создаём Holder_Item для каждой позиции
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder_Item {
        return Holder_Item.create(parent)
    }
    //и заполняем эти поля значениями для каждой позиции
    override fun onBindViewHolder(holder: Holder_Item, position: Int) {
        holder.setData(getItem(position), listener)
    }

    //создадим интерфейс для передачи нажатия в ListActivityBT
    interface listenOfListBT{
        fun onClick(item: BtItem)
    }
}