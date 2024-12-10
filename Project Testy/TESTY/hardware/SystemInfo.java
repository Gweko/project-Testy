package hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para obter informações do sistema, como informações do SO, processador, GPU, BIOS, periféricos e memória.
 */
public class SystemInfo {

    /**
     * Método utilitário para executar comandos do sistema e retornar a saída como uma String.
     *
     * @param command o comando a ser executado.
     * @return a saída do comando como uma String.
     */
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true); // Erros de subprocessos serão mostrados no output do código
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro ao executar comando: " + command;
        }
        return output.toString();
    }

    /**
     * Obtém informações do sistema operacional.
     *
     * @return uma String contendo o nome, versão e arquitetura do sistema operacional.
     */
    public static String getOSInfo() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        return "Sistema Operacional: " + osName + "<br>Versão: " + osVersion + "<br>Arquitetura: " + osArch;
    }

    /**
     * Obtém informações do processador.
     *
     * @return uma String contendo a arquitetura do processador.
     */
    public static String getProcessorInfo() {
        String processorArch = System.getProperty("sun.arch.data.model");
        return "Arquitetura do Processador: " + processorArch + " bits";
    }

    /**
     * Obtém informações da GPU.
     *
     * @return uma String contendo informações da GPU.
     */
    public static String getGPUInfo() {
        String output = executeCommand("wmic path Win32_VideoController get Caption,DriverVersion,AdapterRAM,AdapterCompatibility,VideoModeDescription");
        return "GPU: <br>" + output.replaceAll("\\b(Caption|DriverVersion|AdapterRAM|AdapterCompatibility|VideoModeDescription)\\b", "").trim().replace("\n", "<br>");
    }

    /**
     * Obtém informações do BIOS.
     *
     * @return uma String contendo informações do BIOS.
     */
    public static String getBIOSInfo() {
        String output = executeCommand("wmic bios get Manufacturer,SMBIOSBIOSVersion");
        return "BIOS Info: " + output.replaceAll("\\b(Manufacturer|SMBIOSBIOSVersion)\\b", "").trim().replace("\n", "<br>");
    }

    /**
     * Obtém informações sobre periféricos conectados relevantes.
     *
     * @return uma String contendo a lista de periféricos relevantes.
     */
    public static String getPeripheralsInfo() {
        String output = executeCommand("wmic path Win32_PnPEntity get Caption");
        String[] lines = output.split("\n");
        List<String> peripherals = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty() && isRelevantPeripheral(line.trim())) {
                peripherals.add(line.trim());
            }
        }
        return formatPeripherals(peripherals);
    }

    /**
     * Verifica se uma linha de saída representa um periférico relevante.
     *
     * @param line a linha de saída a ser verificada.
     * @return true se o periférico for relevante, false caso contrário.
     */
    private static boolean isRelevantPeripheral(String line) {
        String lowerCaseLine = line.toLowerCase();
        return lowerCaseLine.contains("keyboard") || 
               lowerCaseLine.contains("mouse") || 
               lowerCaseLine.contains("headset") || 
               lowerCaseLine.contains("speaker") || 
               lowerCaseLine.contains("webcam") ||
               lowerCaseLine.contains("camera") ||
               lowerCaseLine.contains("microphone");
    }

    /**
     * Formata a lista de periféricos relevantes em uma String HTML.
     *
     * @param peripherals a lista de periféricos.
     * @return uma String formatada contendo a lista de periféricos.
     */
    private static String formatPeripherals(List<String> peripherals) {
        StringBuilder formattedOutput = new StringBuilder("Periféricos Para Teste:<br>");
        for (String peripheral : peripherals) {
            formattedOutput.append(" - ").append(peripheral).append("<br>");
        }
        return formattedOutput.toString();
    }

    /**
     * Obtém informações sobre a memória do sistema.
     *
     * @return uma String contendo informações sobre a memória livre, total e máxima.
     */
    public static String getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        return "Memória Livre: " + freeMemory + " MB<br>Memória Total: " + totalMemory + " MB<br>Memória Máxima: " + maxMemory + " MB";
    }
}
