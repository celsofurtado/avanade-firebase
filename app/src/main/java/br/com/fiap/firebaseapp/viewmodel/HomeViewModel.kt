package br.com.fiap.firebaseapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.firebaseapp.model.Contato
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HomeViewModel: ViewModel() {

    private var _contatos = MutableLiveData<ArrayList<Contato>>()
    private lateinit var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        listenContatos()
    }

    // Métodos get e set para _contatos
    internal var getContatos: MutableLiveData<ArrayList<Contato>>
        get() { return _contatos }
        set(value) { _contatos.value }

    private fun listenContatos() {

        val usuarioLogadoId = FirebaseAuth.getInstance().uid

        firestore.collection("contatos")
            .orderBy("nome", Query.Direction.DESCENDING)
            .whereEqualTo("uid", usuarioLogadoId)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val contatos = ArrayList<Contato>()

                if (snapshot != null) {
                    val documents = snapshot.documents
                    documents.forEach {
                        val contato = it.toObject(Contato::class.java)
                        contatos.add(contato!!)
                    }
                }
                _contatos.value = contatos
            }
    }


}