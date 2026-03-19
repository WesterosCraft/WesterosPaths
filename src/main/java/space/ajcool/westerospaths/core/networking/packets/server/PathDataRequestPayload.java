package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record PathDataRequestPayload(UUID requestId) implements CustomPayload {

    public static final CustomPayload.Id<PathDataRequestPayload> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_data_request"));

    public static final PacketCodec<? super RegistryByteBuf, PathDataRequestPayload> CODEC =
            PacketCodec.tuple(
                    Uuids.PACKET_CODEC, PathDataRequestPayload::requestId,
                    PathDataRequestPayload::new
            );

    @Override
    public CustomPayload.Id<PathDataRequestPayload> getId() {
        return ID;
    }
}
