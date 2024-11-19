package rafaelfcm.com.github.ecodicas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import rafaelfcm.com.github.ecodicas.model.Dica

class DicaDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "dicas.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "dicas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DESCRICAO = "descricao"
        const val COLUMN_LINK = "link"
        const val COLUMN_CURIOSIDADE = "curiosidade"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT,
                $COLUMN_DESCRICAO TEXT,
                $COLUMN_LINK TEXT,
                $COLUMN_CURIOSIDADE TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserirDica(dica: Dica): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITULO, dica.titulo)
        values.put(COLUMN_DESCRICAO, dica.descricao)
        values.put(COLUMN_LINK, dica.link)
        values.put(COLUMN_CURIOSIDADE, dica.curiosidade)

        return db.insert(TABLE_NAME, null, values)
    }

    fun listarDicas(): List<Dica> {
        val dicas = mutableListOf<Dica>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                val link = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK))
                val curiosidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURIOSIDADE))

                dicas.add(Dica(titulo, descricao, link, curiosidade))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dicas
    }
}
