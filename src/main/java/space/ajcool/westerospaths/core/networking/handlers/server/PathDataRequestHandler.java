package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.data.Json;
import space.ajcool.westerospaths.core.networking.packets.client.PathDataResponsePacket;
import space.ajcool.westerospaths.core.networking.packets.server.PathDataRequestPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PathDataRequestHandler {

    private final Map<UUID, Consumer<PathDataResponsePacket>> responseConsumers = new HashMap<>();

    public void send(Consumer<PathDataResponsePacket> consumer) {
        UUID requestId = UUID.randomUUID();
        responseConsumers.put(requestId, consumer);
        ClientPlayNetworking.send(new PathDataRequestPayload(requestId));
    }

    public void receiveOnServer(PathDataRequestPayload payload, ServerPlayNetworking.Context context) {
        String json = Json.toJson(WesterosPaths.CONFIG.getPaths());
        context.responseSender().sendPacket(
                new PathDataResponsePacket(payload.requestId(), json)
        );
    }

    public void receiveOnClient(PathDataResponsePacket payload, ClientPlayNetworking.Context context) {
        Consumer<PathDataResponsePacket> consumer = responseConsumers.remove(payload.requestId());
        if (consumer != null) consumer.accept(payload);
    }
}
