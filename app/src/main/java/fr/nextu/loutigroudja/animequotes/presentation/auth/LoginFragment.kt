package fr.nextu.loutigroudja.animequotes.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import fr.nextu.loutigroudja.animequotes.databinding.FragmentLoginBinding
import fr.nextu.loutigroudja.animequotes.presentation.MainActivity
import java.util.concurrent.Executor

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var etPinCode: EditText
    private lateinit var bLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        executor = ContextCompat.getMainExecutor(requireContext())

        etPinCode = binding.etPinCode
        bLogin = binding.bLogin

        if (BiometricManager.from(requireContext())
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
            ) {
            showBiometricPrompt()
        }

        bLogin.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val correctPin = sharedPreferences.getString("pin_code", "")
            val enteredPin = etPinCode.text.toString()

            authWithPin(enteredPin, correctPin!!)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun authWithPin(enteredPin: String, correctPin: String) {
        if (enteredPin == correctPin) {
            showToast("Authentification réussie")
            startActivity(Intent(context, MainActivity::class.java))
        } else {
            showToast("PIN incorrect")
        }
    }

    private fun showBiometricPrompt() {
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("$errString")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentification échouée")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    showToast("Authentification réussie")
                    startActivity(Intent(context, MainActivity::class.java))
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Connexion biométrique")
            .setSubtitle("Se connecter en utilisant vos informations biométrique")
            .setNegativeButtonText("Utiliser un code PIN")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}