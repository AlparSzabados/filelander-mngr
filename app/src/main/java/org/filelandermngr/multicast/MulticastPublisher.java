package org.filelandermngr.multicast;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filelandermngr.config.AppId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MulticastPublisher {
    private final ObjectMapper om = new ObjectMapper();
    private final AppId appId;

    @Scheduled(fixedRateString = "60000")
    public void multicast() throws IOException {
        MetaObject metaObject = new MetaObject();
        metaObject.setIpAddress("0.0.0.0");
        metaObject.setPortNumber("8091");
        metaObject.setApi("/api/handshake");
        metaObject.setId(appId.getAppID());

        log.info("Multicasting...");
        MulticastSocket socket = new MulticastSocket();
        InetAddress group = InetAddress.getByName("230.0.0.0");

        byte[] buf = om.writeValueAsBytes(metaObject);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.setTimeToLive(10);
        socket.send(packet);

        socket.close();
    }
}