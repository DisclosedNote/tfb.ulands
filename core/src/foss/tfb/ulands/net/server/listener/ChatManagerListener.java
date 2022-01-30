package foss.tfb.ulands.net.server.listener;

import foss.tfb.ulands.net.server.GameServer;

public interface ChatManagerListener
{
    void onChatMessageSent(GameServer.Broadcast broadcast, String chatMessage);
}
