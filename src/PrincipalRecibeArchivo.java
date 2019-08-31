
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class PrincipalRecibeArchivo {

    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2500);
        PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datos;
        String datosEntrada;
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            datos = scanner.nextLine();
            escritor.println(datos);
            byte[] mybytearray = new byte[4096];
            InputStream is = socket.getInputStream();
            System.out.println("aaaaaaaaaaaaaaaaa");
            fos = new FileOutputStream("C:\\Users\\Admin\\Desktop\\ejemplo5.txt");
            bos = new BufferedOutputStream(fos);
            System.out.println("aaaaaaaaaaaaaaaaa2");
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            System.out.println("aaaaaaaaaaaaaaaaa3");
            System.out.println(bytesRead+"wwwwwwwww");
            current = bytesRead;
            System.out.println("aaaaaaaaaaaaaaaaa4");
            do {
                bytesRead = is.read(mybytearray, 0, (mybytearray.length - current));
                
                System.out.println("aaaaaaaaaaaaaaaaa5");

                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println("File " + datos
                    + " downloaded (" + current + " bytes read)");
           }
        
        

    }
}

