package br.com.fiap.firebaseapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.firebaseapp.model.Contato
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class DetailsViewModel: ViewModel() {

    private var _contato = MutableLiveData<Contato>()
    private  var firestore: CollectionReference

    init {
        firestore = FirebaseFirestore.getInstance().collection("contatos")
    }

    fun fetch(docId: String) {
        getContato(docId)
    }

    internal var getContato: MutableLiveData<Contato>
        get() { return _contato }
        set(value) { _contato.value }

    private fun getContato(docId: String) {

        firestore.document(docId)
            .get()
            .addOnSuccessListener(OnSuccessListener {
                val contato = it.toObject(Contato::class.java)
                _contato.value = contato
            })

    }

}