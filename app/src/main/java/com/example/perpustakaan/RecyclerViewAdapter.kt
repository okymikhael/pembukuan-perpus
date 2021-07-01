package com.example.perpustakaan

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init

class RecyclerViewAdapter (private val listdata_buku: ArrayList<data_buku>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private val context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NamaBuku: TextView
        val NamaPenulis: TextView
        val Penerbit: TextView
        val TahunTerbit: TextView
        val ListItem: LinearLayout

        init {
            NamaBuku = itemView.findViewById(R.id.namaBuku)
            NamaPenulis = itemView.findViewById(R.id.namaPenulis)
            Penerbit = itemView.findViewById(R.id.penerbit)
            TahunTerbit = itemView.findViewById(R.id.tahunTerbit)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,
            parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val NamaBuku: String? = listdata_buku.get(position).namaBuku
        val NamaPenulis: String? = listdata_buku.get(position).namaPenulis
        val Penerbit: String? = listdata_buku.get(position).penerbit
        val TahunTerbit: String? = listdata_buku.get(position).tahunTerbit

        holder.NamaBuku.text = "Nama Buku: $NamaBuku"
        holder.NamaPenulis.text = "Nama Penulis: $NamaPenulis"
        holder.Penerbit.text = "Penerbit: $Penerbit"
        holder.TahunTerbit.text = "Tahun Terbit: $TahunTerbit"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener{ view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) {
                            0 -> {
                                val bundle = Bundle()
                                bundle.putString("datanamaBuku", listdata_buku[position].namaBuku)
                                bundle.putString(
                                    "datanamaPenulis",
                                    listdata_buku[position].namaPenulis
                                )
                                bundle.putString("datapenerbit", listdata_buku[position].penerbit)
                                bundle.putString(
                                    "datatahunTerbit",
                                    listdata_buku[position].tahunTerbit
                                )
                                bundle.putString("getPrimaryKey", listdata_buku[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {
                                listener?.onDeleteData(listdata_buku.get(position), position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
                }
            })
        }

        override fun getItemCount(): Int {
            return listdata_buku.size
        }

        var listener: datalistener? = null

        init {
            this.context = context
            this.listener = context as MyListData
        }

        interface datalistener {
            fun onDeleteData(data: data_buku?, position: Int)
        }
    }