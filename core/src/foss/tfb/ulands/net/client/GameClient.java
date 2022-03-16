package foss.tfb.ulands.net.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.BaseObject;

import java.io.IOException;

public class GameClient
{
    // TODO: client dispose

    static public int DEFAULT_TIMEOUT = 5000;

    Client client;
    String name;

    protected ChatManager chatManager;

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

        chatManager = new ChatManager(this);
    }

    public void connect(String host, int port) throws IOException
    {
        client.connect(GameClient.DEFAULT_TIMEOUT, host, port);
    }

    public void disconnect()
    {
        if(client.isConnected())
        {
            client.close();
        }
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
        Network.AuthorizePackage authorizeData = new Network.AuthorizePackage();
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

        // just for testing purposes
        if(o instanceof Network.SyncPartsPackage){
            Network.SyncPartsPackage s = (Network.SyncPartsPackage) o;
            if(s.objects == null) return;
            Network.SyncablePart<?>[] objects = s.objects;

            for(Network.SyncablePart<?> syncablePart : objects) {

                if(syncablePart.value instanceof int[])
                {
                    System.out.println("got some specific data: " + syncablePart.id + " " + syncablePart.name + " " + ((int[]) syncablePart.value)[0]);
                    continue;
                }

                System.out.println("got some data: " + syncablePart.id + " " + syncablePart.name + " " + syncablePart.value);
            }
            return;
        }

        // just for testing purposes
        if(o instanceof Network.FullSyncPackage){
            Network.FullSyncPackage s = (Network.FullSyncPackage) o;
            if(s.objects == null) return;
            BaseObject[] objects = s.objects;

            System.out.println(objects.length);

            for(BaseObject asd : objects) {

                String sb = "got object " +
                        asd.id +
                        " " +
                        asd.x +
                        " " +
                        asd.y +
                        " " +
                        asd.width +
                        " " +
                        asd.height +
                        " " +
                        asd.rotation;

                System.out.println(sb);
            }
            return;
        }

    }

    public void idle(Connection c)
    {

    }

    /* Client managers */

    public ChatManager getChatManager()
    {
        return chatManager;
    }
}
