package space.ajcool.westerospaths.core.networking.packets.client;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record WesterosPathsPermissionCheckResponsePacket(UUID requestId, boolean hasPermission) implements CustomPayload {

    public static final CustomPayload.Id<WesterosPathsPermissionCheckResponsePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "westerospaths_permission_check_response"));

    public static final PacketCodec<? super RegistryByteBuf, WesterosPathsPermissionCheckResponsePacket> CODEC =
            PacketCodec.tuple(
                    Uuids.PACKET_CODEC, WesterosPathsPermissionCheckResponsePacket::requestId,
                    PacketCodecs.BOOL, WesterosPathsPermissionCheckResponsePacket::hasPermission,
                    WesterosPathsPermissionCheckResponsePacket::new
            );

    @Override
    public CustomPayload.Id<WesterosPathsPermissionCheckResponsePacket> getId() {
        return ID;
    }
}
