package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.data.config.shared.Color;
import space.ajcool.westerospaths.core.data.config.shared.PathData;
import space.ajcool.westerospaths.core.networking.packets.server.PathDataUpdatePacket;

public class PathDataUpdateRequestHandler {

    public void send(PathDataUpdatePacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(PathDataUpdatePacket payload, ServerPlayNetworking.Context context) {
        final String pathId = payload.id();
        final String name = payload.name();
        final int primaryColor = payload.primaryColor();
        final int secondaryColor = payload.secondaryColor();
        final int tertiaryColor = payload.tertiaryColor();

        PathData pathData = WesterosPaths.CONFIG.getPath(pathId);

        if (pathData != null) {
            WesterosPaths.LOGGER.info("Updating path data for path ID: {}, name {}", pathId, name);

            pathData.setName(name);
            pathData.setPrimaryColor(Color.fromHex(primaryColor));
            pathData.setSecondaryColor(Color.fromHex(secondaryColor));
            pathData.setTertiaryColor(Color.fromHex(tertiaryColor));

            WesterosPaths.CONFIG_MANAGER.save();
        } else {
            WesterosPaths.LOGGER.warn("No path found with ID: {}", pathId);
        }
    }
}
