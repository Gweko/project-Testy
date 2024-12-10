package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Classe que representa uma interface gráfica para testar o funcionamento do mouse.
 * A interface permite testar os botões, a rolagem e a movimentação do mouse.
 */
public class UIMouse {
    private JFrame frame;  // Janela principal da aplicação
    private MousePanel mousePanel;  // Painel personalizado para interações do mouse
    private JLabel mouseLabel;  // Label que exibe a direção do movimento do mouse
    private JLabel scrollLabel;  // Label que exibe a direção da rolagem do mouse
    private Point lastMousePosition;  // Posição anterior do mouse para calcular a direção do movimento

    /**
     * Construtor da classe UIMouse.
     * Inicializa a interface gráfica e configura os listeners de mouse.
     */
    public UIMouse() {
        frame = new JFrame("Teste de Mouse");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela

        mousePanel = new MousePanel();
        mousePanel.setBackground(new Color(30, 30, 60));

        // Adiciona um listener para cliques do mouse
        mousePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                handleMouseClick(button);
            }
        });

        // Adiciona um listener para a rolagem do mouse
        mousePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheel(e);
            }
        });

        // Adiciona listeners para movimentação e arraste do mouse
        mousePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseMovement(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMovement(e);
            }
        });

        mouseLabel = new JLabel("Movimento do mouse: ");
        mouseLabel.setForeground(Color.WHITE);
        mouseLabel.setFont(new Font("Arial", Font.BOLD, 14));

        scrollLabel = new JLabel("Rolagem: ");
        scrollLabel.setForeground(Color.WHITE);
        scrollLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(mouseLabel);
        labelPanel.add(scrollLabel);
        labelPanel.setBackground(new Color(30, 30, 60));

        frame.add(mousePanel, BorderLayout.CENTER);
        frame.add(labelPanel, BorderLayout.SOUTH);
    }

    /**
     * Trata o clique dos botões do mouse.
     *
     * @param button o botão do mouse que foi clicado.
     */
    private void handleMouseClick(int button) {
        switch (button) {
            case MouseEvent.BUTTON1: // Botão esquerdo
                mousePanel.setLeftButtonPressed(true);
                break;
            case MouseEvent.BUTTON2: // Botão do meio
                mousePanel.setMiddleButtonPressed(true);
                break;
            case MouseEvent.BUTTON3: // Botão direito
                mousePanel.setRightButtonPressed(true);
                break;
        }
        mousePanel.repaint();
    }

    /**
     * Trata a rolagem do mouse.
     *
     * @param e o evento de rolagem do mouse.
     */
    private void handleMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            mousePanel.setScrollDirection("UP");
        } else {
            mousePanel.setScrollDirection("DOWN");
        }
        updateScrollLabel();
    }

    /**
     * Trata o movimento do mouse.
     *
     * @param e o evento de movimento do mouse.
     */
    private void handleMouseMovement(MouseEvent e) {
        if (lastMousePosition != null) {
            int dx = e.getX() - lastMousePosition.x;
            int dy = e.getY() - lastMousePosition.y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    mousePanel.setMouseDirection("RIGHT");
                } else {
                    mousePanel.setMouseDirection("LEFT");
                }
            } else {
                if (dy > 0) {
                    mousePanel.setMouseDirection("DOWN");
                } else {
                    mousePanel.setMouseDirection("UP");
                }
            }
        }
        lastMousePosition = e.getPoint();
        updateMouseLabel();
    }

    /**
     * Atualiza o label de direção do movimento do mouse.
     */
    private void updateMouseLabel() {
        mouseLabel.setText(String.format("Movimento do mouse: %s", mousePanel.getMouseDirection()));
    }

    /**
     * Atualiza o label de direção da rolagem do mouse.
     */
    private void updateScrollLabel() {
        scrollLabel.setText(String.format("Rolagem: %s", mousePanel.getScrollDirection()));
    }

    /**
     * Exibe a interface gráfica.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Classe interna que representa um painel personalizado para as interações do mouse.
     */
    class MousePanel extends JPanel {
        private boolean leftButtonPressed = false;  // Flag para indicar se o botão esquerdo está pressionado
        private boolean middleButtonPressed = false;  // Flag para indicar se o botão do meio está pressionado
        private boolean rightButtonPressed = false;  // Flag para indicar se o botão direito está pressionado
        private String scrollDirection = "";  // Direção da rolagem do mouse
        private String mouseDirection = "";  // Direção do movimento do mouse

        public void setLeftButtonPressed(boolean pressed) {
            leftButtonPressed = pressed;
        }

        public void setMiddleButtonPressed(boolean pressed) {
            middleButtonPressed = pressed;
        }

        public void setRightButtonPressed(boolean pressed) {
            rightButtonPressed = pressed;
        }

        public void setScrollDirection(String direction) {
            scrollDirection = direction;
        }

        public String getScrollDirection() {
            return scrollDirection;
        }

        public void setMouseDirection(String direction) {
            mouseDirection = direction;
        }

        public String getMouseDirection() {
            return mouseDirection;
        }

        /**
         * Sobrescreve o método paintComponent para desenhar os componentes gráficos do mouse.
         *
         * @param g o contexto gráfico.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Desenho do corpo do mouse
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(150, 150, 200, 300, 100, 150);

            // Desenho dos botões do mouse
            g2.setColor(leftButtonPressed ? Color.RED : Color.DARK_GRAY);
            g2.fillRoundRect(160, 160, 80, 140, 20, 20);  // Botão esquerdo

            g2.setColor(rightButtonPressed ? Color.BLUE : Color.DARK_GRAY);
            g2.fillRoundRect(260, 160, 80, 140, 20, 20);  // Botão direito

            // Desenho do scroll wheel
            g2.setColor(middleButtonPressed ? Color.GREEN : Color.BLACK);
            g2.fillRoundRect(235, 190, 30, 80, 10, 10);  // Scroll wheel

            // Reseta os estados dos botões
            leftButtonPressed = false;
            middleButtonPressed = false;
            rightButtonPressed = false;
        }
    }
}
