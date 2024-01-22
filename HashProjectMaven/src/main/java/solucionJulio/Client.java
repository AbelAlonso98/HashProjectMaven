package solucionJulio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Base64;

public class Client {
	public static void main(String[] args) {
		try (Socket s = new Socket("192.168.1.102", 9999)) {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			InputStream in = s.getInputStream();
			File f = new File(".//src//main//java//app//TestFile");
			out.writeObject(Files.readAllBytes(f.toPath()));
			s.shutdownOutput();
			System.out.println(Base64.getEncoder().encodeToString(in.readAllBytes()));

		} catch (UnknownHostException e) {
			System.err.println("Host no encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error de entrada/salida");
			e.printStackTrace();
		}

	}

}
