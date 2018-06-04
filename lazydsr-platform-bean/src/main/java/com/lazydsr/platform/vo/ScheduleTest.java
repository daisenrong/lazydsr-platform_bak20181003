package com.lazydsr.platform.vo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ScheduleTest
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.vo
 * Created by Lazy on 2018/6/2 23:15
 * Version: 0.1
 * Info: @TODO:...
 */
@Slf4j
public class ScheduleTest {
    public void runschedule() {
        log.info("runschedule");
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("172.25.1.246", 80));
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
