package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PathDataUpdatePacket(
        String id,
        String name,
        int primaryColor,
        int secondaryColor,
        int tertiaryColor
) implements CustomPayload {

    public static final CustomPayload.Id<PathDataUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_data_update_request"));

    public static final PacketCodec<? super RegistryByteBuf, PathDataUpdatePacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, PathDataUpdatePacket::id,
                    PacketCodecs.STRING, PathDataUpdatePacket::name,
                    PacketCodecs.INTEGER, PathDataUpdatePacket::primaryColor,
                    PacketCodecs.INTEGER, PathDataUpdatePacket::secondaryColor,
                    PacketCodecs.INTEGER, PathDataUpdatePacket::tertiaryColor,
                    PathDataUpdatePacket::new
            );

    @Override
    public CustomPayload.Id<PathDataUpdatePacket> getId() {
        return ID;
    }
}
