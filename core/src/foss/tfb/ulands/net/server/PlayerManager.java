package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.Player;

import java.util.ArrayList;

public class PlayerManager extends Manager
{

    protected ArrayList<Player> players = new ArrayList<>();

    public PlayerManager(GameServer server)
    {
        super(server);
    }

    public Player initializePlayer(GameConnection connection)
    {
        Player player = new Player(connection);

        players.add(player);

        return player;
    }

    public void removePlayer(Player player)
    {
        // TODO: call Player dispose()
        players.remove(player);
    }


    /* Network */

    public void sendPackage(Player player, Network.Package data)
    {
        ArrayList<Player> tmp = new ArrayList<>();
        tmp.add(player);
        sendPackage(tmp, data);
    }


    public void sendPackage(ArrayList<Player> players, Network.Package data)
    {
        for(Player player : players)
        {
            GameConnection gameConnection = player.getConnection();

            GameServer.BroadcastTo broadcast = new GameServer.BroadcastTo(gameConnection.getID());
            broadcast.data = data;

            server.send(broadcast);
        }
    }

    // TODO: sendExcept player(s)

    /* Network: Specific packages */

    public void sendAuthorizedStatus(Player player)
    {
        boolean isAuthorized = player.isAuthorized();
        Network.AuthorizeStatus status = new Network.AuthorizeStatus();
        status.authorized = isAuthorized;
        sendPackage(player, status);
    }


    @Override
    public void think()
    {

    }
}
