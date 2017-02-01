package io.matthd.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Matt on 2017-01-31.
 */
public class BungeeUtil {

    public static void sendPluginMessageToAll(String msg) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("Core");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
    }
}
