package com.example.galo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static char[][] quadrado;
	static char simbolocorrente = 'X';
	boolean jogoterminado = false;
	static int jogadas = -1;
	static int mVitoria;
	char[] jogosalvo = new char[9];

	public static void inicializacaoTabuleiro() {
		quadrado = new char[3][3];
		jogadas = 0;
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				quadrado[linha][coluna] = ' ';
			}
		}
	}

	public static boolean jogoAcabado() {

		for (int linha = 0; linha < 3; linha++) { // ganhou linha ====
			mVitoria++;
			if ((quadrado[linha][0] == quadrado[linha][1])
					&& (quadrado[linha][2] == quadrado[linha][1])
					&& (quadrado[linha][0] != ' ')) {
				return true;
			}
		}

		for (int coluna = 0; coluna < 3; coluna++) { // ganhou coluna ||||
			mVitoria++;
			if ((quadrado[0][coluna] == quadrado[1][coluna])
					&& (quadrado[2][coluna] == quadrado[1][coluna])
					&& (quadrado[0][coluna] != ' ')) {
				return true;
			}
		}

		// verifica se ganhou na diagonal \\ melhorar isto
		mVitoria++;
		if ((quadrado[0][0] == quadrado[1][1])
				&& (quadrado[2][2] == quadrado[1][1])
				&& (quadrado[2][2] != ' ')) {
			return true;
		}

		// verifica se ganhou na diagonal // melhorar isto
		mVitoria++;
		if ((quadrado[0][2] == quadrado[1][1])
				&& (quadrado[2][0] == quadrado[1][1])
				&& (quadrado[2][0] != ' ')) {
			return true;
		}
		return false;
	}

	public static boolean fazerJogada(int x, int y) {
		if (jogadas == -1) {
			return false;
		}
		if (quadrado[x][y] != ' ') { // posicao ja preenchida
			return false;
		}
		quadrado[x][y] = simbolocorrente; // guardar a jogada que foi feita
		troca();
		jogadas++;
		return true; // devolver sucesso
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

		final ImageView[][] btns = new ImageView[3][3];
		btns[0][0] = (ImageView) findViewById(R.id.b11);
		btns[0][1] = (ImageView) findViewById(R.id.b12);
		btns[0][2] = (ImageView) findViewById(R.id.b13);
		btns[1][0] = (ImageView) findViewById(R.id.b21);
		btns[1][1] = (ImageView) findViewById(R.id.b22);
		btns[1][2] = (ImageView) findViewById(R.id.b23);
		btns[2][0] = (ImageView) findViewById(R.id.b31);
		btns[2][1] = (ImageView) findViewById(R.id.b32);
		btns[2][2] = (ImageView) findViewById(R.id.b33);

		final String whowin = getResources().getString(R.string.whowin);
		final String draw = getResources().getString(R.string.draw);

		final Toast toastFimJogo = Toast.makeText(getApplicationContext(),
				draw, Toast.LENGTH_SHORT);
		toastFimJogo.setGravity(Gravity.TOP, Gravity.CENTER_HORIZONTAL, 500);

		/**
		 * Botão inicio de Joggo "Começar"
		 */
		final ImageView binicio = (ImageView) findViewById(R.id.bcomecar);

		binicio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// binicio.setImageAlpha(50);
				inicializacaoTabuleiro();
				for (int i = 0; i < 3; i++) {
					final int j = i;
					for (int x = 0; x < 3; x++) {
						final int y = x;
						btns[j][y].setImageResource(R.drawable.vazio);
					}
				}
			}
		});

		/**
		 * Botões X 0
		 */
		for (int i = 0; i < 3; i++) {
			final int j = i;
			for (int x = 0; x < 3; x++) {
				final int y = x;
				btns[i][y].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (fazerJogada(j, y)) {
							if (simbolocorrente == 'X') {
								btns[j][y].setImageResource(R.drawable.cruz);
							} else if (simbolocorrente == 'O') {
								btns[j][y].setImageResource(R.drawable.bola);
							}

							if (jogoAcabado()) {
								final Toast toastWin = Toast.makeText(
										getApplicationContext(), whowin + " "
												+ simbolocorrente,
										Toast.LENGTH_LONG);
								toastWin.setGravity(Gravity.TOP,
										Gravity.CENTER_HORIZONTAL, 500);
								toastWin.show();
							} else if (jogadas > 8) {
								toastFimJogo.show();
							}
						} else {
							// toastjogInvalida.show();
						}
					}
				});
			}
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
		/*
		 * int id = item.getItemId(); if (id == R.id.menu_regras) { return true;
		 * }
		 */
		switch (item.getItemId()) {
		case R.id.menu_regras:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, RegrasActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int r = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				jogosalvo[r] = quadrado[i][j];
				r++;
			}
		}
		outState.putCharArray("jogsalvo", jogosalvo);
		outState.putChar("simbolocorrente", simbolocorrente);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// jogosalvo = savedInstanceState.getCharArray("jogosalvo");
		// simbolocorrente = savedInstanceState.getChar("simbolocorrente");
	}

}
