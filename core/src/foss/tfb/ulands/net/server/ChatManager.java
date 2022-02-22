package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network.ChatMessagePackage;
import foss.tfb.ulands.net.server.listener.ChatManagerListener;
import foss.tfb.ulands.net.validation.StringValidator;
import foss.tfb.ulands.stage.Player;

import java.util.ArrayList;

public class ChatManager extends Manager
{
    protected ArrayList<ChatManagerListener> listeners = new ArrayList<>();

    public ChatManager(GameServer server)
    {
        super(server);
    }

    // TODO: message listeners

    protected ChatMessagePackage formMessage(String chatMessage)
    {
        ChatMessagePackage message = new ChatMessagePackage();
        message.text = chatMessage;
        return message;
    }

    // TODO: turn send by Broadcast to send by Player (?)

    public void sendChatMessage(GameServer.Broadcast broadcast, String chatMessage)
    {
        for(ChatManagerListener listener : listeners)
        {
            listener.onChatMessageSent(broadcast, chatMessage);
            // TODO: prevent from sending if listener returns false
        }

        broadcast.data = formMessage(chatMessage);
        broadcast.send();
    }

    public void sendConnected(GameConnection connection)
    {
        Player player = connection.getAssociatedPlayer();
        sendChatMessage(server.prepareBroadcastToAll(),
                "Player '" + player.getUsername() + "' connected.");
    }

    public void sendDisconnected(GameConnection connection)
    {
        Player player = connection.getAssociatedPlayer();
        sendChatMessage(server.prepareBroadcastToAll(),
                "Player '" + player.getUsername() + "' disconnected.");
    }

    public void sendPublicPlayerChatMessage(Player player, String message)
    {
        sendChatMessage(server.prepareBroadcastToAll(),
                player.getUsername() + ": " + message);
    }

    /* Listeners */
    public boolean addListener(ChatManagerListener listener)
    {
        return this.listeners.add(listener);
    }

    public boolean removeListener(ChatManagerListener listener)
    {
        return this.listeners.remove(listener);
    }

    /* Network */

    @Override
    public void act(GameConnection connection, Object object)
    {
        Player player = connection.getAssociatedPlayer();
        if(player.isAuthorized())
        {

            if(object instanceof ChatMessagePackage)
            {
                ChatMessagePackage message = (ChatMessagePackage) object;
                String text = message.text;

                StringValidator validator = new StringValidator(text);
                if(!validator.validateText())
                    return;

                /*
                // Broadcast example:
                GameServer.Broadcast broadcast = server.prepareBroadcastToAll();
                broadcast.data = new Network.SyncPackage(
                        new Network.Syncable<>(1, "width", 3),
                        new Network.Syncable<>(1, "length", "asdaskdoqkwodqkwo"),
                        new Network.Syncable<>(1, "height", new int[]{2,5,1,6,1,2,6})
                );
                broadcast.send();
                */

                sendPublicPlayerChatMessage(player, message.text);
                return;
            }


        }
    }

    public void think()
    {

    }
}
