public class HttpRequest {
	private HttpMethod httpMethod;
	private String body;

	public HttpRequest(HttpMethod httpMethod, String body) {
		this.httpMethod = httpMethod;
		this.body = body;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getBody() {
		return body;
	}
}
