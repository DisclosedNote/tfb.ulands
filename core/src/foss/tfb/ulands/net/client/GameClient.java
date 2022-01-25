package foss.tfb.ulands.net.client;

import com.esotericsoftware.kryonet.Client;
import foss.tfb.ulands.net.Network;

import java.io.IOException;

public class GameClient
{
    static public int DEFAULT_TIMEOUT = 5000;

    Client client;
    String name;

    public GameClient()
    {

        client = new Client();
        client.start();

        Network.register(client);

        
    }

    public void connect(String host, int port) throws IOException
    {
        client.connect(GameClient.DEFAULT_TIMEOUT, host, port);
    }

}
