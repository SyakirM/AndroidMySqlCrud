package com.sygame.mysqlcrud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuPresenter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var create: FloatingActionButton

    private val api by lazy { ApiRetrofit().endPoint }

    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Note Ku"

        setupView()
        setupRecyclerView()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun setupView(){
        recyclerView = findViewById(R.id.recyclerView)
        create = findViewById(R.id.create)
    }

    private fun setupRecyclerView(){
        listAdapter = ListAdapter(arrayListOf(), object : ListAdapter.OnAdapterListener {
            override fun onUpdate(data: NotesModel.Data) {
                startActivity(Intent(this@MainActivity,UpdateActivity::class.java)
                    .putExtra("note", data))
            }

            override fun onDelete(data: NotesModel.Data) {
                deleteDialog(data)
            }

        })
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = listAdapter
    }

    private fun setupListener(){
        create.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddActivity::class.java))
        }
    }
    
    private fun getNote(){
        api.getData().enqueue(object : Callback<NotesModel> {
            override fun onFailure(call: Call<NotesModel>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }
            override fun onResponse(call: Call<NotesModel>, response: Response<NotesModel>) {
                if (response.isSuccessful){
                    val listData = response.body()!!.notes
                    //untuk menampilkan data ke recyclerView
                    listAdapter.setData(listData)

                    /*
                    listData.forEach {
                        Log.e("MainActivity", "note ${it.note}")
                    }
                     */
                }

            }
        })
    }

    private fun deleteDialog(data: NotesModel.Data){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Delete Note")
            setMessage("Bist du dir sicher?")
            setNegativeButton("Nein",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Ja",DialogInterface.OnClickListener { dialogInterface, i ->
                api.deleteData(data.id!!).enqueue(object : Callback<SubmitModel>{
                    override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                        if (response.isSuccessful){
                            val submit = response.body()
                            Toast.makeText(applicationContext,
                                submit!!.message, Toast.LENGTH_SHORT).show()
                            getNote()
                        }
                    }

                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            })
        }
        alertDialog.show()
    }
}