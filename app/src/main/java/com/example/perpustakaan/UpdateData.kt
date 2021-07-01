package com.example.perpustakaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var ceknamaBuku: String? = null
    private var ceknamaPenulis: String? = null
    private var cekpenerbit: String? = null
    private var cektahunTerbit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                ceknamaBuku = new_namaBuku.getText().toString()
                ceknamaPenulis = new_namaPenulis.getText().toString()
                cekpenerbit = new_penerbit.getText().toString()
                cektahunTerbit = new_tahunTerbit.getText().toString()

                if (isEmpty(ceknamaBuku!!) || isEmpty(ceknamaPenulis!!) || isEmpty(cekpenerbit!!) || isEmpty(cektahunTerbit!!)) {
                    Toast.makeText(this@UpdateData, "Data Tidak Boleh Kosong",
                        Toast.LENGTH_SHORT).show()
                } else {
                    val setdata_teman = data_buku()
                    setdata_teman.namaBuku = new_namaBuku.getText().toString()
                    setdata_teman.namaPenulis = new_namaPenulis.getText().toString()
                    setdata_teman.penerbit = new_penerbit.getText().toString()
                    setdata_teman.tahunTerbit = new_tahunTerbit.getText().toString()
                    updateTeman(setdata_teman)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    private val data: Unit
        private get() {
            val getnamaBuku = intent.extras!!.getString("datanamaBuku")
            val getnamaPenulis = intent.extras!!.getString("datanamaPenulis")
            val getpenerbit = intent.extras!!.getString("datapenerbit")
            val gettahunTerbit = intent.extras!!.getString("datatahunTerbit")

            new_namaBuku!!.setText(getnamaBuku)
            new_namaPenulis!!.setText(getnamaPenulis)
            new_penerbit!!.setText(getpenerbit)
            new_tahunTerbit!!.setText(gettahunTerbit)
        }

    private fun updateTeman(teman: data_buku) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("DataBuku")
            .child(getKey!!)
            .setValue(teman)
            .addOnSuccessListener {
                new_namaBuku!!.setText("")
                new_namaPenulis!!.setText("")
                new_penerbit!!.setText("")
                new_tahunTerbit!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil Diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}