package foss.tfb.ulands.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import foss.tfb.ulands.stage.BaseObject;

public class Network {
    public static int DEFAULT_GAME_PORT = 19784;
    public static String DEFAULT_IP_ADDRESS = "127.0.0.1";

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        // Custom game classes serializers section
        kryo.addDefaultSerializer(BaseObject.class, CustomSerializers.BaseObjectSerializer.class);

        kryo.register(AuthorizePackage.class);
        kryo.register(AuthorizeStatusPackage.class);
        kryo.register(ChatMessagePackage.class);

        kryo.register(SyncablePart.class);
        kryo.register(SyncablePart[].class);
        kryo.register(SyncPartsPackage.class);
        kryo.register(BaseObject.class);
        kryo.register(BaseObject[].class);
        kryo.register(FullSyncPackage.class);

        kryo.register(int[].class);


    }

    /* Package classes */

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

    static public class SyncPartsPackage extends Package {
        public SyncablePart<?>[] objects;

        public SyncPartsPackage(SyncablePart<?>... syncableParts)
        {
            objects = new SyncablePart<?>[syncableParts.length];
            System.arraycopy(syncableParts, 0, objects, 0, syncableParts.length);
        }

        public SyncPartsPackage()
        {
        }
    }

    static public class SyncablePart<T> {
        public long id;
        public String name;
        public T value;

        public SyncablePart(long id, String name, T value)
        {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public SyncablePart()
        {
        }
    }

    static public class FullSyncPackage extends Package {
        public BaseObject[] objects;

        public FullSyncPackage(BaseObject... objects)
        {
            this.objects = new BaseObject[objects.length];
            System.arraycopy(objects, 0, this.objects, 0, objects.length);
        }

        public FullSyncPackage()
        {
        }
    }

}