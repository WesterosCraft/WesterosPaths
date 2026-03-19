package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.networking.packets.server.ChapterStartRemovePacket;

public class ChapterStartRemoveHandler {

    public void send(ChapterStartRemovePacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(ChapterStartRemovePacket payload, ServerPlayNetworking.Context context) {
        final String pathId = payload.pathId();
        final String chapterId = payload.chapterId();
        WesterosPaths.CONFIG.removeChapterStart(pathId, chapterId);
        WesterosPaths.CONFIG_MANAGER.save();
    }
}
