Jogo do Galo (Pt) | Jogo da Velha (Br RJ)
=========================================

Exemplo do Jogo da Velha para Android.

### Testes no equipamentos
* Samsung Galaxy - S2 4.1.1 (a)
* Motorola Moto X - 4.2.2 (a)
* Sony Xperia Z - 4.3 (a)
* Asus ME173X - 4.2.2 

(a) Emulados via VM [Genymotion](https://www.genymotion.com)

https://pt.linkedin.com/in/jrobertogoes

jrggroberto@gmail.com


### A melhorar

1. onSaveInstanceState  - a validar
2. onRestoreInstanceState - implementar
3. Desenvolver outros modelos para hppi, ldpi, mdpi, xhdpi e xxhdpi
4. De inicio implementar uma função randomize para colocar para jogar contra a máquina, posteriormente um algoritmo Minimax

### Screenshot

![screen shot](http://4.bp.blogspot.com/--IeblpfeTQo/VZ2iQx2qQaI/AAAAAAAACGw/zcRjE6Ghteo/s640/Captura%2Bde%2BTela%2B2015-07-08%2Ba%25CC%2580s%2B22.50.05.png)


public class Arrays {
	
	static String j[] = {"4x", "2o", "0x", "8o", "6x", "5o", "1_ ", "3_", "7_"};
	public static void main(String[] args) {
		String h[] = new String[9];
		for(int x = 0; x < 9; x++)
			h[x] = "_";

		for(int y = 0; y < 9; y ++){
			for(int x = 0; x < y+1; x ++){
				int pos = Integer.parseInt(j[x].substring(0,1));
				String cont = j[x].substring(1,2);
				h[pos] = cont;
				if(h[pos]== "_") break;
			}

			for(int x = 0; x < 9; x ++){
				System.out.print(h[x]);
				if((x+1)% 3 == 0) System.out.println();
			}
			System.out.println();
		}
	}
}
