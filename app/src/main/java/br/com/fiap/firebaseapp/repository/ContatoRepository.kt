package br.com.fiap.firebaseapp.repository

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import br.com.fiap.firebaseapp.imagens.converterBitmapToByteArray
import br.com.fiap.firebaseapp.model.Contato
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ContatoRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().getReference("perfis")

    fun gravar(contato: Contato, bitmap: Bitmap, context: Context) {

        firestore.collection("contatos")
            .add(contato)
            .addOnSuccessListener {

                // Gravar a foto no Firebase Storage
                var fotoUri = ""
                var contatoId = it.id

                var nomeFoto = "$contatoId.jpg"

                // Criar uma referência para o arquivo -> imagem
                val fotoPerfilRef = storage.child(nomeFoto)

                fotoPerfilRef.putBytes(converterBitmapToByteArray(bitmap))
                    .addOnSuccessListener(OnSuccessListener {
                        fotoPerfilRef.downloadUrl.addOnSuccessListener(OnSuccessListener {
                            firestore.collection("contatos")
                                .document(contatoId)
                                .update("urlFoto", it.toString())
                        })
                    })

                Toast.makeText(context, "Contato gravado com sucesso!", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(context, "Ocorreu um erro ao gravar!", Toast.LENGTH_SHORT).show()

            }

    }

}