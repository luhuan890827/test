package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	private BufferedReader socketReader;
	private PrintWriter socketWriter;
	private String hostIP;
	private int hostPort;

	public SocketClient(String ip, int port) {
		this.hostIP = ip;
		this.hostPort = port;
	}

	public String getFile(String fileNameToGet) {
		StringBuffer fileLines = new StringBuffer();
		try {
			socketWriter.println(fileNameToGet);
			socketWriter.flush();
			String line = null;
			while ((line = socketReader.readLine()) != null)
				fileLines.append(line + "\n");
		} catch (IOException e) {
			System.out.println("Error reading from file: " + fileNameToGet);
		}
		return fileLines.toString();
	}

	public void setUpConnection() {
		try {
			Socket client = new Socket(hostIP, hostPort);
			socketReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());
		} catch (UnknownHostException e) {
			System.out
					.println("Error1 setting up socket connection: unknown host at "
							+ hostIP + ":" + hostPort);
		} catch (IOException e) {
			System.out.println("Error2 setting up socket connection: " + e);
		}
	}

	public void tearDownConnection() {
		try {
			socketWriter.close();
			socketReader.close();
		} catch (IOException e) {
			System.out.println("Error tearing down socket connection: " + e);
		}
	}

	public static void main(String args[]) {
		SocketClient remoteFileClient = new SocketClient("127.0.0.1", 1001);
		remoteFileClient.setUpConnection();
		StringBuffer fileContents = new StringBuffer();
		fileContents.append(remoteFileClient.getFile("c:/test.json")); 
		// remoteFileClient.tearDownConnection();
		System.out.println(fileContents);
	}
}
