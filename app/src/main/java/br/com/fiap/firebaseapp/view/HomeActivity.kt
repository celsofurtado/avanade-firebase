package br.com.fiap.firebaseapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.firebaseapp.adapter.ContatosAdapter
import br.com.fiap.firebaseapp.model.Contato
import br.com.fiap.firebaseapp.databinding.ActivityHomeBinding
import br.com.fiap.firebaseapp.viewmodel.HomeViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var adapter: ContatosAdapter
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        // Inicializar a ViewModel
        viewModel = ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)

        adapter = ContatosAdapter()
        binding.rvListaContatos.adapter = adapter
        binding.rvListaContatos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.getContatos.observe(this, Observer {
            adapter.updateLista(it)
        })

        binding.btSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

        binding.btnHomeNovo.setOnClickListener {
            val intent = Intent(this, ContatoActivity::class.java)
            startActivity(intent)
        }





        exibirContatos()

    }

    private fun exibirContatos() {

        // criar uma mutableList
        val contatos = mutableListOf<Contato>()

        FirebaseFirestore.getInstance().collection("contatos")
            .addSnapshotListener { value, _ ->
                contatos.clear()
                for (documento in value!!.documents){
                    val contato = Contato(
                        uid = documento.data?.get("uid").toString(),
                        nome = documento.data?.get("nome").toString(),
                        email = documento.data?.get("email").toString(),
                        tel = documento.data?.get("tel").toString(),
                        cidade = documento.data?.get("cidade").toString(),
                        idade = documento.data?.get("idade").toString().toInt()
                    )
                    contatos.add(contato)
                }
                Log.i("avanade", contatos.toString())

                adapter.updateLista(contatos)

            }

    }
}