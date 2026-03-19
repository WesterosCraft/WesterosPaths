package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.data.config.shared.ChapterData;
import space.ajcool.westerospaths.core.data.config.shared.PathData;
import space.ajcool.westerospaths.core.networking.packets.server.ChapterUpdatePacket;

public class ChapterUpdateHandler {

    public void send(ChapterUpdatePacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(ChapterUpdatePacket payload, ServerPlayNetworking.Context context) {
        final String pathId = payload.pathId();
        final PathData pathData = WesterosPaths.CONFIG.getPath(pathId);
        if (pathData == null) {
            return;
        }

        final String chapterId = payload.chapterId();
        final String chapterName = payload.chapterName();
        final String chapterDate = payload.chapterDate();
        final int chapterIndex = payload.chapterIndex();
        final String warp = payload.warp();
        final ChapterData chapterData = new ChapterData(chapterId, chapterName, chapterDate, chapterIndex, warp);

        pathData.setChapter(chapterData);
        WesterosPaths.CONFIG_MANAGER.save();
    }
}
