public class Get implements HttpMethodHandler {

	@Override
	public void methodHandle(HttpRequest httpRequest) {
		System.out.println(httpRequest.getHttpMethod() + " 요청");
	}
}
