package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa uma interface gráfica para testar a funcionalidade do teclado.
 * Ela exibe um layout de teclado na tela e destaca as teclas pressionadas.
 */
public class UIKeyboard {
    private JFrame frame;                // Janela principal da aplicação
    private JPanel keyboardPanel;        // Painel que contém o layout do teclado
    private Map<String, JLabel> keyLabels; // Mapa para armazenar as labels das teclas

    /**
     * Construtor da classe UIKeyboard.
     * Inicializa a interface gráfica e configura o layout do teclado.
     */
    public UIKeyboard() {
        frame = new JFrame("Teste de Teclado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 400);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela
        frame.setLayout(new BorderLayout());

        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridBagLayout());
        keyboardPanel.setBackground(new Color(32, 31, 58));  // Define a cor de fundo do painel do teclado

        keyLabels = new HashMap<>();
        createKeyboardLayout();  // Cria o layout do teclado

        frame.add(keyboardPanel, BorderLayout.CENTER);

        // Adiciona um listener para capturar eventos de teclado
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String key = getKeyText(e);  // Obtém o texto da tecla pressionada
                if (keyLabels.containsKey(key)) {
                    keyLabels.get(key).setBackground(Color.GREEN);  // Destaca a tecla pressionada
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String key = getKeyText(e);  // Obtém o texto da tecla liberada
                if (keyLabels.containsKey(key)) {
                    keyLabels.get(key).setBackground(Color.LIGHT_GRAY);  // Restaura a cor original da tecla
                }
            }
        });
    }

    /**
     * Cria o layout do teclado adicionando as teclas ao painel.
     */
    private void createKeyboardLayout() {
        String[][] keys = {
                {"ESC", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "SCRLK", "PAUSE"},
                {"`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "BACKSPACE", "INS", "HOME", "PGUP"},
                {"TAB", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\", "DEL", "END", "PGDN"},
                {"CAPS", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "ENTER"},
                {"SHIFT", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "UP"},
                {"CTRL", "ALT", "SPACE", "ALT", "WIN", "MENU", "LEFT", "DOWN", "RIGHT"}
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);  // Define margens entre os componentes

        // Adiciona cada tecla ao painel com seu respectivo layout
        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[row].length; col++) {
                String key = keys[row][col];
                JLabel keyLabel = createKeyLabel(key);  // Cria um JLabel para a tecla
                gbc.gridx = col;
                gbc.gridy = row;
                gbc.gridwidth = getGridWidth(key);  // Define a largura do grid da tecla
                keyLabels.put(key, keyLabel);  // Adiciona a label da tecla ao mapa
                keyboardPanel.add(keyLabel, gbc);  // Adiciona a label ao painel
            }
        }
    }

    /**
     * Cria um JLabel para uma tecla específica.
     *
     * @param key o texto da tecla.
     * @return um JLabel configurado para a tecla.
     */
    private JLabel createKeyLabel(String key) {
        JLabel label = new JLabel(key, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 50));  // Define o tamanho preferido do JLabel
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);  // Define a cor de fundo do JLabel
        label.setForeground(Color.BLACK);  // Define a cor do texto do JLabel
        label.setFont(new Font("Arial", Font.BOLD, 16));  // Define a fonte do texto
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Define a borda do JLabel
        return label;
    }

    /**
     * Retorna a largura do grid para uma tecla específica.
     * Por padrão, a largura é 1.
     *
     * @param key o texto da tecla.
     * @return a largura do grid.
     */
    private int getGridWidth(String key) {
        switch (key) {
            default:
                return 1;
        }
    }

    /**
     * Retorna o texto correspondente ao código da tecla pressionada.
     *
     * @param e o evento de teclado.
     * @return o texto da tecla.
     */
    private String getKeyText(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                return "ESC";
            case KeyEvent.VK_BACK_SPACE:
                return "BACKSPACE";
            case KeyEvent.VK_TAB:
                return "TAB";
            case KeyEvent.VK_CAPS_LOCK:
                return "CAPS";
            case KeyEvent.VK_ENTER:
                return "ENTER";
            case KeyEvent.VK_SHIFT:
                return "SHIFT";
            case KeyEvent.VK_CONTROL:
                return "CTRL";
            case KeyEvent.VK_ALT:
                return "ALT";
            case KeyEvent.VK_SPACE:
                return "SPACE";
            case KeyEvent.VK_WINDOWS:
                return "WIN";
            case KeyEvent.VK_CONTEXT_MENU:
                return "MENU";
            case KeyEvent.VK_UP:
                return "UP";
            case KeyEvent.VK_DOWN:
                return "DOWN";
            case KeyEvent.VK_LEFT:
                return "LEFT";
            case KeyEvent.VK_RIGHT:
                return "RIGHT";
            case KeyEvent.VK_INSERT:
                return "INS";
            case KeyEvent.VK_DELETE:
                return "DEL";
            case KeyEvent.VK_HOME:
                return "HOME";
            case KeyEvent.VK_END:
                return "END";
            case KeyEvent.VK_PAGE_UP:
                return "PGUP";
            case KeyEvent.VK_PAGE_DOWN:
                return "PGDN";
            case KeyEvent.VK_SCROLL_LOCK:
                return "SCRLK";
            case KeyEvent.VK_PAUSE:
                return "PAUSE";
            default:
                return KeyEvent.getKeyText(keyCode).toUpperCase();
        }
    }

    /**
     * Exibe a interface gráfica.
     */
    public void showUI() {
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
