package com.example.retrofitogreniyorum

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitogreniyorum.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usersLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersLayout = binding.root as LinearLayout

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        service.getData().enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { users ->
                        for (user in users) {
                            // Her kullanıcı için yeni bir LinearLayout oluştur
                            val userLayout = LinearLayout(this@MainActivity).apply {
                                orientation = LinearLayout.VERTICAL
                                setPadding(0, 16, 0, 0) // Üstten boşluk ekle
                            }

                            // ID TextView
                            val idTextView = TextView(this@MainActivity).apply {
                                text = "ID: ${user.id}"
                                textSize = 16f
                            }

                            // Name TextView
                            val nameTextView = TextView(this@MainActivity).apply {
                                text = "Name: ${user.name}"
                                textSize = 16f
                            }

                            // Username TextView
                            val usernameTextView = TextView(this@MainActivity).apply {
                                text = "Username: ${user.username}"
                                textSize = 16f
                            }

                            // Email TextView
                            val emailTextView = TextView(this@MainActivity).apply {
                                text = "Email: ${user.email}"
                                textSize = 16f
                            }

                            // LinearLayout'a TextView'leri ekle
                            userLayout.addView(idTextView)
                            userLayout.addView(nameTextView)
                            userLayout.addView(usernameTextView)
                            userLayout.addView(emailTextView)

                            // Ana layout'a kullanıcı layout'unu ekle
                            usersLayout.addView(userLayout)
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Hata oluştu: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println("Bir hata oldu: ${t.message}")
            }
        })
    }
}