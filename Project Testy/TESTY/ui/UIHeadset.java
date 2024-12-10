package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Classe que representa uma interface gráfica para testar fones de ouvido e microfone.
 * A interface permite testar os lados esquerdo e direito dos fones de ouvido, ambos os lados juntos, e também realizar um teste de gravação com o microfone.
 */
public class UIHeadset {
    private JFrame frame;  // Janela principal da aplicação
    private JButton leftEarButton;  // Botão para testar o fone esquerdo
    private JButton rightEarButton;  // Botão para testar o fone direito
    private JButton bothEarsButton;  // Botão para testar ambos os fones
    private JButton micButton;  // Botão para testar o microfone
    private JLabel soundPlayingLabel;  // Label que indica quando o som está tocando
    private JLabel volumeLabel;  // Label que exibe o volume atual do microfone
    private JLabel timerLabel;  // Label que exibe o tempo restante do teste de microfone
    private int recordingDuration = 10;  // Duração do teste de microfone em segundos
    private Timer micTestTimer;  // Timer para controlar a duração do teste de microfone
    private TargetDataLine micLine;  // Linha de dados de entrada do microfone
    private boolean running;  // Flag para indicar se o teste de microfone está em andamento

    /**
     * Construtor da classe UIHeadset.
     * Inicializa a interface gráfica e configura os botões e labels.
     */
    public UIHeadset() {
        frame = new JFrame("Teste de Fones de Ouvido");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(32, 31, 58));  // Define a cor de fundo da janela

        // Inicializa os botões com os textos apropriados
        leftEarButton = UIUtils.createButton("Testar Fone Esquerdo");
        rightEarButton = UIUtils.createButton("Testar Fone Direito");
        bothEarsButton = UIUtils.createButton("Testar Ambos os Fones");
        micButton = UIUtils.createButton("Testar Microfone");

        // Adiciona ActionListeners aos botões para executar as ações correspondentes
        leftEarButton.addActionListener(e -> playSound("left"));
        rightEarButton.addActionListener(e -> playSound("right"));
        bothEarsButton.addActionListener(e -> playSound("both"));
        micButton.addActionListener(e -> testMicrophone());

        // Inicializa os labels com as configurações apropriadas
        soundPlayingLabel = new JLabel("Som tocando...", SwingConstants.CENTER);
        soundPlayingLabel.setForeground(Color.WHITE);
        soundPlayingLabel.setVisible(false);  // Inicialmente invisível

        volumeLabel = new JLabel("Volume: 0.0 dB", SwingConstants.CENTER);
        volumeLabel.setForeground(Color.WHITE);

        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);

        // Configura o painel de botões com layout GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(32, 31, 58));
        buttonPanel.add(leftEarButton);
        buttonPanel.add(rightEarButton);
        buttonPanel.add(bothEarsButton);
        buttonPanel.add(micButton);

        // Adiciona o painel de botões e labels à janela usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        frame.add(timerLabel, gbc);

        gbc.gridy = 2;
        frame.add(volumeLabel, gbc);

        gbc.gridy = 3;
        frame.add(soundPlayingLabel, gbc);
    }

    /**
     * Exibe a interface gráfica.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Reproduz um som para testar os fones de ouvido.
     *
     * @param side o lado do fone de ouvido a ser testado ("left", "right" ou "both").
     */
    private void playSound(String side) {
        disableButtons();  // Desabilita os botões enquanto o som está tocando
        soundPlayingLabel.setText("Som tocando...");
        soundPlayingLabel.setVisible(true);

        File soundFile;
        // Define o arquivo de som a ser reproduzido baseado no lado selecionado
        switch (side) {
            case "left":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            case "right":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            case "both":
                soundFile = new File("sounds/dogbass-undertale.wav");
                break;
            default:
                return;
        }

        try {
            // Abre o arquivo de som e inicializa o Clip para reprodução
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Ajusta o controle de panorama (panning) baseado no lado selecionado
            if ("left".equals(side)) {
                FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue(-1.0f);  // Som à esquerda
            } else if ("right".equals(side)) {
                FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue(1.0f);  // Som à direita
            }

            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    soundPlayingLabel.setVisible(false);
                    enableButtons();  // Reabilita os botões após o som
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            soundPlayingLabel.setVisible(false);
            enableButtons();  // Reabilita os botões em caso de erro
        }
    }

    /**
     * Realiza um teste de microfone.
     */
    private void testMicrophone() {
        disableButtons();  // Desabilita os botões durante o teste de microfone
        timerLabel.setText("Tempo restante: " + recordingDuration + " segundos");
        volumeLabel.setText("Volume: 0.0 dB");

        AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // Verifica se a linha de dados do microfone é suportada
        if (!AudioSystem.isLineSupported(info)) {
            JOptionPane.showMessageDialog(frame, "Microfone não suportado", "Erro", JOptionPane.ERROR_MESSAGE);
            enableButtons();
            return;
        }

        try {
            micLine = (TargetDataLine) AudioSystem.getLine(info);
            micLine.open(format);
            micLine.start();
            running = true;

            // SwingWorker para realizar a leitura do microfone em segundo plano
            new SwingWorker<Void, Double>() {
                @Override
                protected Void doInBackground() {
                    byte[] buffer = new byte[1024];
                    while (running) {
                        int bytesRead = micLine.read(buffer, 0, buffer.length);
                        double rms = calculateRMSLevel(buffer, bytesRead);
                        publish(rms);
                    }
                    return null;
                }

                @Override
                protected void process(java.util.List<Double> chunks) {
                    if (!chunks.isEmpty()) {
                        double latestRMS = chunks.get(chunks.size() - 1);
                        volumeLabel.setText(String.format("Volume: %.2f dB", latestRMS));
                    }
                }

                @Override
                protected void done() {
                    micLine.stop();
                    micLine.close();
                    enableButtons();
                    timerLabel.setText("Teste de microfone concluído.");
                }
            }.execute();

            // Timer para controlar a duração do teste de microfone
            micTestTimer = new Timer(1000, e -> {
                recordingDuration--;
                timerLabel.setText("Tempo restante: " + recordingDuration + " segundos");
                if (recordingDuration <= 0) {
                    running = false;
                    micTestTimer.stop();
                    recordingDuration = 10;  // Reseta a duração para o próximo teste
                }
            });
            micTestTimer.start();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            enableButtons();
        }
    }

    /**
     * Calcula o nível RMS (Root Mean Square) do áudio capturado.
     *
     * @param audioData os dados de áudio.
     * @param bytesRead o número de bytes lidos.
     * @return o nível RMS em decibéis.
     */
    private double calculateRMSLevel(byte[] audioData, int bytesRead) {
        long lSum = 0;
        for (int i = 0; i < bytesRead; i += 2) {
            short sample = ByteBuffer.wrap(audioData, i, 2).order(ByteOrder.BIG_ENDIAN).getShort();
            lSum += sample * sample;
        }
        double mean = lSum / (bytesRead / 2.0);
        double rms = Math.sqrt(mean);
        return 20 * Math.log10(rms);
    }

    /**
     * Desabilita os botões da interface.
     */
    private void disableButtons() {
        leftEarButton.setEnabled(false);
        rightEarButton.setEnabled(false);
        bothEarsButton.setEnabled(false);
        micButton.setEnabled(false);
    }

    /**
     * Habilita os botões da interface.
     */
    private void enableButtons() {
        leftEarButton.setEnabled(true);
        rightEarButton.setEnabled(true);
        bothEarsButton.setEnabled(true);
        micButton.setEnabled(true);
    }
}
