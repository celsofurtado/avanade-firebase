package br.com.fiap.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fiap.firebaseapp.databinding.ActivityCadastroBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.btNovoUsuario.setOnClickListener {
            criarConta()
        }

    }

    private fun criarConta() {
        val email = binding.etCadastroEmail.text.toString()
        val password = binding.etCadastroPassword.text.toString()

        // Primeira coisa: Obter a instância do FirebaseAuthentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(OnSuccessListener {
                Log.i("avanade", it.user!!.uid)
            })
            .addOnFailureListener(OnFailureListener {
                try {
                    throw it
                } catch (e: FirebaseAuthUserCollisionException) {
                    Log.i("avanade", e.message.toString())
                } catch (e: FirebaseAuthWeakPasswordException) {
                    Log.i("avanade", e.message.toString())
                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.i("avanade", e.message.toString())
                } catch (e: Exception) {
                    Log.i("avanade", e.message.toString())
                }
            })

    }
}