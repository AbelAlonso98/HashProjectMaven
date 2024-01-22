package solucionJulio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceTask implements Runnable {

	Socket socket;

	public ServiceTask(Socket socket) throws SocketException {
		socket.setSoTimeout(3000);
		this.socket = socket;
	}

	@Override
	public void run() {
		try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
			MessageDigest md = MessageDigest.getInstance("SHA-224");
			byte[] buffer = new byte[1024];
			int length;
			while (true) {
				while ((length = in.read(buffer)) != -1) {
					md.update(buffer, 0, length);
				}
				out.write(md.digest());
				out.flush();
			}

		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: " + e.getLocalizedMessage() + " (" + socket.getInetAddress() + ")");
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + " (" + socket.getInetAddress() + ")");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + " (" + socket.getInetAddress() + ")");
		}

	}

}
