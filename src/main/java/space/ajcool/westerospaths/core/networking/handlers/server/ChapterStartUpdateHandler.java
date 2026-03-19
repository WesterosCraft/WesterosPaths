package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.math.BlockPos;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.data.config.server.PositionData;
import space.ajcool.westerospaths.core.networking.packets.server.ChapterStartUpdatePacket;

public class ChapterStartUpdateHandler {

    public void send(ChapterStartUpdatePacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(ChapterStartUpdatePacket payload, ServerPlayNetworking.Context context) {
        final String pathId = payload.pathId();
        final String chapterId = payload.chapterId();
        final BlockPos start = payload.position();
        WesterosPaths.CONFIG.setChapterStart(pathId, chapterId, PositionData.fromBlockPos(start));
        WesterosPaths.CONFIG_MANAGER.save();
    }
}
