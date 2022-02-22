package foss.tfb.ulands.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.net.Network.AuthorizePackage;
import foss.tfb.ulands.stage.Player;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GameServer
{

    // TODO: server dispose

    protected ChatManager chatManager;
    protected MapManager mapManager;
    protected PlayerManager playerManager;
    protected SyncManager syncManager;

    protected Server server;
    protected boolean isStarted = false;
    protected int port = Network.DEFAULT_GAME_PORT;
    protected String ip = Network.DEFAULT_IP_ADDRESS;
    protected Thread logicThread;

    public GameServer()
    {

        /* Server instance */
        server = new Server()
        {
            protected Connection newConnection ()
            {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                GameConnection gameConnection = new GameConnection();
                gameConnection.setAssociatedPlayer(playerManager.initializePlayer(gameConnection));
                return gameConnection;
            }
        };

        /* Network events listener*/
        server.addListener(new Listener(){
            @Override
            public void connected(Connection connection)
            {
                GameServer.this.connected(connection);
            }

            @Override
            public void disconnected(Connection connection)
            {
                GameServer.this.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o)
            {
                GameServer.this.received(connection, o);
            }

            @Override
            public void idle(Connection connection)
            {
                GameServer.this.idle(connection);
            }
        });

        Network.register(server);

        /* Managers */
        chatManager = new ChatManager(this);
        mapManager = new MapManager(this);
        playerManager = new PlayerManager(this);
        syncManager = new SyncManager(this);

        /* Logic thread */
        logicThread = new Thread(GameServer.this::think);
    }

    /* Managers */

    public ChatManager getChatManager()
    {
        return chatManager;
    }

    public MapManager getMapManager()
    {
        return mapManager;
    }

    public PlayerManager getPlayerManager()
    {
        return playerManager;
    }

    /* Basic logic */

    public void start()
    {
        if(isStarted) return;

        if(logicThread.isAlive())
            logicThread.interrupt();

        try {
            server.start();
            server.bind(this.port);
            logicThread.start();
            isStarted = true;
        } catch (IOException e) {
            // TODO: better error message
            e.printStackTrace();
        }
    }

    public void stop()
    {
        if(!isStarted) return;
        // if(logicThread.isAlive())
            logicThread.interrupt();
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

    public Connection[] getConnections()
    {
        return this.server.getConnections();
    }

    public Server getServer()
    {
        return server;
    }

    /* Logic thread */
    protected void think()
    {
        while(true)
        {
            playerManager.think();
            chatManager.think();
            mapManager.think();
        }
    }

    /* Network */

    public void connected(Connection c)
    {
        GameConnection gameConnection = (GameConnection) c;

    }

    public void disconnected(Connection c)
    {
        GameConnection connection = (GameConnection) c;
        Player player = connection.getAssociatedPlayer();
        if (player.isAuthorized()) {
            // Announce to everyone that someone (with a registered name) has left.
            chatManager.sendDisconnected(connection);

            playerManager.removePlayer(player);

            // TODO: send player disconnect
        }
    }

    public void received(Connection c, Object object)
    {
        // We know all connections for this server are actually ChatConnections.
        GameConnection connection = (GameConnection)c;


        // TODO: move to appropriate manager
        if (object instanceof AuthorizePackage) {
            Player player = connection.getAssociatedPlayer();

            // Validate object (if sent from hacker)
            if (player.isAuthorized()) return;

            // Ignore the object if the name is invalid.
            String name = ((AuthorizePackage) object).name;
            if (name == null) return;
            name = name.trim();
            if (name.length() == 0) return;

            // Store the name on the connection.
            player.setUsername(name);
            player.setAuthorized(true);
            chatManager.sendConnected(connection);

            playerManager.sendAuthorizedStatus(player);

            // TODO: send player connect (send create Player instance)
            return;
        }

        chatManager.act(connection, object);

    }

    public void idle(Connection c)
    {

    }

    /**
     *  <h2>Broadcasts</h2>
     *  Low-level connections communication.
     *  Several classes describes data to be sent and direction (to all/specific connections).
     */

    public abstract class Broadcast {
        public Network.Package data = new Network.Package();
        protected GameServer gameServer;

        public Broadcast(GameServer gameServer)
        {
            this.gameServer = gameServer;
        }

        /**
         * Raw TCP-connection send method.
         * Use if low-level access is required (ex. send data to specific connection ID, not a player).
         * See {@link PlayerManager#sendPackage} for sending {@link Network.Package} to specific players.
         */
        abstract public void send();
    }

    public class BroadcastToAll extends Broadcast {

        public BroadcastToAll(GameServer gameServer)
        {
            super(gameServer);
        }

        @Override
        public void send()
        {
            GameServer.this.server.sendToAllTCP(this.data);
        }
    }

    public class BroadcastTo extends Broadcast {
        public int sendTo;

        public BroadcastTo(GameServer gameServer)
        {
            super(gameServer);
        }

        @Override
        public void send()
        {
            GameServer.this.server.sendToTCP(this.sendTo, this.data);
        }
    }

    public class BroadcastToAllExcept extends Broadcast {
        public int sendExcept;

        public BroadcastToAllExcept(GameServer gameServer)
        {
            super(gameServer);
        }

        @Override
        public void send()
        {
            GameServer.this.server.sendToAllExceptTCP(this.sendExcept, this.data);
        }
    }

    /**
     * <h3>Broadcast factories</h3>
     * Used for generating outgoing broadcast packages outside GameServer.
     */

    public GameServer.BroadcastToAll prepareBroadcastToAll()
    {
        return new BroadcastToAll(this);
    }

    public GameServer.BroadcastTo prepareBroadcastTo()
    {
        return new BroadcastTo(this);
    }

    public GameServer.BroadcastToAllExcept prepareBroadcastToAllExcept()
    {
        return new BroadcastToAllExcept(this);
    }
}
