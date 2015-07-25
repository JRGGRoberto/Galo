package com.example.galo;


import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static char[] tab = new char[9];
	static char simbolocorrente;
	static char simboloInicio;
	static String player1 = "-";
	static String player2 = "-";
	boolean jogoterminado = false;
	static int jogadas = -1;
	static int TAMANHO = 3;
	static int win = 0;
	
	Basedados bd;
	

	public static void inicializacaoTabuleiro() {
		jogadas = 0;
		for (int pos = 0; pos < tab.length; pos++) {
			tab[pos] = ' ';
		}
		simbolocorrente = simboloInicio;
		troca();
	}

	public static boolean jogoAcabado() {
		int inicio = 0;
		int fim = 0;
		int intervalo = 0;

		for (int j = 0; j < (TAMANHO * 2 + 2); j++) {
			if (j < TAMANHO) {
				inicio = j * TAMANHO;
				intervalo = +1;
			} else if (j < TAMANHO * 2) {
				inicio = j - TAMANHO;
				intervalo = TAMANHO;
			} else if (j == TAMANHO * 2) {
				inicio = 0;
				intervalo = TAMANHO + 1;
			} else {
				inicio = TAMANHO - 1;
				intervalo = TAMANHO - 1;
			}
			fim = inicio + TAMANHO - 1;

			int ok = 0;

			if (tab[inicio] != ' ') {
				win++;

				for (int i = inicio; i < fim; i++) {
					if (tab[inicio] != tab[inicio + intervalo]) {
						break;
					} else {
						ok++;
						inicio += intervalo;
					}
				}
				if (ok == TAMANHO - 1) {
					return true;
				} else {
					ok = 0;
				}
			}
		}
		return false;
	}

	public static boolean fazerJogada(int x) {
		if (!jogoAcabado()) {
			if (jogadas == -1) {
				return false;
			}
			if (tab[x] != ' ') { // posicao ja preenchida
				return false;
			}
			tab[x] = simbolocorrente; // guardar a jogada que foi feita
			troca();
			jogadas++;
			return true; // devolver sucesso
		}
		return false;
	}

	public static void troca() {
		if (simbolocorrente == 'X') {
			simbolocorrente = 'O';
		} else {
			simbolocorrente = 'X';
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bd = new Basedados(this);

		final ImageView[] btns = { (ImageView) findViewById(R.id.b11),
				(ImageView) findViewById(R.id.b12),
				(ImageView) findViewById(R.id.b13),
				(ImageView) findViewById(R.id.b21),
				(ImageView) findViewById(R.id.b22),
				(ImageView) findViewById(R.id.b23),
				(ImageView) findViewById(R.id.b31),
				(ImageView) findViewById(R.id.b32),
				(ImageView) findViewById(R.id.b33) };

		final String whowin = getResources().getString(R.string.whowin);
		final String draw = getResources().getString(R.string.draw);

		final Toast toastFimJogo = Toast.makeText(getApplicationContext(),draw, Toast.LENGTH_SHORT);
		toastFimJogo.setGravity(Gravity.TOP, Gravity.CENTER_HORIZONTAL, 500);

		/**
		 * Botão inicio de Joggo "Começar"
		 */
		final ImageView binicio = (ImageView) findViewById(R.id.bcomecar);

		binicio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inicializacaoTabuleiro();
				
				for (int i = 0; i < tab.length; i++) {
					final int j = i;
					btns[j].setImageResource(R.drawable.vazio);
				}
			}
		});

		
		/**
		 * Captura jogadas
		 */
		for (int i = 0; i < tab.length; i++) {
			final int j = i;
			btns[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (fazerJogada(j)) {
						if (simbolocorrente == 'X') {
							btns[j].setImageResource(R.drawable.cruz);
						} else if (simbolocorrente == 'O') {
							btns[j].setImageResource(R.drawable.bola);
						}

						if (jogoAcabado()) {
							final Toast toastWin = Toast.makeText(getApplicationContext(), whowin + " " + simbolocorrente,Toast.LENGTH_LONG);
							toastWin.setGravity(Gravity.TOP,Gravity.CENTER_HORIZONTAL, 500);
							toastWin.show();
							bd.consultaEscrita("insert into jogos(tresultado, nome_imagem,tempo) values ('Vitoria de', '"+ simbolocorrente +"', " + (new Date()).getTime() +")");
						} else if (jogadas > 8) {
							toastFimJogo.show();
							bd.consultaEscrita("insert into jogos(tresultado, nome_imagem,tempo) values ('Empate ', 'vazio', " + (new Date()).getTime() +")");
						}
					} else {
						// toastjogInvalida.show();
					}
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_regras:
			intent.setClass(MainActivity.this, RegrasActivity.class);
			startActivity(intent);
			return true;

		case R.id.menu_regras1:
			intent.setClass(MainActivity.this, RegrasActivity.class);
			startActivity(intent);
			return true;

		case R.id.action_settings:
			intent.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
			
		case R.id.ultimosjogos:
			intent.setClass(MainActivity.this, UltimosJogosActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putCharArray("jogsalvo", tab);
		outState.putChar("simbolocorrente", simbolocorrente);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		tab = savedInstanceState.getCharArray("jogosalvo");
		final ImageView[] btnsalvo = new ImageView[9];
		for (int i = 0; i < tab.length; i++) {
			switch (tab[i]) {
			case 'X':
				btnsalvo[i].setImageResource(R.drawable.cruz);
				break;
			case 'O':
				btnsalvo[i].setImageResource(R.drawable.bola);
				break;
			}
		}
		simbolocorrente = savedInstanceState.getChar("simbolocorrente");
		String turnof = getResources().getString(R.string.vezdo);
		final Toast toastWin = Toast.makeText(getApplicationContext(), turnof+ simbolocorrente, Toast.LENGTH_LONG);
		toastWin.setGravity(Gravity.TOP, Gravity.CENTER_HORIZONTAL, 500);
		toastWin.show();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

		player1 = pref.getString("play1AAA", "defaultValue");
		player2 = pref.getString("play2AAA", "defaultValue");
		simboloInicio = pref.getString("oplista", "defaultValue").charAt(0);
	
	}

}
