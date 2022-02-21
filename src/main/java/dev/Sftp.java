package dev;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class Sftp {
    private static Session session;
    private static ChannelSftp channelSftp;

    public static void Connect() throws Exception {
        try {
            String user = "debian";
            String pass = "debian";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            String host = "172.16.89.45";

            JSch jSch = new JSch();
            session = jSch.getSession(user, host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void Disconnect() {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
    }

    public static Boolean IsConnected() {
        if (session != null) {
            return  session.isConnected();
        }

        return false;
    }

    public static void Upload() throws Exception {
        try {
            channelSftp.put("C:\\Users\\dimit\\temp\\user.html", "../../../var/www/html/user.html");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
