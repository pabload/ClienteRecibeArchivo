
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
    }

    public static void ValidarRutaDescarga(String ip, String puerto, String datos) {
        try {
            File direcion = new File(new File(".").getCanonicalPath() + "\\Downloads");
            EnviarArchivo(direcion, ip, Integer.parseInt(puerto), datos);
        } catch (IOException ex) {
            System.out.println("Error en la ruta descarga" + ex);
        }
    }

    public static void EnviarArchivo(File direcion, String ip, int puerto, String datos) {
        if (direcion.exists()) {
            Socket socket = null;
            try {
                socket = new Socket(ip, puerto);
            } catch (IOException ex) {
                System.out.println("Error al crear socket " + ex);
            }
            PrintWriter escritor = null;
            try {
                escritor = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ex) {
                System.out.println("Error al crear escritor " + ex);
            }
            BufferedReader lector = null;
            try {
                lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                System.out.println("Error al crear Lector " + ex);
            }
            String datosEntrada = "";
            long tamar;
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
                    System.out.println("Error al leer" + ex);
                }
                if (!datosEntrada.equals("non")) {
                    try {
                        File Archivo = new File(datos);
                        tamar = Long.parseLong(datosEntrada);
                        InputStream is = socket.getInputStream();
                        fos = new FileOutputStream(direcion + "\\" + Archivo.getName());
                        bos = new BufferedOutputStream(fos);
                        long count;
                        long c = 0;
                        byte[] buffer = null;
                        if (tamar > 100000000) {

                            buffer = new byte[100000000];
                        } else {
                            if (tamar < 10) {
                                buffer = new byte[(int) tamar];
                            } else {
                                if (tamar <= 100000000) {

                                    buffer = new byte[(int) tamar / 10];
                                }

                            }
                        }
                        while ((count = is.read(buffer)) > 0) {
                            c = c + count;
                            fos.write(buffer, 0, (int) count);
                            //System.out.println("llego a"+c);
                            System.out.print("Espere...." + " \r");
                        }
                        if (tamar == c) {
                            System.out.print("Descarga completada ");
                        } else {
                            System.out.print("Error en la descarga intente de nuevo");
                        }
                        fos.close();
                        fos.flush();
                        socket.close();
                    } catch (Exception e) {
                        System.out.println("Error al recibir mensaje" + e);
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
