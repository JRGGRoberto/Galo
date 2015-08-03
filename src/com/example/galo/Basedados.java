 package com.example.galo;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Basedados {
	private Conexao conexaobd;
	private SQLiteDatabase bd;

	private class Conexao extends SQLiteOpenHelper {

		public Conexao(Context context) {
			super(context, "dbgalo.db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			StringBuffer createTable = new StringBuffer("");
			createTable.append("create table jogos( ");
			createTable.append("_id INTEGER PRIMARY KEY, ");
			createTable.append("tresultado varchar(50), ");
			createTable.append("nome_imagem varchar(5), ");
			createTable.append("tempo integer, ");
			createTable.append("jogador1 varchar(50), ");
			createTable.append("jogador2 varchar(50), ");
			createTable.append("simpinicial varchar(1)");
			createTable.append(")");
			db.execSQL(createTable.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	public Basedados(Context contexto_inicial) {
		conexaobd = new Conexao(contexto_inicial);
		bd = conexaobd.getWritableDatabase();
	}

	public void fechar() {
		conexaobd.close();
	}

	public void consultaEscrita(String consulta) {
		bd.execSQL(consulta);
	}

	public Cursor consultaLeitura(String consulta) {
		return bd.rawQuery(consulta, null);
	}
}
