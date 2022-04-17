package com.sygame.mysqlcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {

    lateinit var schreiben: EditText
    lateinit var speichern: MaterialButton

    private val api by lazy { ApiRetrofit().endPoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        supportActionBar!!.title = "Note Baru"

        setupView()
        setupListener()
    }

    private fun setupView(){
        schreiben = findViewById(R.id.schreiben)
        speichern = findViewById(R.id.speichern)
    }

    private fun setupListener(){
        speichern.setOnClickListener {
            if (schreiben.text.toString().isNotEmpty()){
                Log.e("AddActivity",schreiben.text.toString())
                api.createData(schreiben.text.toString())
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

                    }

                })
            }else{
                Toast.makeText(applicationContext, "Du musst zuerst schreiben!",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }
}