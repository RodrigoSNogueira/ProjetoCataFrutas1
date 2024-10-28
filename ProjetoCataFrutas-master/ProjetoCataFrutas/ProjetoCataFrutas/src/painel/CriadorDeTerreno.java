package painel;

import javax.swing.*;

import projeto.ConfiguraçãoGlobal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CriadorDeTerreno extends JFrame {    
	private Map<String, Integer> quantidadeFrutas = new HashMap<>();
	private Map<String, Integer> quantidadeArvores = new HashMap<>();
	
    public CriadorDeTerreno() {
        setTitle("Criador de Terreno");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new Color(240, 240, 240)); // Fundo mais suave
        add(painelPrincipal);

        // Configurações
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("CONFIGURAÇÕES:"));
        configPanel.setBackground(new Color(255, 255, 255));
        painelPrincipal.add(configPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  
        gbc.fill = GridBagConstraints.BOTH;       
        gbc.weightx = 1.0;                        
        gbc.weighty = 0.2;                       
        // Adicionar campos de texto e rótulos
        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(new JLabel("Dimensão:"), gbc);
        gbc.gridx = 1;
        JTextField dimensaoFieldX = new JTextField(10);
        dimensaoFieldX.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        configPanel.add(dimensaoFieldX, gbc);

        gbc.gridx = 2; 
        configPanel.add(new JLabel("Capacidade da Mochila:"), gbc);
        gbc.gridx = 3;
        JTextField mochilaField = new JTextField(10);
        mochilaField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        configPanel.add(mochilaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        configPanel.add(new JLabel("Quantidade de Pedras:"), gbc);
        gbc.gridx = 1;
        JTextField pedrasField = new JTextField(10);
        pedrasField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        configPanel.add(pedrasField, gbc);

        gbc.gridx = 2;
        configPanel.add(new JLabel("Chance de bichadas (%):"), gbc);
        gbc.gridx = 3;
        JTextField bichadasField = new JTextField(10);
        bichadasField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        configPanel.add(bichadasField, gbc);

        JPanel frutasPanel = new JPanel(new GridBagLayout());
        frutasPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "FRUTAS E ÁRVORES: "));
        frutasPanel.setBackground(new Color(255, 255, 255));
        painelPrincipal.add(frutasPanel);
        GridBagConstraints gbcFrutas = new GridBagConstraints();
        gbcFrutas.insets = new Insets(5, 5, 5, 5);  
        gbcFrutas.fill = GridBagConstraints.BOTH;       
        gbcFrutas.weightx = 1.0;

        // Frutas e campos
        String[] frutas = {"maracuja", "laranja", "abacate", "coco", "acerola", "amora", "goiaba"};
        ArrayList<JTextField> arvoresFields = new ArrayList<>();
        ArrayList<JTextField> chaoFields = new ArrayList<>();

        for (int i = 0; i < frutas.length; i++) {
            // Label da fruta
            gbcFrutas.gridx = 0;
            gbcFrutas.gridy = i;
            frutasPanel.add(new JLabel(frutas[i] + ":"), gbcFrutas);
            
            // Campo de frutas no chão
            gbcFrutas.gridx = 1;
            JTextField chaoField = new JTextField(10);
            chaoField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            chaoFields.add(chaoField); 
            frutasPanel.add(chaoField, gbcFrutas);

            // Campo de árvores
            gbcFrutas.gridx = 2;
            JTextField arvoresField = new JTextField(10);
            arvoresField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            arvoresFields.add(arvoresField); 
            frutasPanel.add(arvoresField, gbcFrutas);
        }

        // Painel inferior com botões
        JPanel botoesPanel = new JPanel();
        botoesPanel.setBackground(new Color(240, 240, 240));
        painelPrincipal.add(botoesPanel);

        JButton importarButton = new JButton("Importar Terreno");
        estiloBotao(importarButton);
        botoesPanel.add(importarButton);
        
        importarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Abrir um arquivo existente
              JFileChooser fileChooser = new JFileChooser();
              fileChooser.setDialogTitle("Selecionar Configurações do Terreno");
              fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
              // Opção para escolher o arquivo
              int userSelection = fileChooser.showOpenDialog(null);
              if (userSelection == JFileChooser.APPROVE_OPTION) {
                 File fileToOpen = fileChooser.getSelectedFile();
                 Path caminhoArquivo = Paths.get(fileToOpen.getAbsolutePath());
                 // Lê as configurações do arquivo
                 try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo.toFile()))) {
                   String line;
                   int linhas = 0, quantPedras = 0 , chanceBichadas = 0 , capacidadeMochila ;
                    ArrayList<Integer> quantArvores = new ArrayList<>();
                    ArrayList<Integer> quantFrutas = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("dimensao")) {
                        	linhas = Integer.parseInt(line.split(" ")[1].trim());
                        	ConfiguraçãoGlobal.setTamanhoMapa(linhas);
                        	if (linhas < 3) {
                                JOptionPane.showMessageDialog(null, "A dimensão mínima permitida é 3. Por favor, insira um valor maior ou igual a 3.");
                                return; 
                            }
                        } 
                        else if (line.startsWith("pedras")) {
                        	quantPedras = Integer.parseInt(line.split(" ")[1].trim());
                        	ConfiguraçãoGlobal.setQuantPedras(quantPedras);
                        } 
                        else if (line.startsWith("bichadas")) {
                            chanceBichadas = Integer.parseInt(line.split(" ")[1].trim());
                            ConfiguraçãoGlobal.setChanceBichada(chanceBichadas);
                        } else if (line.startsWith("mochila")) {
                            capacidadeMochila = Integer.parseInt(line.split(" ")[1].trim());
                            ConfiguraçãoGlobal.setCapacidadeMochila(capacidadeMochila);
                        }
                        else {
                            String[] partes = line.split(" ");
                            String fruta = partes[0];
                            int arvores = Integer.parseInt(partes[1].trim());
                            int frutasNoChao = Integer.parseInt(partes[2].trim());
                            if (fruta.equals("maracuja")) { // Para o maracujá, o primeiro valor representa a quantidade total no jogo e o segundo valor quantas nascem no início
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
                            } 
                            else {
                                quantArvores.add(arvores);
                                quantidadeArvores.put(fruta, arvores);
                                ConfiguraçãoGlobal.setQuantArvoresPorTipo(fruta, arvores);
                                quantFrutas.add(frutasNoChao);
                                quantidadeFrutas.put(fruta, frutasNoChao);
                                ConfiguraçãoGlobal.setQuantFrutasPorTipo(fruta, frutasNoChao);
                            }
                        }
                   }              
                   dispose();
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
                  } 
                  catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + ex.getMessage());
                  } 
                  catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: formato inválido.");
                  }
        }}});

        JButton exportarButton = new JButton("Exportar Terreno");
        estiloBotao(exportarButton);
        botoesPanel.add(exportarButton);
        
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // Opção para salvar o arquivo
                fileChooser.setDialogTitle("Salvar Configurações do Terreno");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);     
                int userSelection = fileChooser.showSaveDialog(null); // Opção para escolher onde salvar o arquivo
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                  File fileToSave = fileChooser.getSelectedFile();
                  Path caminhoArquivo = Paths.get(fileToSave.getAbsolutePath());
                  try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo.toFile()))) {
                    writer.write("dimensao " + dimensaoFieldX.getText().trim());
                    writer.newLine();
                    writer.write("pedras " + pedrasField.getText().trim());
                    writer.newLine();
                    for (int i = 0; i < frutas.length; i++) {
                         if (frutas[i] == "maracuja") {
                        	 writer.write(frutas[i] + " " + chaoFields.get(i).getText().trim() + " " + arvoresFields.get(i).getText().trim()); 
                             writer.newLine();
                         }
                         else {
                        	 writer.write(frutas[i] + " " + arvoresFields.get(i).getText().trim() + " " + chaoFields.get(i).getText().trim());
                        	 writer.newLine();
                         }                    
                    }
                    writer.write("bichadas " + bichadasField.getText().trim());
                    writer.newLine();
                    writer.write("mochila " + mochilaField.getText().trim());
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Configurações exportadas com sucesso!");
                 } 
                   catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
                   }
               }
           }
        });

        JButton testarButton = new JButton("Testar Terreno");
        estiloBotao(testarButton);
        botoesPanel.add(testarButton);

        testarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    dispose();
                    JFrame window = new JFrame();
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                    window.setResizable(false);
                    window.setTitle("2D Adventure"); 
                    int linhas = Integer.parseInt(dimensaoFieldX.getText().trim());
                    if (linhas < 3) {
                        JOptionPane.showMessageDialog(null, "A dimensão mínima permitida é 3. Por favor, insira um valor maior ou igual a 3.");
                        return; 
                    }
                    int quantPedras = Integer.parseInt(pedrasField.getText().trim());
                    int[] quantArvores = new int[arvoresFields.size()];
                    int[] quantFrutas = new int[chaoFields.size()];
                    for (int i = 0; i < arvoresFields.size(); i++) {
                        if (!frutas[i].equals("maracujá")) {
                        	quantArvores[i] = Integer.parseInt(arvoresFields.get(i).getText().trim());
                        }
                    }
                    for (int i = 0; i < chaoFields.size(); i++) {
                        if (!frutas[i].equals("maracujá")) {
                        	quantFrutas[i] = Integer.parseInt(chaoFields.get(i).getText().trim());
                        }
                    }
                    // Aqui requer uma lógica um pouco diferente, pois o maracujá no menu tem um comportamente diferente
                    for (int i = 0; i < frutas.length; i++) {
                        if (frutas[i].equals("maracuja")) {
                            int totalMaracuja = Integer.parseInt(chaoFields.get(i).getText().trim());
                            int inicialNoChao = Integer.parseInt(arvoresFields.get(i).getText().trim());
                            if (totalMaracuja < 1) {
                                totalMaracuja = 1;
                            }
                            if (totalMaracuja < inicialNoChao) {
                            	JOptionPane.showMessageDialog(null, "Erro: A quantidade de maracujá que nasce no chão não pode ser maior que o total de maracujá");
                                return; 
                            }
                            quantArvores[i] = inicialNoChao; 
                            quantFrutas[i] = totalMaracuja;   
                            break;   
                        }
                    }
                                       
                    Painel painel = new Painel(linhas, quantPedras, quantArvores, quantFrutas);
                    window.add(painel);
                    window.pack();
                    window.setLocationRelativeTo(null);
                    window.setVisible(true);
                    painel.startGameThread();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage());
                }
            }
        });
    };
    
    
    private void estiloBotao(JButton botao) {
        botao.setBackground(new Color(100, 149, 237)); // Azul suave
        botao.setForeground(Color.WHITE); 
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espaço interno do botão
        botao.setFont(new Font("Arial", Font.BOLD, 14)); // Fonte moderna
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setContentAreaFilled(false);
        botao.setOpaque(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CriadorDeTerreno().setVisible(true);
            }
        });
    }
}