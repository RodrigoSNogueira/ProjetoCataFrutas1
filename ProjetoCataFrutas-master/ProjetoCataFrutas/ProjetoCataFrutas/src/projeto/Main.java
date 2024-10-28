package projeto;

import javax.swing.*;
import painel.CriadorDeTerreno;
import painel.Painel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new GridLayout(3, 1));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); 

        JLabel label = new JLabel("Escolha uma opção:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24)); 
        label.setForeground(new Color(25, 25, 112));
        frame.add(label);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); 

        JButton jogarButton = createStyledButton("Jogar");
        jogarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                iniciarJogo();   
            }
        });
        panel.add(jogarButton);

        JButton criadorButton = createStyledButton("Criador de Terreno");
        criadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                new CriadorDeTerreno().setVisible(true);;
       
            }
        });
        panel.add(criadorButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void iniciarJogo() {
        JFrame jogoFrame = new JFrame("Iniciar Jogo");
        jogoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogoFrame.setSize(400, 300);
        jogoFrame.setLayout(new GridLayout(6, 1));
        jogoFrame.setLocationRelativeTo(null); 
        jogoFrame.getContentPane().setBackground(new Color(240, 248, 255)); 

        // Labels e campos de texto para nomes dos jogadores
        JLabel label1 = new JLabel("Digite o nome do Jogador 1:", SwingConstants.CENTER);
        label1.setFont(new Font("Arial", Font.PLAIN, 18));
        label1.setForeground(new Color(25, 25, 112));
        JTextField nomeJogador1 = new JTextField();
        JLabel label2 = new JLabel("Digite o nome do Jogador 2:", SwingConstants.CENTER);
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        label2.setForeground(new Color(25, 25, 112));
        JTextField nomeJogador2 = new JTextField();

        // Botão para iniciar o jogo
        JButton iniciarButton = createStyledButton("Iniciar Jogo");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome1 = nomeJogador1.getText();
                String nome2 = nomeJogador2.getText();
                if (nome1.isEmpty() || nome2.isEmpty()) {
                    JOptionPane.showMessageDialog(jogoFrame, "Os nomes dos jogadores não podem estar vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return; // Não inicia o jogo se os nomes estiverem vazios
                }
                Floresta floresta = new Floresta();
                Jogador jogador1 = new Jogador(nome1, floresta, 0, 0);
                Jogador jogador2 = new Jogador(nome2, floresta, ConfiguraçãoGlobal.getTamanhoMapa() - 1, ConfiguraçãoGlobal.getTamanhoMapa() - 1);
                
             // Selecionar configurações do terreno
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Selecionar Configurações do Terreno");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                // Opção para escolher o arquivo
                int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    Path caminhoArquivo = Paths.get(fileToOpen.getAbsolutePath());
    
                    try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo.toFile()))) {
                        String line;
                        int linhas = 0, quantPedras = 0, chanceBichadas = 0, capacidadeMochila = 0;
                        ArrayList<Integer> quantArvores = new ArrayList<>();
                        ArrayList<Integer> quantFrutas = new ArrayList<>();
    
                        while ((line = reader.readLine()) != null) {
                            try {
                                // Processa a linha conforme o prefixo
                                if (line.startsWith("dimensao")) {
                                    linhas = Integer.parseInt(line.split(" ")[1].trim());
                                    ConfiguraçãoGlobal.setTamanhoMapa(linhas);
                                    if (linhas < 3) {
                                        JOptionPane.showMessageDialog(null, "A dimensão mínima permitida é 3. Por favor, insira um valor maior ou igual a 3.");
                                        return;
                                    }
                                } else if (line.startsWith("pedras")) {
                                    quantPedras = Integer.parseInt(line.split(" ")[1].trim());
                                    ConfiguraçãoGlobal.setQuantPedras(quantPedras);
                                } else if (line.startsWith("bichadas")) {
                                    chanceBichadas = Integer.parseInt(line.split(" ")[1].trim());
                                    ConfiguraçãoGlobal.setChanceBichada(chanceBichadas);
                                } else if (line.startsWith("mochila")) {
                                    capacidadeMochila = Integer.parseInt(line.split(" ")[1].trim());
                                    ConfiguraçãoGlobal.setCapacidadeMochila(capacidadeMochila);
                                } else {
                                    String[] partes = line.split(" ");
                                    String fruta = partes[0];
                                    int arvores = Integer.parseInt(partes[1].trim());
                                    int frutasNoChao = Integer.parseInt(partes[2].trim());
    
                                    if (fruta.equals("maracuja")) {
                                        quantArvores.add(frutasNoChao);
                                        ConfiguraçãoGlobal.setQuantidadeFrutasOuro(frutasNoChao);
                                        if (arvores < 1) {
                                            arvores = 1;
                                        }
                                        quantFrutas.add(arvores);
                                        if (arvores < frutasNoChao) {
                                            JOptionPane.showMessageDialog(null, "Erro: A quantidade total de maracujás não pode ser menor que a quantidade que nasce no chão.");
                                            return;
                                        }
                                    } else {
                                        quantArvores.add(arvores);
                                        Map<String, Integer> quantidadeFrutas = new HashMap<>();
                                        Map<String, Integer> quantidadeArvores = new HashMap<>();
    
                                        quantidadeArvores.put(fruta, arvores);
                                        ConfiguraçãoGlobal.setQuantArvoresPorTipo(fruta, arvores);
                                        quantFrutas.add(frutasNoChao);
                                        quantidadeFrutas.put(fruta, frutasNoChao);
                                        ConfiguraçãoGlobal.setQuantFrutasPorTipo(fruta, frutasNoChao);
                                    }
                                }
                            } catch (NumberFormatException e1) {
                                JOptionPane.showMessageDialog(null, "Erro ao processar a linha: " + line + "\nFormato numérico inválido.");
                            } catch (ArrayIndexOutOfBoundsException e2) {
                                JOptionPane.showMessageDialog(null, "Erro ao processar a linha: " + line + "\nLinha mal formatada.");
                            }
                        }
    
                        // Após carregar as configurações, instanciar o Painel do Jogo
                   JFrame window = new JFrame();
                   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   window.setResizable(false);
                   window.setTitle("2D Adventure");
                    
                   int[] quantArvoresArray = quantArvores.stream().mapToInt(i -> i).toArray();
                   int[] quantFrutasArray = quantFrutas.stream().mapToInt(i -> i).toArray();

                   Painel painel = new Painel(linhas, quantPedras, quantArvoresArray, quantFrutasArray);
                   window.add(painel);
                   window.pack();
                   window.setLocationRelativeTo(null);
                   window.setVisible(true);
                   painel.startGameThread();
                   Jogo jogo = new Jogo(jogador1, jogador2, painel);
                   jogo.iniciarRodada();  
                    } catch (IOException e3) {
                        JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e3.getMessage());
                    }
                }
                jogoFrame.dispose(); // Fecha a janela de configuração dos jogadores     
            }
        });

        jogoFrame.add(label1);
        jogoFrame.add(nomeJogador1);
        jogoFrame.add(label2);
        jogoFrame.add(nomeJogador2);
        jogoFrame.add(iniciarButton);
        jogoFrame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180)); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); 
            }
        });
        return button;
    }
}
