package rafaelfcm.com.github.ecodicas.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import rafaelfcm.com.github.ecodicas.model.Dica
import rafaelfcm.com.github.ecodicas.R

class DicaAdapter(
    private val context: Context,
    private val dicas: List<Dica>
) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicas[position]
        holder.tvTitulo.text = dica.titulo
        holder.tvDescricao.text = dica.descricao

        holder.itemView.setOnClickListener {
            Toast.makeText(context, dica.curiosidade, Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dica.link))
            context.startActivity(intent)
            true
        }
    }

    override fun getItemCount(): Int = dicas.size
}