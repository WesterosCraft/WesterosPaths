package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record PermissionCheckRequestPayload(UUID requestId) implements CustomPayload {

    public static final CustomPayload.Id<PermissionCheckRequestPayload> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "westerospaths_permission_check_request"));

    public static final PacketCodec<? super RegistryByteBuf, PermissionCheckRequestPayload> CODEC =
            PacketCodec.tuple(
                    Uuids.PACKET_CODEC, PermissionCheckRequestPayload::requestId,
                    PermissionCheckRequestPayload::new
            );

    @Override
    public CustomPayload.Id<PermissionCheckRequestPayload> getId() {
        return ID;
    }
}
