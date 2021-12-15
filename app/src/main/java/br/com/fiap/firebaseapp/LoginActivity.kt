package br.com.fiap.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.firebaseapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.btCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        binding.btLogin.setOnClickListener{
            autenticar()
        }

        // *** Verificar se usuário já está autenticado.
        val usuarioAutenticado = FirebaseAuth.getInstance().currentUser

        if (usuarioAutenticado != null) {
            entrar()
        }

    }

    private fun autenticar() {
        val email = binding.etCadastroEmail.text.toString()
        val password = binding.etCadastroPassword.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful){
                    entrar()
                }
            })
            .addOnFailureListener(OnFailureListener {
                try {
                    throw it
                } catch (e: FirebaseNoSignedInUserException) {
                    Toast.makeText(this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show()
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun entrar() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}