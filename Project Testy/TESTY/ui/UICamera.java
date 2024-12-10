package ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Classe que representa uma interface gráfica para testar o funcionamento da webcam.
 * A interface permite abrir o aplicativo de câmera do Windows através de um botão.
 */
public class UICamera {
    private JFrame frame;  // Janela principal da aplicação
    private JButton webcamButton;  // Botão para iniciar o teste da webcam
    private JLabel webcamStatusLabel;  // Label que exibe o estado do teste da webcam

    /**
     * Construtor da classe UICamera.
     * Inicializa a interface gráfica e configura o botão de teste da webcam.
     */
    public UICamera() {
        frame = new JFrame("Teste de Webcam");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));

        webcamButton = UIUtils.createButton("Testar Webcam");
        webcamButton.addActionListener(e -> testWebcam());

        webcamStatusLabel = new JLabel("Clique no botão para testar a webcam.", SwingConstants.CENTER);
        webcamStatusLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(webcamButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(webcamStatusLabel, gbc);
    }

    /**
     * Exibe a interface gráfica.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Testa a webcam abrindo o aplicativo de câmera do Windows.
     * O botão é desativado durante a execução do teste.
     */
    private void testWebcam() {
        disableButtons();

        // Tentar abrir o aplicativo de Câmera do Windows
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start microsoft.windows.camera:");
            processBuilder.start();
            webcamStatusLabel.setText("Aplicativo de câmera do Windows aberto.");
        } catch (IOException e) {
            e.printStackTrace();
            webcamStatusLabel.setText("Erro ao abrir o aplicativo de câmera do Windows.");
        }

        enableButtons();
    }

    /**
     * Desabilita o botão de teste da webcam.
     */
    private void disableButtons() {
        webcamButton.setEnabled(false);
    }

    /**
     * Habilita o botão de teste da webcam.
     */
    private void enableButtons() {
        webcamButton.setEnabled(true);
    }
}
