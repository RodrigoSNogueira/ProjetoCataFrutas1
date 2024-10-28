package projeto;

public class ElementoEstatico {
	private String tipo;
    private boolean temFruta;
    private Frutas fruta;
	private String[] tipoArvore = {"laranjeira", "abacateiro", "coqueiro", "PéDeAcerola", "PéDeAmora", "goiabeira"};
	
	public ElementoEstatico(String tipo, boolean temFruta) {
		this.tipo = tipo;
        this.temFruta = temFruta;
        if (isArvore(tipo)) {
            alocarFruta(tipo);
        }
	}
	
	private boolean isArvore(String tipo) {
		for (String arvore : tipoArvore) {
			if(arvore.equals(tipo)) {
				return true;
			}
		}
		return false;
	}

	public void alocarFruta(String tipoArvore) {
		int quantidadeFrutas = ConfiguraçãoGlobal.getQuantFrutasPorTipo(tipoArvore);
		if(quantidadeFrutas > 0) {
			switch (tipoArvore) {
			case "laranjeira":
                this.fruta = new Frutas("laranja");
                break;
            case "abacateiro":
                this.fruta = new Frutas("abacate");
                break;
            case "coqueiro":
                this.fruta = new Frutas("coco");
                break;
            case "PéDeAcerola":
                this.fruta = new Frutas("acerola");
                break;
            case "PéDeAmora":
                this.fruta = new Frutas("amora");
                break;
            case "goiabeira":
                this.fruta = new Frutas("goiaba");
                break;
            default:
                System.out.println("Essa árvore não existe");
                break;
			}
			this.temFruta = true;
		}
	
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isTemFruta() {
		return temFruta;
	}
	
	public void setTemFruta(boolean temFruta) {
        this.temFruta = temFruta; 
    }
	
	public Frutas getFruta() {
		return fruta;
	}
}
