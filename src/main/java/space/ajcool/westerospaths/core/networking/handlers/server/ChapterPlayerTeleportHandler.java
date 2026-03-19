package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.executors.WarpExecutor;
import space.ajcool.westerospaths.core.networking.packets.server.ChapterPlayerTeleportPacket;

import java.util.Optional;

public class ChapterPlayerTeleportHandler {

    public void send(ChapterPlayerTeleportPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(ChapterPlayerTeleportPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        MinecraftServer server = context.server();

        server.execute(() -> {
            final String pathId = payload.pathId();
            final String chapterId = payload.chapterId();

            final Optional<String> startWarp = WesterosPaths.CONFIG.getChapterStartWarp(pathId, chapterId);

            if (startWarp.isPresent() && FabricLoader.getInstance().isModLoaded("huskhomes")) {
                WesterosPaths.LOGGER.info("Attempting to warp player {} at {}", player.getUuidAsString(), startWarp.get());
                WarpExecutor warpExecutor = new WarpExecutor();
                warpExecutor.warpTo(player, startWarp.get());
            } else {
                final BlockPos start = WesterosPaths.CONFIG.getChapterStartCoordinates(pathId, chapterId);

                if (start != null) {
                    player.requestTeleport(start.getX() + 0.5, start.getY(), start.getZ() + 0.5);
                }
            }
        });
    }
}
