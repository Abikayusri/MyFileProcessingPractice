package binar.academy.myfileprocessingpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.academy.myfileprocessingpractice.databinding.ActivityMainBinding
import binar.academy.myfileprocessingpractice.databinding.ActivityVideoHandleBinding

class VideoHandleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}