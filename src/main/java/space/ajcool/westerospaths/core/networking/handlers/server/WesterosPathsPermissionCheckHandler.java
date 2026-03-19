package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import space.ajcool.westerospaths.core.PermissionHelper;
import space.ajcool.westerospaths.core.networking.packets.client.WesterosPathsPermissionCheckResponsePacket;
import space.ajcool.westerospaths.core.networking.packets.server.PermissionCheckRequestPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class WesterosPathsPermissionCheckHandler {

    private final Map<UUID, Consumer<WesterosPathsPermissionCheckResponsePacket>> responseConsumers = new HashMap<>();

    public void send(Consumer<WesterosPathsPermissionCheckResponsePacket> consumer) {
        UUID requestId = UUID.randomUUID();
        responseConsumers.put(requestId, consumer);
        ClientPlayNetworking.send(new PermissionCheckRequestPayload(requestId));
    }

    public void receiveOnServer(PermissionCheckRequestPayload payload, ServerPlayNetworking.Context context) {
        boolean hasPerm = PermissionHelper.hasEditPermission(context.player());
        context.responseSender().sendPacket(
                new WesterosPathsPermissionCheckResponsePacket(payload.requestId(), hasPerm)
        );
    }

    public void receiveOnClient(WesterosPathsPermissionCheckResponsePacket payload, ClientPlayNetworking.Context context) {
        Consumer<WesterosPathsPermissionCheckResponsePacket> consumer = responseConsumers.remove(payload.requestId());
        if (consumer != null) consumer.accept(payload);
    }
}
