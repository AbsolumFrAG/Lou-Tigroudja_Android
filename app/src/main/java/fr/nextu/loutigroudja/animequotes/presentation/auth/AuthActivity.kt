package fr.nextu.loutigroudja.animequotes.presentation.auth

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.ui.AppBarConfiguration
import fr.nextu.loutigroudja.animequotes.R
import fr.nextu.loutigroudja.animequotes.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (registered()) {
            showLoginFragment()
        } else {
            showRegisterFragment()
        }
    }

    private fun registered(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("has_pin_code", false)
    }

    private fun showLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, LoginFragment())
            .commit()
    }

    private fun showRegisterFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, RegisterFragment())
            .commit()
    }
}