package br.com.fiap.firebaseapp.model

data class Contato(
    var uid: String = "",
    var nome: String = "",
    var email: String = "",
    var idade: Int = 0,
    var tel: String = "",
    var cidade: String = "",
    var urlFoto: String = ""
)
