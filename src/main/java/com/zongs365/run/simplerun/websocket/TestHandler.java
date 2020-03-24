package com.zongs365.run.simplerun.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author tengdj
 * @date 2020/3/24 16:53
 **/
@Slf4j
public class TestHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String name = message.getPayload();
        log.info("收到消息：" + name);
        String cmd = "/deploy/" + name + "/start-docker.sh";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd}, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            session.sendMessage(new TextMessage("clear"));
            while ((line = input.readLine()) != null) {
                log.info(line);
                session.sendMessage(new TextMessage(line + "\r\n"));
            }
            process.waitFor();
        } catch (Exception e) {
            for (StackTraceElement ee : e.getStackTrace()){
                stringBuilder.append(ee.toString()).append("\r\n");
            }
            session.sendMessage(new TextMessage(stringBuilder.toString()));
        }
        session.sendMessage(new TextMessage("end"));
    }
}
