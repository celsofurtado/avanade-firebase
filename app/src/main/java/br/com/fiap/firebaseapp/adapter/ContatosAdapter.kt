package br.com.fiap.firebaseapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.firebaseapp.R
import br.com.fiap.firebaseapp.model.Contato
import br.com.fiap.firebaseapp.view.DetailsActivity
import com.bumptech.glide.Glide

class ContatosAdapter(): RecyclerView.Adapter<ContatosAdapter.ContatoViewHolder>() {

    private var contatos = emptyList<Contato>()

    fun updateLista(lista: List<Contato>) {
        contatos = lista
        notifyDataSetChanged()
    }

    class ContatoViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvNome = view.findViewById<TextView>(R.id.tv_recycler_nome)
        val tvEmail = view.findViewById<TextView>(R.id.tv_recycler_email)
        val tvTelefone = view.findViewById<TextView>(R.id.tv_recycler_telefone)
        val cardContato = view.findViewById<CardView>(R.id.card_contato)
        val ivContatoFoto = view.findViewById<ImageView>(R.id.iv_contato_foto_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_contatos_layout, parent, false)

        return ContatoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {

        val contato = contatos[position]

        holder.tvNome.text = contato.nome
        holder.tvEmail.text = contato.email
        holder.tvTelefone.text = contato.tel

        Log.i("avanadexx", contato.urlFoto)

        // Inserir a imagem na Imageview
        Glide
            .with(holder.itemView.context)
            .load(contato.urlFoto)
            .into(holder.ivContatoFoto)

        holder.cardContato.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("doc_id", contato.docId)
            intent.putExtra("valor", 100)
            holder.itemView.context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return contatos.size
    }
}