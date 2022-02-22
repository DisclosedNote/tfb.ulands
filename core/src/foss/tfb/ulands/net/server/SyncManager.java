package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.Player;

import java.util.ArrayList;

public class SyncManager extends Manager
{
    public SyncManager(GameServer server)
    {
        super(server);
    }

    /**
     * Sync independent data fields.
     * @param players Target players
     * @param syncPackage {@link Network.SyncPackage}s to be sent
     */
    public void syncTo(ArrayList<Player> players, Network.SyncPackage syncPackage)
    {
        for(Player player : players){
            GameConnection c = player.getConnection();
            if(c == null) continue;

            GameServer.BroadcastTo bc = server.prepareBroadcastTo();
            bc.sendTo = c.getID();
            bc.data = syncPackage;
            bc.send();
        }
    }

    public void syncTo(Player player, Network.SyncPackage syncPackage)
    {
        ArrayList<Player> a = new ArrayList<>();
        a.add(player);
        syncTo(a, syncPackage);
    }

    public void syncToAll(Network.SyncPackage syncPackage)
    {
        syncTo(server.playerManager.getPlayers(), syncPackage);
    }



}
