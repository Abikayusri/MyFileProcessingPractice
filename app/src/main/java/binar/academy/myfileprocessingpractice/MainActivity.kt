package binar.academy.myfileprocessingpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.academy.myfileprocessingpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnPdfHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, PdfHandleActivity::class.java)
                startActivity(intent)
            }

            btnImageHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, ImageHandleActivity::class.java)
                startActivity(intent)
            }

            btnVideoHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, VideoHandleActivity::class.java)
                startActivity(intent)
            }
        }
    }
}