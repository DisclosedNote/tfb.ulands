package foss.tfb.ulands.net.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
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
        client.addListener(new Listener()
        {
            @Override
            public void connected(Connection connection)
            {
                GameClient.this.connected(connection);
            }

            @Override
            public void disconnected(Connection connection)
            {
                GameClient.this.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o)
            {
                GameClient.this.received(connection, o);
            }

            @Override
            public void idle(Connection connection)
            {
                GameClient.this.idle(connection);
            }
        });
        client.start(); //TODO: move start() to dedicated method

        Network.register(client);
    }

    public void connect(String host, int port) throws IOException
    {
        client.connect(GameClient.DEFAULT_TIMEOUT, host, port);
    }

    public void addListener(Listener listener)
    {
        this.client.addListener(listener);
    }

    public void removeListener(Listener listener)
    {
        this.client.removeListener(listener);
    }

    /* Network */

    public int send(Object o)
    {
        return client.sendTCP(o);
    }

    public int authorize(String name, String password)
    {
        Network.Authorize authorizeData = new Network.Authorize();
        authorizeData.name = name;
        authorizeData.password = password;
        return client.sendTCP(authorizeData);
    }

    /* Network Events */

    public void connected(Connection c)
    {

    }

    public void disconnected(Connection c)
    {

    }

    public void received(Connection c, Object o)
    {

    }

    public void idle(Connection c)
    {

    }

}
