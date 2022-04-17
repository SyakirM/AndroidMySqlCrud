package com.sygame.mysqlcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {

    lateinit var updateText: EditText
    lateinit var aktualisieren: MaterialButton
    private val api by lazy { ApiRetrofit().endPoint }
    private val note by lazy { intent.getSerializableExtra("note") as NotesModel.Data }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar!!.title = "Update"
        setupView()
        setupListener()
    }

    private fun setupView(){
        updateText = findViewById(R.id.updateText)
        aktualisieren = findViewById(R.id.aktualisieren)
        updateText.setText( note.note )
    }

    private fun setupListener(){
        aktualisieren.setOnClickListener {
            if (updateText.text.isNotEmpty()){
                api.updateData(note.id!!,updateText.text.toString())
                    .enqueue(object : Callback<SubmitModel>{
                        override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                            if (response.isSuccessful){
                                val submit = response.body()
                                Toast.makeText(applicationContext,
                                    submit!!.message, Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }
            else{
                Toast.makeText(applicationContext,
                    "Du musst zuerst schreiben!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}