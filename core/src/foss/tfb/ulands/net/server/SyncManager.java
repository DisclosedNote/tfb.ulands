package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.BaseObject;
import foss.tfb.ulands.stage.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class SyncManager extends Manager
{
    public SyncManager(GameServer server)
    {
        super(server);
    }

    /* Sync intention */

    public class SyncIntention {

        protected ArrayList<BaseObject> objectsToSync = new ArrayList<>();
        protected ArrayList<String> fieldsToSync = new ArrayList<>();
        protected SyncCondition condition;

        public SyncIntention(BaseObject object, String ...fields)
        {
            objectsToSync.add(object);
            fieldsToSync.addAll(Arrays.asList(fields));
        }

        public SyncIntention(String field, BaseObject ...objects)
        {
            fieldsToSync.add(field);
            objectsToSync.addAll(Arrays.asList(objects));
        }

        public SyncIntention()
        {
        }

        public ArrayList<BaseObject> getObjectsToSync()
        {
            return objectsToSync;
        }

        public void addObjectToSync(BaseObject objectToSync)
        {
            this.objectsToSync.add(objectToSync);
        }

        public ArrayList<String> getFieldsToSync()
        {
            return fieldsToSync;
        }

        public void addFieldToSync(String fieldToSync)
        {
            this.fieldsToSync.add(fieldToSync);
        }

        public SyncCondition getCondition()
        {
            return condition;
        }

        public void setCondition(SyncCondition condition)
        {
            this.condition = condition;
        }

        public void syncTo(Player ...players)
        {
            for(Player player : players)
                for(BaseObject object : objectsToSync)
                    if(condition.shouldSyncTo(object, player))
                    {
                        Network.SyncablePart<?>[] parts = new Network.SyncablePart<?>[fieldsToSync.size()];

                        int i = 0;
                        for(String field : fieldsToSync){
                            try {
                                parts[i] = new Network.SyncablePart<>(object.id, field, object.getClass().getField(field));
                            } catch (NoSuchFieldException e) {
                                System.out.println("Sync error.");
                                e.printStackTrace();
                            }
                            i++;
                        }

                        Network.SyncPartsPackage syncPartsPackage = new Network.SyncPartsPackage(parts);
                        SyncManager.this.server.playerManager.sendPackage(player, syncPartsPackage);
                    }

        }

    }

    /* SyncConditions */

    public abstract class SyncCondition {
        abstract public boolean shouldSyncTo(BaseObject syncing, Player current);
    }

    public class NearSyncCondition extends SyncCondition {
        @Override
        public boolean shouldSyncTo(BaseObject syncing, Player current)
        {
            // TODO: NearSyncCondition
            return true;
        }
    }



}
