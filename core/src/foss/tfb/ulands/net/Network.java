package foss.tfb.ulands.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    public static int DEFAULT_GAME_PORT = 19784;
    public static String DEFAULT_IP_ADDRESS = "127.0.0.1";

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Authorize.class);
        kryo.register(AuthorizeStatus.class);
        kryo.register(String[].class);
        kryo.register(ChatMessage.class);
    }

    static public class Package {

    }

    static public class Authorize extends Package {
        public String name;
        public String password;
    }

    static public class AuthorizeStatus extends Package {
        public boolean authorized;
    }

    static public class ChatMessage extends Package {
        public String text;
    }
}