package conversiontcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConversionTCPClient {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String SERVER = "localhost";
    public static final int PORT = 3400;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    private Socket clientSideSocket;

    public ConversionTCPClient() {
        System.out.println("Cliente iniciado...");
    }

    public void init() throws Exception {

        clientSideSocket = new Socket(SERVER, PORT);

        createStreams(clientSideSocket);

        protocol(clientSideSocket);

        clientSideSocket.close();
    }

    public void protocol(Socket socket) throws Exception {

        System.out.println("Seleccione una operación:");
        System.out.println("1: Decimal → Binario");
        System.out.println("2: Binario → Decimal");
        System.out.println("3: Decimal → Hexadecimal");
        System.out.println("4: Hexadecimal → Decimal");
        System.out.println("5: Binario → Hexadecimal");
        System.out.println("6: Hexadecimal → Binario");

        System.out.print("Opción: ");
        int opcion = SCANNER.nextInt();
        SCANNER.nextLine();

        String mensaje = "";

        if (opcion == 1 || opcion == 3) {
            System.out.print("Ingrese número decimal: ");
            String num = SCANNER.nextLine();
            System.out.print("Ingrese longitud/ancho: ");
            String ancho = SCANNER.nextLine();
            mensaje = opcion + ";" + num + ";" + ancho;

        } else if (opcion == 2 || opcion == 4 || opcion == 6) {
            System.out.print("Ingrese número: ");
            String num = SCANNER.nextLine();
            mensaje = opcion + ";" + num;

        } else if (opcion == 5) {
            System.out.print("Ingrese número binario: ");
            String num = SCANNER.nextLine();
            System.out.print("Ingrese ancho hexadecimal: ");
            String ancho = SCANNER.nextLine();
            mensaje = opcion + ";" + num + ";" + ancho;
        }

        toNetwork.println(mensaje);

        String respuesta = fromNetwork.readLine();
        System.out.println("Resultado del servidor: " + respuesta);
    }

    private void createStreams(Socket socket) throws Exception {

        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String args[]) throws Exception {
        ConversionTCPClient client = new ConversionTCPClient();
        client.init();
    }
}