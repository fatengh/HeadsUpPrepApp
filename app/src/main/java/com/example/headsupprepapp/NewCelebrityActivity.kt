package com.example.headsupprepapp


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewCelebrityActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etT1: EditText
    private lateinit var etT2: EditText
    private lateinit var etT3: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnReturn: Button
    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }
    private lateinit var progressDialog: ProgressDialog
    private lateinit var existingCelebrities: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_celebrity)

        existingCelebrities = intent.extras!!.getStringArrayList("celebrityNames")!!

        etName = findViewById(R.id.etName)
        etT1 = findViewById(R.id.etT1)
        etT2 = findViewById(R.id.etT2)
        etT3 = findViewById(R.id.etT3)

        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            if(etName.text.isNotEmpty() && etT1.text.isNotEmpty() &&
                etT2.text.isNotEmpty() && etT3.text.isNotEmpty()){
                addCelebrity()
            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }

        btnReturn = findViewById(R.id.btnRetern)
        btnReturn.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addCelebrity(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.show()

        apiInterface.addCelebrity(
            Celebrity(
                etName.text.toString().capitalize(),
                etT1.text.toString(),
                etT2.text.toString(),
                etT3.text.toString(),
                0
            )
        ).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                progressDialog.dismiss()
                if(!existingCelebrities.contains(etName.text.toString().lowercase())){
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@NewCelebrityActivity, "Celebrity Already Exists", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@NewCelebrityActivity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }
}