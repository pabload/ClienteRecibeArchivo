
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PrincipalRecibeArchivo {

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Ruta de tu carpeta cliente");
            Scanner scanner = new Scanner(System.in);
            String RutaCarpetaCliente = scanner.nextLine();
            File direcion = new File(RutaCarpetaCliente);
            if (direcion.exists()) {
                System.out.println("existe");
                Socket socket = new Socket("127.0.0.1", 2500);
                PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String datos;
                String datosEntrada = "";
                int tamar;
                int bytesRead;
                int current = 0;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                while (true) {
                    datos = scanner.nextLine();
                    if (datos.equals("fin")) {
                        socket.close();
                         System.exit(0);
                    } else {
                        escritor.println(datos);
                        datosEntrada = lector.readLine();
                        if (!datosEntrada.equals("non")) {
                            tamar = Integer.parseInt(datosEntrada);
                            System.out.println("TAMAÑO" + tamar);
                            System.out.print(datosEntrada);
                            byte[] mybytearray = new byte[tamar];
                            InputStream is = socket.getInputStream();
                            fos = new FileOutputStream(direcion + "\\" + datos);
                            bos = new BufferedOutputStream(fos);
                            bytesRead = is.read(mybytearray, 0, mybytearray.length);
                            current = bytesRead;
                            do {
                                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));

                                if (bytesRead >= 0) {
                                    current += bytesRead;
                                }
                            } while (bytesRead > 0);
                            bos.write(mybytearray, 0, current);
                            bos.flush();
                            System.out.println("File " + datos + " downloaded (" + current + " bytes read)");
                        } else {
                            System.out.println("no existe");
                        }

                    }
                }

            } else {
                System.out.println("ruta no existente");
            }
        }

    }

}
