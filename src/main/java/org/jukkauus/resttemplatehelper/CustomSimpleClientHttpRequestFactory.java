package org.jukkauus.resttemplatehelper;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * http://www.jroller.com/jurberg/entry/using_a_hostnameverifier_with_spring
 * Created by jukka on 1/30/16.
 */
public class CustomSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {


    private final HostnameVerifier verifier;

    public CustomSimpleClientHttpRequestFactory(HostnameVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
        }
        super.prepareConnection(connection, httpMethod);
    }


}
