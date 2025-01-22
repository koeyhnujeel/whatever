import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {
	public HttpRequest parse(BufferedReader reader) throws IOException {
		String firstLine = reader.readLine();
		HttpMethod httpMethod = null;

		if (firstLine != null) {
			System.out.println(firstLine);
			httpMethod = HttpMethod.valueOf(firstLine.split(" ")[0]);
		}

		String line;
		int contentLength = 0;
		while ((line = reader.readLine()) != null && !line.isEmpty()) {
			System.out.println(line);

			if (line.startsWith("Content-Length:")) {
				contentLength = Integer.parseInt(line.split(": ")[1]);
			}
		}

		String body = "";
		if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.PATCH) {
			if (contentLength > 0) {
				char[] bodyChars = new char[contentLength];
				reader.read(bodyChars);
				body = new String(bodyChars);
				System.out.println(body);
			}
		}

		return new HttpRequest(httpMethod, body);
	}
}
