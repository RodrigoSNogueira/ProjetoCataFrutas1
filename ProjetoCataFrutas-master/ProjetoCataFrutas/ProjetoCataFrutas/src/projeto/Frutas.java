package projeto;

import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class Frutas extends ElementoDinamico{
	private String nome;
	private Random random;
	private ImageIcon sprites;
	
	public Frutas(String nome) {
		this.nome = nome;
		this.random = new Random();
		this.sprites = carregarImagem(nome);
	}

	private ImageIcon carregarImagem(String nome) {
		String diretorioImagem = "/sprites/" + nome + ".png";
		return new ImageIcon(getClass().getResource(diretorioImagem));
	}

	public ImageIcon getSprites(){
		return this.sprites;
	}
	public boolean isBichada() {
        double chance = random.nextDouble() * 100;
        return chance <= ConfiguraçãoGlobal.getChanceBichada();
    }
	
	public void efeitoFruta(String fruta) {
		switch (nome) {
		case "laranja": // Laranja, anula o efeito de uma bichada		
			break;
		case "abacate": // Abacate, dobra o número de pontos de movimento
			break;
		case "coco": // Coco, dobra a força do jogador
			break; 
		}
	}
	
	@Override
	public void mover(int novaX, int novaY) {
		
	}
	
	 public String getNome() {
		 return nome;
    }
}
