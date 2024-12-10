import javax.swing.*;
import java.awt.*;
import hardware.SystemInfo;
import ui.UIUtils;
import ui.UICamera;
import ui.UIHeadset;
import ui.UIKeyboard;
import ui.UIMouse;
import ui.UISpeaker;

public class Main {
    public static void main(String[] args) {
        // Configuração da Janela Principal
        JFrame frame = createMainFrame();

        // Painel de Informações (Esquerda)
        JPanel infoPanel = createInfoPanel();
        frame.add(infoPanel, BorderLayout.CENTER);

        // Painel de Botões (Direita)
        JPanel buttonPanel = createButtonPanel(frame);
        frame.add(buttonPanel, BorderLayout.EAST);

        // Exibir a janela principal
        frame.setVisible(true);
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Testy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private static JPanel createInfoPanel() { // Usando static para função pertencer a classe
        JPanel infoPanel = UIUtils.createInfoPanel();

        // Obtendo e exibindo informações do Sistema Operacional
        String osInfo = SystemInfo.getOSInfo();
        JLabel osLabel = UIUtils.createInfoLabel(osInfo);
        infoPanel.add(osLabel);

        // Obtendo e exibindo a Arquitetura do Processador
        String processorInfo = SystemInfo.getProcessorInfo();
        JLabel processorLabel = UIUtils.createInfoLabel(processorInfo);
        infoPanel.add(processorLabel);

        // Obtendo e exibindo informações sobre a GPU
        String gpuInfo = SystemInfo.getGPUInfo();
        JLabel gpuLabel = UIUtils.createInfoLabel(gpuInfo);
        infoPanel.add(gpuLabel);

        // Exibindo informações da BIOS
        String biosInfo = SystemInfo.getBIOSInfo();
        JLabel biosLabel = UIUtils.createInfoLabel(biosInfo);
        infoPanel.add(biosLabel);

        // Exibindo lista de Periféricos Conectados
        String peripheralsInfo = SystemInfo.getPeripheralsInfo();
        JLabel peripheralsLabel = UIUtils.createInfoLabel(peripheralsInfo);
        infoPanel.add(peripheralsLabel);

        // Exibindo a quantidade de Memória Disponível
        JLabel memoryLabel = UIUtils.createInfoLabel(SystemInfo.getMemoryInfo());
        infoPanel.add(memoryLabel);

        // Iniciar atualização dinâmica das informações de memória
        startDynamicUpdate(memoryLabel);

        return infoPanel;
    }

    private static JPanel createButtonPanel(JFrame frame) {
        JPanel buttonPanel = UIUtils.createButtonPanel();

        // Logo
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("images/robo.png").getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH));
        JLabel logoLabel = UIUtils.createLogoLabel(logoIcon);
        buttonPanel.add(logoLabel);

        // Botões
        String[] buttonLabels = {"TECLADO", "MOUSE", "FONE DE OUVIDO", "CAIXA DE SOM", "WEBCAM"};
        for (String label : buttonLabels) {
            JButton button = UIUtils.createButton(label);
            button.addActionListener(e -> openTestWindow(label));
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(10)); // Espaço entre os botões
        }

        // Espaço vazio final para alinhar botões
        buttonPanel.add(Box.createVerticalGlue());

        return buttonPanel;
    }

    private static void openTestWindow(String testType) {
        switch (testType) {
            case "TECLADO":
                UIKeyboard keyboardTest = new UIKeyboard();
                keyboardTest.showUI();
                break;
            case "MOUSE":
                UIMouse mouseTest = new UIMouse();
                mouseTest.showUI();
                break;
            case "FONE DE OUVIDO":
                UIHeadset headsetTest = new UIHeadset();
                headsetTest.showUI();
                break;
            case "CAIXA DE SOM":
                UISpeaker speakerTest = new UISpeaker();
                speakerTest.showUI();
                break;
            case "WEBCAM":
                UICamera webcamTest = new UICamera();
                webcamTest.showUI();
                break;
            default: // JavaSwing: painel para teste não implementado
                JOptionPane.showMessageDialog(null, "Teste não implementado para: " + testType, "Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private static void startDynamicUpdate(JLabel memoryLabel) {
        Timer timer = new Timer(1000, e -> { // Função lambda pro timer
            // Atualizar o JLabel com as informações de memória
            String memoryInfo = SystemInfo.getMemoryInfo();
            memoryLabel.setText("<html><div style='color: white;'>" + memoryInfo + "</div></html>");
        });
        timer.start();
    }
}
