package br.com.fiap.firebaseapp.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.firebaseapp.R
import br.com.fiap.firebaseapp.databinding.ActivityContatoBinding
import br.com.fiap.firebaseapp.model.Contato
import br.com.fiap.firebaseapp.repository.ContatoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

const val CODE_IMAGE = 5000

class ContatoActivity : AppCompatActivity() {

    lateinit var binding: ActivityContatoBinding
    private var fotoPerfil: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContatoBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.btnContatoGravar.setOnClickListener {
            gravarContato()
        }

        binding.cardViewContatoFoto.setOnClickListener {
            abrirGaleria()
        }

    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Escolha uma foto"), CODE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        if (requestCode == CODE_IMAGE && resultCode == RESULT_OK) {
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)
            fotoPerfil = BitmapFactory.decodeStream(fluxoImagem)
            binding.ivContatoFoto.setImageBitmap(fotoPerfil)
        }

    }

    private fun gravarContato() {
        // obter o uid do usuário logado
        val uid = FirebaseAuth.getInstance().uid

        val contato = Contato(
            uid = uid.toString(),
            nome = binding.etContatoNome.text.toString(),
            email = binding.etContatoEmail.text.toString(),
            tel = binding.etContatoTelefone.text.toString(),
            cidade = binding.etContatoCidade.text.toString(),
            idade = binding.etContatoIdade.text.toString().toInt()
        )

        val repository = ContatoRepository()
        repository.gravar(contato, fotoPerfil!!,this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Usuário cadastrado com sucesso.")
        builder.setMessage("Seu contato foi gravado com sucesso!!")
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}