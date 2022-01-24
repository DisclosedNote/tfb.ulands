package foss.tfb.ulands.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.net.Network.RegisterName;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GameServer
{
    protected ChatManager chatManager;
    protected Server server;
    protected boolean isStarted = false;
    protected int port = Network.DEFAULT_GAME_PORT;
    protected String ip = Network.DEFAULT_IP_ADDRESS;
    protected Listener listener;

    public GameServer()
    {

        this.server = new Server(){
            protected Connection newConnection () {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new GameConnection();
            }
        };

        Network.register(server);

        this.chatManager = new ChatManager(this);

        this.listener = new Listener() {
            public void received (Connection c, Object object) {
                // We know all connections for this server are actually ChatConnections.
                GameConnection connection = (GameConnection)c;

                if (object instanceof RegisterName) {
                    // Validate object (if sent from hacker)
                    if (connection.name != null) return;

                    // Ignore the object if the name is invalid.
                    String name = ((RegisterName)object).name;
                    if (name == null) return;
                    name = name.trim();
                    if (name.length() == 0) return;

                    // Store the name on the connection.
                    connection.name = name;

                    chatManager.sendConnected(connection);

                    // Send everyone a new list of connection names.
//                    updateNames();
                    return;
                }

                /*
                if (object instanceof ChatMessage) {
                    // Ignore the object if a client tries to chat before registering a name.
                    if (connection.name == null) return;
                    ChatMessage chatMessage = (ChatMessage)object;
                    // Ignore the object if the chat message is invalid.
                    String message = chatMessage.text;
                    if (message == null) return;
                    message = message.trim();
                    if (message.length() == 0) return;
                    // Prepend the connection's name and send to everyone.
                    chatMessage.text = connection.name + ": " + message;
                    server.sendToAllTCP(chatMessage);
                    return;
                }
                 */
            }

            public void disconnected (Connection c) {
                GameConnection connection = (GameConnection) c;
                if (connection.name != null) {
                    // Announce to everyone that someone (with a registered name) has left.
                    chatManager.sendDisconnected(connection);

//                    updateNames();
                }
            }
        };

        this.server.addListener(this.listener);

    }

    public void start()
    {
        if(isStarted) return;

        try {
            server.start();
            server.bind(this.port);
            isStarted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        if(!isStarted) return;
        server.stop();
    }

    public boolean isStarted()
    {
        return isStarted;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        if(port > 65535 || port <= 0)
            port = Network.DEFAULT_GAME_PORT;
        this.port = port;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIPPort(String ip, int port) throws IOException
    {
        this.server.bind(new InetSocketAddress(ip, port), null);
    }

    public void addListener(Listener listener)
    {
        this.server.addListener(listener);
    }

    public void removeListener(Listener listener)
    {
        this.server.removeListener(listener);
    }

    public Server getServer()
    {
        return server;
    }

    static public class Broadcast {
        Network.Package data;
    }

    static public class BroadcastToAll extends Broadcast {

    }

    static public class BroadcastTo extends Broadcast {
        int sendTo;

        public BroadcastTo(int sendTo)
        {
            this.sendTo = sendTo;
        }
    }

    static public class BroadcastToAllExcept extends Broadcast {
        int sendExcept;

        public BroadcastToAllExcept(int sendExcept)
        {
            this.sendExcept = sendExcept;
        }
    }

    public void send(Broadcast type)
    {
        if(type instanceof BroadcastTo)
        {
            this.server.sendToTCP(((BroadcastTo) type).sendTo, type.data);
            return;
        }

        if(type instanceof BroadcastToAllExcept)
        {
            this.server.sendToAllExceptTCP(((BroadcastToAllExcept) type).sendExcept, type.data);
            return;
        }

        if(type instanceof BroadcastToAll)
        {
            this.server.sendToAllTCP(type.data);
            return;
        }
    }
}
