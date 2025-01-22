import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	private int port;

	public SocketServer(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("클라이언트 연결 완료");

				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				HttpRequestParser parser = new HttpRequestParser();
				HttpRequest httpRequest = parser.parse(reader);

				if (httpRequest.getHttpMethod() == null) {
					System.out.println("잘못된 요청으로 연결을 종료합니다");
					clientSocket.close();
					continue;
				}

				HttpRequestHandler requestHandler = new HttpRequestHandler();
				requestHandler.handle(httpRequest);


				OutputStream output = clientSocket.getOutputStream();
				String httpResponse = "HTTP/1.1 200 OK\r\n" +
					"Content-Type: text/plain\r\n" +
					"Content-Length: 2\r\n" +
					"\r\n" +
					"OK";
				output.write(httpResponse.getBytes());
				output.flush();

				clientSocket.close();
				System.out.println("클라이언트 연결 종료");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
