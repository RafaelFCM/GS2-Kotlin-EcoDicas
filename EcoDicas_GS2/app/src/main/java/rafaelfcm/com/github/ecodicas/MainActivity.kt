package rafaelfcm.com.github.ecodicas

//Rafael Fiel Cruz Miranda - RM94654
//Luca Moraes Zaharic RM95794
//3SIR

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rafaelfcm.com.github.ecodicas.R.*
import rafaelfcm.com.github.ecodicas.adapter.DicaAdapter
import rafaelfcm.com.github.ecodicas.database.DicaDatabaseHelper
import rafaelfcm.com.github.ecodicas.model.Dica

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DicaAdapter
    private lateinit var searchView: SearchView
    private lateinit var databaseHelper: DicaDatabaseHelper

    private var dicas = mutableListOf<Dica>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        databaseHelper = DicaDatabaseHelper(this)

        recyclerView = findViewById(id.recyclerView)
        searchView = findViewById(id.searchView)

        dicas = databaseHelper.listarDicas().toMutableList()

        if (dicas.isEmpty()) {
            inicializarDicas()
            dicas = databaseHelper.listarDicas().toMutableList()
        }

        adapter = DicaAdapter(this, dicas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredDicas = dicas.filter { it.titulo.contains(newText ?: "", ignoreCase = true) }
                recyclerView.adapter = DicaAdapter(this@MainActivity, filteredDicas)
                return true
            }
        })
    }

    private fun inicializarDicas() {
        val dicasIniciais = listOf(
            Dica(
                "Use lâmpadas LED",
                "Economize energia utilizando lâmpadas LED.",
                "https://exemplo.com/led",
                "Lâmpadas LED consomem até 90% menos energia que as lâmpadas incandescentes."
            ),
            Dica(
                "Desligue aparelhos",
                "Aparelhos eletrônicos consomem energia mesmo em modo de espera.",
                "https://exemplo.com/standby",
                "Dispositivos em standby podem consumir até 10% da sua conta de energia."
            ),
            Dica(
                "Reutilize água",
                "Aproveite a água da chuva para tarefas externas.",
                "https://exemplo.com/agua",
                "Reutilizar água da chuva pode reduzir o consumo em até 30%."
            ),
            Dica(
                "Reduza o uso de plástico",
                "Prefira sacolas reutilizáveis em vez de sacolas plásticas.",
                "https://exemplo.com/plastico",
                "O plástico leva até 500 anos para se decompor na natureza."
            ),
            Dica(
                "Aproveite a luz natural",
                "Abra janelas e cortinas para iluminar sua casa durante o dia.",
                "https://exemplo.com/luz-natural",
                "Utilizar luz natural pode reduzir até 20% no consumo de energia."
            ),
            Dica(
                "Separe o lixo",
                "Pratique a coleta seletiva para facilitar a reciclagem.",
                "https://exemplo.com/reciclagem",
                "A reciclagem pode reduzir em até 70% o volume de lixo nos aterros."
            )
        )

        for (dica in dicasIniciais) {
            databaseHelper.inserirDica(dica)
        }
    }
}
