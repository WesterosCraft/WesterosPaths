package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record ChapterStartUpdatePacket(String pathId, String chapterId, BlockPos position) implements CustomPayload {

    public static final CustomPayload.Id<ChapterStartUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_chapter_start_update"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterStartUpdatePacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ChapterStartUpdatePacket::pathId,
                    PacketCodecs.STRING, ChapterStartUpdatePacket::chapterId,
                    BlockPos.PACKET_CODEC, ChapterStartUpdatePacket::position,
                    ChapterStartUpdatePacket::new
            );

    @Override
    public CustomPayload.Id<ChapterStartUpdatePacket> getId() {
        return ID;
    }
}
