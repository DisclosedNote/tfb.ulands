package foss.tfb.ulands.net.client;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.Object;
import foss.tfb.ulands.stage.Player;

import java.io.IOException;

public class GameClient
{
    static public int DEFAULT_TIMEOUT = 5000;

    Client client;
    String name;

    public static Array<Object> objects = null;

    public GameClient()
    {
        // TODO:
        Listener objectsHandler = new Listener()
        {
            @Override
            public void connected(Connection connection)
            {
                objects.add(new Player());
            }
        };
        client = new Client();
        client.addListener(objectsHandler);
        client.start();

        Network.register(client);

        objects = new Array<>();
    }

    public void connect(String host, int port) throws IOException
    {
        client.connect(GameClient.DEFAULT_TIMEOUT, host, port);


    }

}
