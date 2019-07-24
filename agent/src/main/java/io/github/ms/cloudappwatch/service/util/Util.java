package io.github.ms.cloudappwatch.service.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util {

    public static final String hostname;

    static {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }


}
