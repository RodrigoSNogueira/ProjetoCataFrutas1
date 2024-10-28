package projeto;

import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import painel.Painel;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private int turnoAtual; // 0 = jogador1, 1 = jogador2
    private int frutasOuroNecessarias = (int) Math.ceilDiv(ConfiguraçãoGlobal.getQuantidadeFrutasOuro(),2);
    private Floresta floresta;
    private Painel painel;  // Painel onde o jogo será renderizado
    private boolean jogoAtivo;  // Controle se o jogo está ativo
    private int pontos;

    public Jogo(Jogador jogador1, Jogador jogador2, Painel painel) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.turnoAtual = 0; 
        this.floresta = new Floresta();
        this.painel = painel;
        this.jogoAtivo = true;
    }

    public void iniciarRodada() {
    	Thread gameThread = new Thread(() -> {
            while (jogoAtivo) {
                logicaJogo();
                // Redesenha o painel
                painel.repaint();
                try {
                    Thread.sleep(100); // Atualiza a cada 100ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();  // Inicia a thread do jogo
    }
    
    public void rolarDados() {
		Random random = new Random();
		int dado1 = random.nextInt(6) + 1;
		int dado2 = random.nextInt(6) + 1;
		this.pontos = dado1 + dado2;
	}
    
    public void logicaJogo() {
    	while (true) {
        Jogador jogadorAtual = (turnoAtual == 0) ? jogador1 : jogador2;
        rolarDados();
        jogadorAtual.setPontosMov(pontos);
        painel.setMensagem("Vez do competidor: " + jogadorAtual.getNome());
        if (jogadorAtual.verificarVitoria(frutasOuroNecessarias)) {
        	JOptionPane.showMessageDialog(null, jogadorAtual.getNome() + " venceu o jogo!"); 
        }
        Scanner scanner = new Scanner(System.in);
        char movimento = scanner.next().charAt(0);
        int novaX = jogadorAtual.getX();
        int novaY = jogadorAtual.getY();
        
        switch (movimento) {
        case 'w': 
            novaX--;
            break;
        case 's': 
            novaX++;
            break;
        case 'a': 
            novaY--;
            break;
        case 'd': 
            novaY++;
            break;
    }
        jogadorAtual.mover(novaX, novaY);
        Jogador outroJogador = (jogadorAtual == jogador1) ? jogador2 : jogador1;
        if (jogadorAtual.estaAdjacente(outroJogador)) {
            jogadorAtual.encontrarJogador(outroJogador); 
        }
        ElementoEstatico elemento = floresta.getElemento(novaX, novaY);
        if (elemento.isTemFruta()) {
            Frutas frutaEncontrada = new Frutas("Fruta"); 
            jogadorAtual.catarFruta(frutaEncontrada); 
        }
        if (jogadorAtual.verificarVitoria(frutasOuroNecessarias)) {
        	JOptionPane.showMessageDialog(null,jogadorAtual.getNome() + " venceu o jogo!");
            break;
        }
        jogadorAtual.finalizarJogada();
        turnoAtual = (turnoAtual == 0) ? 1 : 0; // Passa para o próximo turno (próximo jogador)
     }
    }

    public void encerrarRodada() {
    	painel.setMensagem("Fim da rodada do jogador atual.");
        iniciarRodada(); 
    }
}