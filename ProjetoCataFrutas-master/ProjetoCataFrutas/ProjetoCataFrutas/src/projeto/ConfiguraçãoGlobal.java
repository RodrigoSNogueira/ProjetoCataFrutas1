package projeto;

import java.util.HashMap;
import java.util.Map;

import painel.CriadorDeTerreno;

public class ConfiguraçãoGlobal {
	private static int chanceBichada;
	private static int capacidadeMochila;
	private static int QuantidadeFrutasOuro;
	private static int tamanhoMapa;
	private static int quantPedras;
	private static Map<String, Integer> quantArvoresPorTipo = new HashMap<>();
    private static Map<String, Integer> quantFrutasPorTipo = new HashMap<>();

    
    public static Map<String, Integer> getQuantArvores() {
        return quantArvoresPorTipo;
    }

    public static Map<String, Integer> getQuantFrutas() {
        return quantFrutasPorTipo;
    }

    public static void setQuantArvoresPorTipo(String tipo, int quantidade) {
        quantArvoresPorTipo.put(tipo, quantidade);
    }

    public static int getQuantArvoresPorTipo(String tipo) {
        return quantArvoresPorTipo.getOrDefault(tipo, 0);
    }

    public static void setQuantFrutasPorTipo(String tipo, int quantidade) {
        quantFrutasPorTipo.put(tipo, quantidade);
    }

    public static int getQuantFrutasPorTipo(String tipo) {
        return quantFrutasPorTipo.getOrDefault(tipo, 0);
    }

	public static int getQuantidadeFrutasOuro() {
		return QuantidadeFrutasOuro;
	}

	public static void setQuantidadeFrutasOuro(int quantidadeFrutasOuro) {
		QuantidadeFrutasOuro = quantidadeFrutasOuro;
	}

	public static int getTamanhoMapa() {
		return tamanhoMapa;
	}

	public static void setTamanhoMapa(int tamanhoMapa) {
		ConfiguraçãoGlobal.tamanhoMapa = tamanhoMapa;
	}

	public static void setChanceBichada(int chance) {
	      chanceBichada = chance;
	 }

	 public static int getChanceBichada() {
	      return chanceBichada;
	 }

	public static int getCapacidadeMochila() {
		return capacidadeMochila;
	}

	public static void setCapacidadeMochila(int capacidadeMochila) {
		ConfiguraçãoGlobal.capacidadeMochila = capacidadeMochila;
	}

	public static int getQuantPedras() {
		return quantPedras;
	}

	public static void setQuantPedras(int quantPedras) {
		ConfiguraçãoGlobal.quantPedras = quantPedras;
	}
	 
}
