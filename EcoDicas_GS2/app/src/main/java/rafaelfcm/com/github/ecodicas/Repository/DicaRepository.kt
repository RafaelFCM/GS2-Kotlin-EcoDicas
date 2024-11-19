package rafaelfcm.com.github.ecodicas.Repository

import android.content.ContentValues
import android.content.Context
import rafaelfcm.com.github.ecodicas.database.DicaDatabaseHelper
import rafaelfcm.com.github.ecodicas.model.Dica

class DicaRepository(context: Context) {
    private val dbHelper = DicaDatabaseHelper(context)

    fun inserirDica(dica: Dica): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DicaDatabaseHelper.COLUMN_TITULO, dica.titulo)
            put(DicaDatabaseHelper.COLUMN_DESCRICAO, dica.descricao)
            put(DicaDatabaseHelper.COLUMN_LINK, dica.link)
            put(DicaDatabaseHelper.COLUMN_CURIOSIDADE, dica.curiosidade)
        }
        return db.insert(DicaDatabaseHelper.TABLE_NAME, null, values)
    }

    fun listarDicas(): List<Dica> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DicaDatabaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val dicas = mutableListOf<Dica>()
        with(cursor) {
            while (moveToNext()) {
                val titulo = getString(getColumnIndexOrThrow(DicaDatabaseHelper.COLUMN_TITULO))
                val descricao = getString(getColumnIndexOrThrow(DicaDatabaseHelper.COLUMN_DESCRICAO))
                val link = getString(getColumnIndexOrThrow(DicaDatabaseHelper.COLUMN_LINK))
                val curiosidade = getString(getColumnIndexOrThrow(DicaDatabaseHelper.COLUMN_CURIOSIDADE))
                dicas.add(Dica(titulo, descricao, link, curiosidade))
            }
        }
        cursor.close()
        return dicas
    }
}
