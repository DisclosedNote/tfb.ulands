package foss.tfb.ulands.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import foss.tfb.ulands.stage.Player;

public class Network {
    public static int DEFAULT_GAME_PORT = 19784;
    public static String DEFAULT_IP_ADDRESS = "127.0.0.1";

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(AuthorizePackage.class);
        kryo.register(AuthorizeStatusPackage.class);
        kryo.register(ChatMessagePackage.class);

        kryo.register(Syncable.class);
        kryo.register(Syncable[].class);
        kryo.register(SyncPackage.class);

        kryo.register(int[].class);

        kryo.addDefaultSerializer(Player.class, Serializer.PlayerSerializer.class);
    }

    static public class Package {

    }

    static public class AuthorizePackage extends Package {
        public String name;
        public String password;
    }

    static public class AuthorizeStatusPackage extends Package {
        public boolean authorized;
    }

    static public class ChatMessagePackage extends Package {
        public String text;
    }

    static public class SyncPackage extends Package {
        public Network.Syncable<?>[] objects;

        public SyncPackage(Network.Syncable<?>... syncables)
        {
            objects = new Network.Syncable<?>[syncables.length];

            for(int i = 0; i < syncables.length; i++){
                objects[i] = syncables[i];
            }
        }

        public SyncPackage()
        {
        }
    }

    static public class Syncable<T> {
        public long id;
        public String name;
        public T value;

        public Syncable(long id, String name, T value)
        {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public Syncable()
        {
        }
    }

}