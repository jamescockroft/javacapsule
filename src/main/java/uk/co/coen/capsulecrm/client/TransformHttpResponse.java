package uk.co.coen.capsulecrm.client;

import com.google.common.base.Function;
import com.ning.http.client.Response;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;

public class TransformHttpResponse<T> implements Function<Response, T> {
    private final XStream xstream;

    public TransformHttpResponse(XStream xstream) {
        this.xstream = xstream;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T apply(Response response) {
        if (response.getStatusCode() < 200 || response.getStatusCode() > 299)
            throw new IllegalStateException("Response is not OK: " + response.getStatusCode() + " " + response.getStatusText());

        try {
            return (T) xstream.fromXML(response.getResponseBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
