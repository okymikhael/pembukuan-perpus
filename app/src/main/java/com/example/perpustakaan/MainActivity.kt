package com.example.perpustakaan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener  {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        show_data.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }
    private fun isEmpty(s: String) : Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()
                val getNamaBuku: String = namaBuku.getText().toString()
                val getNamaPenulis: String = namaPenulis.getText().toString()
                val getPenerbit: String = penerbit.getText().toString()
                val getTahunTerbit: String = tahunTerbit.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNamaBuku) || isEmpty(getNamaPenulis) || isEmpty(getPenerbit) || isEmpty(getTahunTerbit)) {
                    Toast.makeText(this@MainActivity, "Data Tidak Boleh Kosong",
                            Toast.LENGTH_SHORT).show()
                } else {
                    getReference.child("Admin").child(getUserID).child("DataBuku").push()
                            .setValue(data_buku(getNamaBuku, getNamaPenulis, getPenerbit, getTahunTerbit))
                            .addOnCompleteListener(this) {
                                namaBuku.setText("")
                                namaPenulis.setText("")
                                penerbit.setText("")
                                tahunTerbit.setText("")
                                Toast.makeText(this@MainActivity, "Data Tersimpan",
                                        Toast.LENGTH_SHORT).show()
                            }
                }
            }
            R.id.logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(p0: Task<Void>){
                                Toast.makeText(this@MainActivity, "Logout Berhasil",
                                        Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            }
            R.id.show_data -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}