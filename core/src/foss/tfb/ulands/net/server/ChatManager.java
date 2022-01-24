package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;
import foss.tfb.ulands.net.Network.ChatMessage;

public class ChatManager
{

    protected GameServer server;
    public ChatManager(GameServer server)
    {
        this.server = server;
    }

    protected ChatMessage formMessage(String chatMessage)
    {
        ChatMessage message = new ChatMessage();
        message.text = chatMessage;
        return message;
    }

    public void sendChatMessage(GameServer.Broadcast broadcast, String chatMessage)
    {
        broadcast.data = formMessage(chatMessage);
        this.server.send(broadcast);
    }

    public void sendConnected(GameConnection connection)
    {
        sendChatMessage(new GameServer.BroadcastToAllExcept(connection.getID()),
                "Player '" + connection.name + "' connected.");
    }

    public void sendDisconnected(GameConnection connection)
    {
        sendChatMessage(new GameServer.BroadcastToAllExcept(connection.getID()),
                "Player '" + connection.name + "' disconnected.");
    }

}
