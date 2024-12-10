package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe que representa uma interface gráfica para testar o funcionamento das caixas de som.
 * A interface permite testar a reprodução de som através de um botão.
 */
public class UISpeaker {
    private JFrame frame;  // Janela principal da aplicação
    private JButton speakerButton;  // Botão para iniciar o teste da caixa de som
    private JLabel soundPlayingLabel;  // Label que exibe o estado da reprodução do som

    /**
     * Construtor da classe UISpeaker.
     * Inicializa a interface gráfica e configura o botão de teste de som.
     */
    public UISpeaker() {
        frame = new JFrame("Teste de Caixa de Som");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));

        speakerButton = UIUtils.createButton("Testar Caixa de Som");
        speakerButton.addActionListener(e -> playSound());

        soundPlayingLabel = new JLabel("Som tocando...", SwingConstants.CENTER);
        soundPlayingLabel.setForeground(Color.WHITE);
        soundPlayingLabel.setVisible(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(speakerButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(soundPlayingLabel, gbc);
    }

    /**
     * Exibe a interface gráfica.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Reproduz o som para testar as caixas de som.
     * O som é desativado enquanto está sendo reproduzido e reativado ao terminar.
     */
    private void playSound() {
        disableButtons();
        soundPlayingLabel.setText("Som tocando...");
        soundPlayingLabel.setVisible(true);

        File soundFile = new File("sounds/dogbass-undertale.wav");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    soundPlayingLabel.setVisible(false);
                    enableButtons();  // Reabilitar os botões após o som
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            soundPlayingLabel.setVisible(false);
            enableButtons();  // Reabilitar os botões em caso de erro
        }
    }

    /**
     * Desabilita o botão de teste de som.
     */
    private void disableButtons() {
        speakerButton.setEnabled(false);
    }

    /**
     * Habilita o botão de teste de som.
     */
    private void enableButtons() {
        speakerButton.setEnabled(true);
    }
}
