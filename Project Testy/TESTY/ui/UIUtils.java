package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Classe utilitária para criar componentes de interface gráfica com estilo padronizado.
 * Fornece métodos para criar painéis, labels e botões com estilos específicos.
 */
public class UIUtils {

    /**
     * Cria um painel de informações com layout vertical, fundo escuro e bordas internas.
     *
     * @return Um JPanel configurado para exibir informações.
     */
    public static JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(12, 7, 36));
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    /**
     * Cria um JLabel estilizado para exibir texto informativo.
     *
     * @param text O texto a ser exibido no label.
     * @return Um JLabel configurado para exibir informações.
     */
    public static JLabel createInfoLabel(String text) {
        JLabel label = new JLabel("<html><div style='color: white;'>" + text + "</div></html>");
        label.setForeground(new Color(178, 102, 255));
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    /**
     * Cria um painel de botões com layout vertical e fundo escuro.
     *
     * @return Um JPanel configurado para conter botões.
     */
    public static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(32, 31, 58));
        panel.setPreferredSize(new Dimension(200, 0));
        return panel;
    }

    /**
     * Cria um JButton estilizado com texto, cores e fonte específicas.
     *
     * @param label O texto do botão.
     * @return Um JButton configurado com estilo padronizado.
     */
    public static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(new Color(142, 68, 173));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        return button;
    }

    /**
     * Cria um JLabel para exibir um ícone, com bordas internas e alinhamento central.
     *
     * @param icon O ícone a ser exibido no label.
     * @return Um JLabel configurado para exibir um ícone.
     */
    public static JLabel createLogoLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
