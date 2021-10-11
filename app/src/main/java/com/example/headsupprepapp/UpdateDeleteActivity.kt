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

class UpdateDeleteActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etT1: EditText
    private lateinit var etT2: EditText
    private lateinit var etT3: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnReturn: Button

    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private var cID = 0

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        cID = intent.extras!!.getInt("celebrityID", 0)

        etName = findViewById(R.id.etName)
        etT1 = findViewById(R.id.etT1)
        etT2 = findViewById(R.id.etT2)
        etT3 = findViewById(R.id.etT3)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnReturn = findViewById(R.id.btnRetern)



        btnDelete.setOnClickListener {
            deleteCelebrity()
        }
        btnUpdate.setOnClickListener {
            if(etName.text.isNotEmpty() && etT1.text.isNotEmpty() &&
                etT2.text.isNotEmpty() && etT3.text.isNotEmpty()){
                updateCelebrity()
            }else{
                Toast.makeText(this, "theres empty field", Toast.LENGTH_LONG).show()
            }
        }
        btnReturn.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")

        getCelebrity()

    }

    private fun getCelebrity(){
        progressDialog.show()

        apiInterface.getCelebrity(cID).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                progressDialog.dismiss()
                val celebrity = response.body()!!
                etName.setText(celebrity.name)
                etT1.setText(celebrity.taboo1)
                etT2.setText(celebrity.taboo2)
                etT3.setText(celebrity.taboo3)
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateCelebrity(){
        progressDialog.show()

        apiInterface.updateCelebrity(
            cID,
            Celebrity(
                etName.text.toString(),
                etT1.text.toString(),
                etT2.text.toString(),
                etT3.text.toString(),
                cID
            )).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Celebrity Updated", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteCelebrity(){
        progressDialog.show()

        apiInterface.deleteCelebrity(cID).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Celebrity Deleted", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
}