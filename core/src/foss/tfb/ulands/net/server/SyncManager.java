package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.stage.BaseObject;
import foss.tfb.ulands.stage.Player;

import java.util.Arrays;

public class SyncManager extends Manager
{
    public SyncManager(GameServer server)
    {
        super(server);
    }

    /* Sync intention */

    public class SyncIntention {

        protected BaseObject[] objectsToSync;
        protected String[] fieldsToSync;
        protected SyncCondition condition;

        public SyncIntention(BaseObject object, String ...fields)
        {
            objectsToSync = new BaseObject[]{ object };
            fieldsToSync = fields;
        }

        public SyncIntention(String field, BaseObject ...objects)
        {
            fieldsToSync = new String[]{ field };
            objectsToSync = objects;
        }

        public SyncIntention(BaseObject[] objectsToSync, String[] fieldsToSync)
        {
            this.objectsToSync = Arrays.copyOf(objectsToSync, objectsToSync.length);
            this.fieldsToSync = Arrays.copyOf(fieldsToSync, fieldsToSync.length);
        }

        public BaseObject[] getObjectsToSync() {
            return objectsToSync;
        }

        public String[] getFieldsToSync() {
            return fieldsToSync;
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
                        Network.SyncablePart<?>[] parts = new Network.SyncablePart<?>[fieldsToSync.length];

                        int i = 0;
                        for(String field : fieldsToSync){
                            try {
                                parts[i] = new Network.SyncablePart<>(object.id, field, object.getClass().getField(field));
                            } catch (NoSuchFieldException e) {
                                System.out.println("[!!! Sync error !!!]");
                                e.printStackTrace();
                            }
                            i++;
                        }

                        Network.SyncPartsPackage syncPartsPackage = new Network.SyncPartsPackage(parts);
                        SyncManager.this.server.playerManager.sendPackage(player, syncPartsPackage);
                    }

        }

    }

    /* Sync conditions */

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
