package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.Player;

import java.util.ArrayList;

public class PlayerManager extends Manager
{

    private ArrayList<Player> players = new ArrayList<>();

    public PlayerManager(GameServer server)
    {
        super(server);
    }

    synchronized public Player initializePlayer(GameConnection connection)
    {
        Player player = new Player(connection);

        players.add(player);

        return player;
    }

    synchronized public void removePlayer(Player player)
    {
        // TODO: call Player dispose()
        players.remove(player);
    }

    synchronized public ArrayList<Player> getPlayers()
    {
        return players;
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

            GameServer.BroadcastTo broadcast = server.prepareBroadcastTo();
            broadcast.data = data;
            broadcast.sendTo = gameConnection.getID();
            broadcast.send();
        }
    }

    // TODO: sendExcept player(s)

    /* Network: Specific packages */

    public void sendAuthorizedStatus(Player player)
    {
        boolean isAuthorized = player.isAuthorized();
        Network.AuthorizeStatusPackage status = new Network.AuthorizeStatusPackage();
        status.authorized = isAuthorized;
        sendPackage(player, status);
    }


    @Override
    public void think()
    {

    }

}
