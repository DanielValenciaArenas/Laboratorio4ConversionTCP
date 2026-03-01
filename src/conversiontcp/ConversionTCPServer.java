package conversiontcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConversionTCPServer {

    public static final int PORT = 3400;

    private ServerSocket listener;
    private Socket serverSideSocket;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    public ConversionTCPServer() {
        System.out.println("Servidor iniciado en el puerto: " + PORT);
    }

    public void init() throws Exception {

        listener = new ServerSocket(PORT);

        while (true) {

            serverSideSocket = listener.accept();

            createStreams(serverSideSocket);

            protocol(serverSideSocket);
        }
    }

    public void protocol(Socket socket) throws Exception {

        String message = fromNetwork.readLine();
        System.out.println("[Servidor] Mensaje recibido: " + message);

        String[] parts = message.split(";");

        int opcion = Integer.parseInt(parts[0]);

        String resultado = "";

        switch (opcion) {

            case 1:
                int num1 = Integer.parseInt(parts[1]);
                int bits = Integer.parseInt(parts[2]);
                resultado = decimalABinario(num1, bits);
                break;

            case 2:
                resultado = String.valueOf(binarioADecimal(parts[1]));
                break;

            case 3:
                int num3 = Integer.parseInt(parts[1]);
                int ancho = Integer.parseInt(parts[2]);
                resultado = decimalAHex(num3, ancho);
                break;

            case 4:
                resultado = String.valueOf(hexADecimal(parts[1]));
                break;

            case 5:
                int ancho5 = Integer.parseInt(parts[2]);
                resultado = binarioAHex(parts[1], ancho5);
                break;

            case 6:
                resultado = hexABinario(parts[1]);
                break;
        }

        toNetwork.println(resultado);
    }

    private void createStreams(Socket socket) throws Exception {

        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Métodos de Conversión

    public static String decimalABinario(int numero, int bits) {
        String bin = Integer.toBinaryString(numero);
        return String.format("%" + bits + "s", bin).replace(' ', '0');
    }

    public static int binarioADecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    public static String decimalAHex(int numero, int ancho) {
        String hex = Integer.toHexString(numero).toUpperCase();
        return String.format("%" + ancho + "s", hex).replace(' ', '0');
    }

    public static int hexADecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    public static String binarioAHex(String binario, int ancho) {
        int decimal = Integer.parseInt(binario, 2);
        String hex = Integer.toHexString(decimal).toUpperCase();
        return String.format("%" + ancho + "s", hex).replace(' ', '0');
    }

    public static String hexABinario(String hex) {
        int decimal = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(decimal);
    }

    public static void main(String args[]) throws Exception {
        ConversionTCPServer server = new ConversionTCPServer();
        server.init();
    }
}