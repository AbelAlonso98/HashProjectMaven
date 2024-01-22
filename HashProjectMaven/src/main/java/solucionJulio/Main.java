package solucionJulio;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	public static void main(String[] args) throws IOException {
		ExecutorService es = Executors.newFixedThreadPool(100);
		try (ServerSocket ss = new ServerSocket(9999)) {
			System.out.println("Waiting for clientes on port 9999");
			while(true) {
				es.submit(new ServiceTask(ss.accept()));
			}
		}
	}

}
