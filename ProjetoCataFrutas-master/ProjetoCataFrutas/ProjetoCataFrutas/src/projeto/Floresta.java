package projeto;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Floresta {
	private ElementoEstatico matriz[][];
	private int tamanho = ConfiguraçãoGlobal.getTamanhoMapa();
	private Random random = new Random();
	
	public Floresta() {
		this.matriz = new ElementoEstatico[tamanho][tamanho];
		inicializarFloresta();
	}
	
	public void inicializarFloresta() {
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				matriz[i][j] = new ElementoEstatico("grama", false); // Grana sen fruta
			}
		}
		alocarElementos();
	}
	
	public void alocarElementos() {
		 for (int i = 0; i < ConfiguraçãoGlobal.getQuantPedras(); i++) {
	         int x = random.nextInt(tamanho);
	         int y = random.nextInt(tamanho);
	         matriz[x][y] = new ElementoEstatico("pedra", false);
	     }
		 for (Map.Entry<String, Integer> entry : ConfiguraçãoGlobal.getQuantArvores().entrySet()) {
			 String tipoArvore = entry.getKey();
		     int quantidade = entry.getValue();
	         for (int i = 0; i < quantidade; i++) {
	             alocarElementoAleatorio(tipoArvore, true);
	         }
	        }
		 for (Map.Entry<String, Integer> entry : ConfiguraçãoGlobal.getQuantFrutas().entrySet()) {
		      String tipoFruta = entry.getKey();
		      int quantidade = entry.getValue();
	          for (int i = 0; i < quantidade; i++) {
	              alocarElementoAleatorio(tipoFruta, true);
	          }
	     }
	 }
	
	 private void alocarElementoAleatorio(String tipo, boolean temFruta) {
	      int x, y;
          do {
	          x = random.nextInt(tamanho);
	          y = random.nextInt(tamanho);
	      } while (!posicaoValida(x, y)); 
	        matriz[x][y] = new ElementoEstatico(tipo, temFruta);
	    }
	
	 public ElementoEstatico getElemento(int x, int y) {
	        if (posicaoValida(x, y)) {
	            return matriz[x][y];
	        }
	        return null; 
	    }

	    public boolean posicaoValida(int x, int y) {
	        return x >= 0 && x < tamanho && y >= 0 && y < tamanho;
	    }

	    public int getTamanho() {
	        return tamanho;
	    }
}
