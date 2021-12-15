package br.com.fiap.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fiap.firebaseapp.databinding.ActivityHomeBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.btSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

        binding.btnGravar.setOnClickListener {

            val uid = FirebaseAuth.getInstance().uid

            val contato = Contato(
                uid.toString(),
                "Juliana",
                "ju@terra.com.br",
                36,
                "45145-898",
                "Recife")

            FirebaseFirestore.getInstance().collection("contatos")
                .add(contato)
                .addOnSuccessListener(OnSuccessListener {
                    Log.i("avanade", "${it.id}")
                })
                .addOnFailureListener(OnFailureListener {
                    Log.i("avanade", "${it.message}")
                })
        }

    }
}