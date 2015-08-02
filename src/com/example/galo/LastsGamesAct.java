package com.example.galo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class LastsGamesAct extends Activity {
	
	private Cursor c;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lasts_games);
	}
	
	public void onStart(){
		super.onStart();
		
Basedados db = new Basedados(this);
		/*
		db.consultaEscrita("delete from jogos");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'X', 1438022703882)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('empate  de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		db.consultaEscrita("insert into jogos(tresultado, nome_imagem, tempo) values ('Vitoria de', 'O', 1438022703899)");
		*/
		c = db.consultaLeitura("select * from jogos order by 1 desc");
		
		Log.d("teste: ",c.getColumnCount()+"");
		Log.d("teste: ",c.getCount()+"");
		
		ArrayList<HashMap<String,String>> ListaGames = new ArrayList<HashMap<String,String>>();
		if(c.moveToFirst())
			do {
				HashMap<String, String> hisjogo = new HashMap<String,String>();
				
				hisjogo.put("tresultado", c.getString(c.getColumnIndex("tresultado")));
				
				if(c.getString(c.getColumnIndex("nome_imagem")).equals("X"))
					hisjogo.put("nome_imagem", Integer.toString(R.drawable.cruz));
				else if(c.getString(c.getColumnIndex("nome_imagem")).equals("O"))
					hisjogo.put("nome_imagem", Integer.toString(R.drawable.bola));
				else 
					hisjogo.put("nome_imagem", Integer.toString(R.drawable.empate));
				
				long tempmil = c.getLong(c.getColumnIndex("tempo"));
				Date tempdt = new Date(tempmil);
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy hh:mm");
				hisjogo.put("tempo", formato.format(tempdt));
				
				hisjogo.put("player1", c.getString(c.getColumnIndex("jogador1")));
				hisjogo.put("player2", c.getString(c.getColumnIndex("jogador2")));
				
				hisjogo.put("inicio", c.getString(c.getColumnIndex("simpinicial")));
				
	            ListaGames.add(hisjogo);
	            
			}while (c.moveToNext());
		c.close();

		String[] from = {"tresultado", "nome_imagem", "tempo", "player1", "player2", "inicio"};
		int[] to ={R.id.textoresultado, R.id.imagemresultado, R.id.textodata, R.id.jogadora, R.id.jogadorb, R.id.charinicio};
        int layout = R.layout.hist_game;
		
		
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), ListaGames, layout, from, to);
        
        ListView lv = ( ListView ) findViewById(R.id.listajogos);
 
        lv.setAdapter(adapter);
		
		
		///////////
		/*
		 * 
		 @android:id/list
		 
		 android:id="@+id/listagames"
		 * 
		setListAdapter(new CursorAdapter(ultcontext, c, 0){
			
			@Override
			public void bindView(View view, Context context, Cursor cur) {
				
				Log.d("teste: ",cur.getCount()+"");

				TextView txres = (TextView) findViewById(R.id.textoresultado);
				txres.setText(cur.getString(cur.getColumnIndex("tresultado")));
				
				
				String tximg = cur.getString(cur.getColumnIndex("nome_imagem"));
				
				ImageView imag = (ImageView) findViewById(R.id.imagemresultado);
				
				if(tximg.equals("X"))
					imag.setImageResource(R.drawable.cruz);
				else if(tximg.equals("O"))
					imag.setImageResource(R.drawable.bola);
				else imag.setImageResource(R.drawable.vazio);
				
				TextView txtemp = (TextView) findViewById(R.id.textodata);
				long tempmil = cur.getLong(cur.getColumnIndex("tempo"));
				
				Date tempdt = new Date(tempmil);
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyy hh:mm");
				txtemp.setText(formato.format(tempdt));
				
			}

			@Override
			public View newView(Context context, Cursor cur, ViewGroup parent) {
				LayoutInflater infla = LayoutInflater.from(context);
				View v = infla.inflate(R.layout.hist_game, parent, false);	
				bindView(v, context, cur);
				return v;
			}
		});
		*/
	}
	
	@Override
	protected void onDestroy(){
		c.close();
		super.onDestroy();
	}
}
