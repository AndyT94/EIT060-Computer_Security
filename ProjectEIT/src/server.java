
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;

import org.json.simple.parser.ParseException;

import server.AuditLog;
import server.ResourceMonitor;
import util.Command;
import util.Format;

public class server implements Runnable {
	private ServerSocket serverSocket = null;
	private static int numConnectedClients = 0;
	private ResourceMonitor rm;

	public server(ServerSocket ss) throws IOException {
		serverSocket = ss;
		rm = new ResourceMonitor();
		AuditLog.setUp();
		newListener();
	}

	public void run() {
		try {
			SSLSocket socket = (SSLSocket) serverSocket.accept();

			newListener();
			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			String issuer = cert.getIssuerDN().getName();
	//		BigInteger serialNbr = cert.getSerialNumber();
			numConnectedClients++;
			
			
//			System.out.println("client connected");

//			System.out.println("Issuer: " + issuer);
//			System.out.println("Serial number: " + serialNbr);
//			System.out.println("Welcome " + subject);
//			System.out.println(numConnectedClients + " concurrent connection(s)\n");

			subject = subject.substring(3, 13);
			
			AuditLog.log(subject + " has logged in, verified by " + issuer + ", " + numConnectedClients + " concurrent connection(s)");
			
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String clientMsg = null;
			try {
				while ((clientMsg = in.readLine()) != null) {
					
					Command cmd = Format.decode(clientMsg);
					String result = rm.tryAccess(subject, cmd);
					
					//String rev = new StringBuilder(clientMsg).reverse().toString();
					//System.out.println("received '" + clientMsg + "' from client");
					//System.out.print("sending '" + result + "' to client...");
					out.println(result);
					out.flush();
				}
			} catch (IllegalArgumentException | ParseException e) {
				e.printStackTrace();
			}
			in.close();
			out.close();
			socket.close();
			numConnectedClients--;
			//System.out.println("client disconnected");
			//System.out.println(numConnectedClients + " concurrent connection(s)\n");
			AuditLog.log(subject + " disconnected, " + numConnectedClients + " concurrent connection(s)");
		} catch (IOException e) {
			System.out.println("Client died: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private void newListener() {
		(new Thread(this)).start();
	} // calls run()

	public static void main(String args[]) {
		System.out.println("\nServer Started\n");
		int port = -1;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		}
		String type = "TLS";
		try {
			ServerSocketFactory ssf = getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			((SSLServerSocket) ss).setNeedClientAuth(true); // enables client
															// authentication
			new server(ss);
		} catch (IOException e) {
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try { // set up key manager to perform server authentication
				SSLContext ctx = SSLContext.getInstance("TLS");
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				char[] password = "password".toCharArray();

				ks.load(new FileInputStream("../certificate/serverkeystore"), password); // keystore
																							// password
																							// (storepass)
				ts.load(new FileInputStream("../certificate/servertruststore"), password); // truststore
																							// password
																							// (storepass)
				kmf.init(ks, password); // certificate password (keypass)
				tmf.init(ts); // possible to use keystore as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ServerSocketFactory.getDefault();
		}
		return null;
	}
}
