package org.filelandermngr.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.filelandermngr.multicast.MetaObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class HandshakeService {

    public void registerAgent(MetaObject metaObject) {
        log.info("Sending Handshake to Agent...");

        String url = constructUrlFromObject(metaObject);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            StringEntity entity = new StringEntity("{}");

            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.CREATED.value()) {
                log.info("Registered Agent: " + metaObject.getId());
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String constructUrlFromObject(MetaObject metaObject) {
        return "http:/" + metaObject.getIpAddress() + ":" + metaObject.getPortNumber() + metaObject.getApi();
    }
}
