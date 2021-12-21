package br.com.fiap.firebaseapp.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.firebaseapp.R
import br.com.fiap.firebaseapp.databinding.ActivityDetailsBinding
import br.com.fiap.firebaseapp.viewmodel.DetailsViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val docId = intent.getStringExtra("doc_id")

        viewModel = ViewModelProvider.NewInstanceFactory().create(DetailsViewModel::class.java)
        viewModel.fetch(docId!!)

        // Ativar a observação no ViewModel
        viewModel.getContato.observe(this, Observer {
            binding.etConsultaNome.setText(it!!.nome)
            binding.etConsultaTelefone.setText(it!!.tel)
            binding.etConsultaCidade.setText(it!!.cidade)
            binding.etConsultaIdade.setText(it!!.idade.toString())
            binding.etConsultaEmail.setText(it!!.email)

            Glide
                .with(this)
                .load(it.urlFoto)
                .into(binding.ivFotoPerfil)
        })

        binding.btnDetailsDelete.setOnClickListener {

            val documentReference = FirebaseFirestore.getInstance().collection("contatos")

            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Excluir contato")
//            builder.setMessage("Ao confirmar, essa ação não poderá ser desfeita.")

            builder.setView(layoutInflater.inflate(R.layout.dialog_delete_layout, null))

            builder.setPositiveButton("Confirmar", DialogInterface.OnClickListener { _, _ ->
                documentReference.document(docId).delete()
                    .addOnSuccessListener(OnSuccessListener {
                        Toast.makeText(this, "Contato excluído com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    })
            })

            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })

            builder.create().show()

        }

    }
}