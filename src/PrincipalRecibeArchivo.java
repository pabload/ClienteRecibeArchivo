
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalRecibeArchivo {

    public static void main(String[] args) throws IOException {
        ValidarRutaDescarga(args[0], args[1], args[2]);
        /*File direcion = new File(new File(".").getCanonicalPath() + "\\Downloads");
        if (direcion.exists()) {
            String ip = args[0];
            int puerto = Integer.valueOf(args[1]);
            String datos = args[2];
            Socket socket = new Socket(ip, puerto);
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String datosEntrada = "";
            int tamar;
            int bytesRead;
            int current = 0;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            if (datos.equals("fin")) {
                escritor.println("fin");
                //socket.close();
                System.exit(0);
            } else {
                escritor.println(datos);
                datosEntrada = lector.readLine();
                if (!datosEntrada.equals("non")) {
                    File Archivo = new File(datos);
                    tamar = Integer.parseInt(datosEntrada);
                    byte[] mybytearray = new byte[tamar];
                    InputStream is = socket.getInputStream();
                    fos = new FileOutputStream(direcion + "\\" + Archivo.getName());
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
                    socket.close();
                    System.out.println("File " + datos + " downloaded (" + current + " bytes read)");
                } else {
                    System.out.println("no existe");
                    System.exit(0);
                }
            }

        } else {
            System.out.println("ruta no existente");
        }*/
    }
    public static void ValidarRutaDescarga(String ip , String puerto ,String datos ){
        try {
            File direcion = new File(new File(".").getCanonicalPath() + "\\Downloads");
            EnviarArchivo(direcion, ip, Integer.parseInt(puerto), datos);
        } catch (IOException ex) {
            System.out.println("Error en la ruta descarga"+ ex);
        }
    }
    public static void EnviarArchivo(File direcion,String ip , int puerto ,String datos ){
         if (direcion.exists()) {
            Socket socket = null;
             try {
                 socket = new Socket(ip, puerto);
             } catch (IOException ex) {
                 System.out.println("Error al crear socket "+ex);
             }
            PrintWriter escritor = null;
             try {
                 escritor = new PrintWriter(socket.getOutputStream(), true);
             } catch (IOException ex) {
                 System.out.println("Error al crear escritor "+ex);
             }
            BufferedReader lector = null;
             try {
                 lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             } catch (IOException ex) {
                 System.out.println("Error al crear Lector "+ex);
             }
            String datosEntrada = "";
            int tamar;
            int bytesRead;
            int current = 0;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            if (datos.equals("fin")) {
                escritor.println("fin");
                //socket.close();
                System.exit(0);
            } else {
                escritor.println(datos);
                try {
                    datosEntrada = lector.readLine();
                } catch (IOException ex) {
                    System.out.println("Error al leer"+ ex);
                }
                if (!datosEntrada.equals("non")) {
                    try {
                    File Archivo = new File(datos);
                    tamar = Integer.parseInt(datosEntrada);
                    byte[] mybytearray = new byte[tamar];
                    InputStream is = socket.getInputStream();
                    fos = new FileOutputStream(direcion + "\\" + Archivo.getName());
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
                    socket.close();
                    System.out.println("File " + datos + " downloaded (" + current + " bytes read)"); 
                    } catch (Exception e) {
                        System.out.println("Error al recibir mensaje"+ e);
                    }
                } else {
                    System.out.println("no existe");
                    System.exit(0);
                }
            }

        } else {
            System.out.println("ruta no existente");
        }
    }
}
