package org.jukkauus.resttemplatehelper;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by jukka on 1/29/16.
 */
public class RestTemplateHelper {


    public String get(String url, boolean ignoreSSLIssues) {
        RestTemplate restTemplate = getRestTemplate(url, ignoreSSLIssues);
        return getInternal(restTemplate, url);
    }

    private String getInternal(RestTemplate template, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = template.exchange(url, HttpMethod.GET, entity, String.class);
        return result.getStatusCode().toString();

    }


    private RestTemplate getRestTemplate(String url, boolean ignoreSSLIssues) {
        RestTemplate restTemplate = null;
        try {
            if (url.startsWith("https")) {
                if (ignoreSSLIssues) {
                    SSLUtil.turnOffSslChecking();
                    CustomSimpleClientHttpRequestFactory factory = new CustomSimpleClientHttpRequestFactory(new NoopHostnameVerifier());
                    restTemplate = new RestTemplate(factory);
                } else {
                    SSLUtil.turnOnSslChecking();
                    CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                    requestFactory.setHttpClient(httpClient);
                    restTemplate = new RestTemplate(requestFactory);
                }
            } else {
                SSLUtil.turnOnSslChecking();
                restTemplate = new RestTemplate();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return restTemplate;
    }


}
