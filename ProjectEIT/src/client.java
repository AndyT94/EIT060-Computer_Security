
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;

import util.Format;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class client {
	private static int failedLogin = 0;
	private static long latestLoginAttempt = System.currentTimeMillis();
	
	public static void main(String[] args) {
		String host = null;
		int port = -1;

		for (int i = 0; i < args.length; i++) {
			System.out.println("args[" + i + "] = " + args[i]);
		}
		if (args.length < 2) {
			System.out.println("USAGE: java client host port");
			System.exit(-1);
		}
		try { /* get input parameters */
			host = args[0];
			port = Integer.parseInt(args[1]);
		} catch (IllegalArgumentException e) {
			System.out.println("USAGE: java client host port");
			System.exit(-1);
		}

		try { /* set up a key manager for client authentication */
			SSLSocketFactory factory = null;

			factory = login(factory);
			System.out.println("Failed logins: " + failedLogin);
			
			failedLogin = 0;
			latestLoginAttempt = System.currentTimeMillis();
			
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			System.out.println("\nsocket before handshake:\n" + socket + "\n");

			/*
			 * send http request
			 *
			 * See SSLSocketClient.java for more information about why there is
			 * a forced handshake here when using PrintWriters.
			 */
			socket.startHandshake();

			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			String issuer = cert.getIssuerDN().getName();
			BigInteger serialNbr = cert.getSerialNumber();
			System.out.println(
					"certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
			System.out.println("Issuer: " + issuer);
			System.out.println("Serial number: " + serialNbr);
			System.out.println("socket after handshake:\n" + socket + "\n");
			System.out.println("secure connection established\n\n");

			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg;

			for (;;) {
				System.out.print(">");
				msg = read.readLine();
				if (msg.equalsIgnoreCase("quit")) {
					break;
				}

				if ((msg = Format.encode(msg)) != null) {
					out.println(msg);
					out.flush();
					System.out.println(in.readLine());
				} else {
					System.out.println("Could not process command!");
				}
			}
			in.close();
			out.close();
			read.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SSLSocketFactory login(SSLSocketFactory factory) {
		try {

			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter username: ");
			String username = scan.nextLine();
			System.out.println("Enter password: ");

			Console console = System.console();
			char[] password = console.readPassword();
			
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore ts = KeyStore.getInstance("JKS");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			SSLContext ctx = SSLContext.getInstance("TLS");
			String ksPath = "../client/users/" + username + "/" + username + "keystore";
			String tsPath = "../client/users/" + username + "/" + username + "truststore";
			ks.load(new FileInputStream(ksPath), password); // keystore
															// password
															// (storepass)
			ts.load(new FileInputStream(tsPath), password); // truststore
															// password
															// (storepass);
			kmf.init(ks, password); // user password (keypass)
			tmf.init(ts); // keystore can be used as truststore here
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			factory = ctx.getSocketFactory();
		} catch (Exception e) {
			System.out.println("Wrong username or password!");
			if(System.currentTimeMillis() - latestLoginAttempt > 300000) {
				failedLogin = 0;
			}
			failedLogin++;
			latestLoginAttempt = System.currentTimeMillis();
			return login(factory);
		}
		return factory;
	}
}
