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
     * @param syncPartsPackage {@link Network.SyncPartsPackage}s to be sent
     */
    public void syncTo(ArrayList<Player> players, Network.SyncPartsPackage syncPartsPackage)
    {
        for(Player player : players){
            GameConnection c = player.getConnection();
            if(c == null) continue;

            GameServer.BroadcastTo bc = server.prepareBroadcastTo();
            bc.sendTo = c.getID();
            bc.data = syncPartsPackage;
            bc.send();
        }
    }

    public void syncTo(Player player, Network.SyncPartsPackage syncPartsPackage)
    {
        ArrayList<Player> a = new ArrayList<>();
        a.add(player);
        syncTo(a, syncPartsPackage);
    }

    public void syncToAll(Network.SyncPartsPackage syncPartsPackage)
    {
        syncTo(server.playerManager.getPlayers(), syncPartsPackage);
    }

}
