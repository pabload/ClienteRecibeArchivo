
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
        String datosEntrada="";
        int tamar;
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            
            datos = scanner.nextLine();
            escritor.println(datos);
            datosEntrada=lector.readLine();
            tamar=Integer.parseInt(datosEntrada);
            System.out.print(datosEntrada);
            byte[] mybytearray = new byte[tamar];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream("C:\\Users\\G5\\Desktop\\recibecliente\\"+datos);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;
            do {
                bytesRead  = is.read(mybytearray, current, (mybytearray.length - current));
                
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > 0);
            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + datos + " downloaded (" + current + " bytes read)");
           }
        
    }
    
    
}

