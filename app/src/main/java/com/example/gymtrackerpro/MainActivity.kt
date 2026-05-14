package com.example.gymtrackerpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gymtrackerpro.data.GymDatabase
import com.example.gymtrackerpro.navigation.AppNavigation
import com.example.gymtrackerpro.repository.RutinaRepository
import com.example.gymtrackerpro.viewmodel.RutinaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = GymDatabase.getDatabase(this)
        val repository = RutinaRepository(database.rutinaDao())
        
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RutinaViewModel(repository) as T
            }
        }
        
        val viewModel = ViewModelProvider(this, viewModelFactory)[RutinaViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
