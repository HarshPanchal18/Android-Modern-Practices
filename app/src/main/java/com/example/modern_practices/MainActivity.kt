package com.example.modern_practices

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.modern_practices.Grid.GridRecyclerView
import com.example.modern_practices.Linear.LinearItemsAdapter
import com.example.modern_practices.Linear.LinearRecyclerView
import com.example.modern_practices.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearRvBtn.setOnClickListener {
            startActivity(Intent(this, LinearRecyclerView::class.java))
        }

        binding.gridRvBtn.setOnClickListener {
            startActivity(Intent(this, GridRecyclerView::class.java))
        }
    }
}
