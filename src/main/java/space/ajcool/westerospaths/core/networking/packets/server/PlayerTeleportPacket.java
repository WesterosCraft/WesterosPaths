package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PlayerTeleportPacket(double x, double y, double z, Identifier worldId) implements CustomPayload {

    public static final CustomPayload.Id<PlayerTeleportPacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "player_teleport"));

    public static final PacketCodec<? super RegistryByteBuf, PlayerTeleportPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.DOUBLE, PlayerTeleportPacket::x,
                    PacketCodecs.DOUBLE, PlayerTeleportPacket::y,
                    PacketCodecs.DOUBLE, PlayerTeleportPacket::z,
                    Identifier.PACKET_CODEC, PlayerTeleportPacket::worldId,
                    PlayerTeleportPacket::new
            );

    @Override
    public CustomPayload.Id<PlayerTeleportPacket> getId() {
        return ID;
    }
}
