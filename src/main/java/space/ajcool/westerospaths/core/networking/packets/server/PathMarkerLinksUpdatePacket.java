package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record PathMarkerLinksUpdatePacket(BlockPos position, NbtCompound data) implements CustomPayload {

    public static final CustomPayload.Id<PathMarkerLinksUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_marker_links_update"));

    public static final PacketCodec<? super RegistryByteBuf, PathMarkerLinksUpdatePacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, PathMarkerLinksUpdatePacket::position,
                    PacketCodecs.NBT_COMPOUND, PathMarkerLinksUpdatePacket::data,
                    PathMarkerLinksUpdatePacket::new
            );

    @Override
    public CustomPayload.Id<PathMarkerLinksUpdatePacket> getId() {
        return ID;
    }
}
