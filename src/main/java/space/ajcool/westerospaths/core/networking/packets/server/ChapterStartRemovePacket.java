package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChapterStartRemovePacket(String pathId, String chapterId) implements CustomPayload {

    public static final CustomPayload.Id<ChapterStartRemovePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "chapter_start_remove"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterStartRemovePacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ChapterStartRemovePacket::pathId,
                    PacketCodecs.STRING, ChapterStartRemovePacket::chapterId,
                    ChapterStartRemovePacket::new
            );

    @Override
    public CustomPayload.Id<ChapterStartRemovePacket> getId() {
        return ID;
    }
}
