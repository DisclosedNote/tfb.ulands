package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network.ChatMessage;
import foss.tfb.ulands.stage.Player;

public class ChatManager extends Manager
{

    public ChatManager(GameServer server)
    {
        super(server);
    }

    // TODO: message listeners

    protected ChatMessage formMessage(String chatMessage)
    {
        ChatMessage message = new ChatMessage();
        message.text = chatMessage;
        return message;
    }

    // TODO: turn send by Broadcast to send by Player (?)

    public void sendChatMessage(GameServer.Broadcast broadcast, String chatMessage)
    {
        broadcast.data = formMessage(chatMessage);
        this.server.send(broadcast);
    }

    public void sendConnected(GameConnection connection)
    {
        Player player = connection.getAssociatedPlayer();
        sendChatMessage(new GameServer.BroadcastToAll(),
                "Player '" + player.getUsername() + "' connected.");
    }

    public void sendDisconnected(GameConnection connection)
    {
        Player player = connection.getAssociatedPlayer();
        sendChatMessage(new GameServer.BroadcastToAll(),
                "Player '" + player.getUsername() + "' disconnected.");
    }

}
