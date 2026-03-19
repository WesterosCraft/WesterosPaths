package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record PathMarkerUpdatePacket(BlockPos position, NbtCompound data) implements CustomPayload {

    public static final CustomPayload.Id<PathMarkerUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_marker_update"));

    public static final PacketCodec<? super RegistryByteBuf, PathMarkerUpdatePacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, PathMarkerUpdatePacket::position,
                    PacketCodecs.NBT_COMPOUND, PathMarkerUpdatePacket::data,
                    PathMarkerUpdatePacket::new
            );

    @Override
    public CustomPayload.Id<PathMarkerUpdatePacket> getId() {
        return ID;
    }
}
