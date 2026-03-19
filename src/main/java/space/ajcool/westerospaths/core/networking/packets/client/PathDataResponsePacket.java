package space.ajcool.westerospaths.core.networking.packets.client;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record PathDataResponsePacket(UUID requestId, String json) implements CustomPayload {

    public static final CustomPayload.Id<PathDataResponsePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_data_response"));

    public static final PacketCodec<? super RegistryByteBuf, PathDataResponsePacket> CODEC =
            PacketCodec.tuple(
                    Uuids.PACKET_CODEC, PathDataResponsePacket::requestId,
                    PacketCodecs.STRING, PathDataResponsePacket::json,
                    PathDataResponsePacket::new
            );

    @Override
    public CustomPayload.Id<PathDataResponsePacket> getId() {
        return ID;
    }
}
