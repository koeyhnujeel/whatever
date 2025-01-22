import java.util.Map;

public class HttpRequestHandler {
	private Map<HttpMethod, HttpMethodHandler> map = Map.ofEntries(
		Map.entry(HttpMethod.GET, new Get()),
		Map.entry(HttpMethod.POST, new Post()),
		Map.entry(HttpMethod.PUT, new Put()),
		Map.entry(HttpMethod.PATCH, new Patch()),
		Map.entry(HttpMethod.DELETE, new Delete())
	);

	public void handle(HttpRequest request) {
		HttpMethodHandler httpMethodHandler = map.get(request.getHttpMethod());
		httpMethodHandler.methodHandle(request);
	}
}
