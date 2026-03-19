package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChapterDeletePacket(
        String pathId,
        String chapterId
) implements CustomPayload {

    public static final CustomPayload.Id<ChapterDeletePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_chapter_delete"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterDeletePacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ChapterDeletePacket::pathId,
                    PacketCodecs.STRING, ChapterDeletePacket::chapterId,
                    ChapterDeletePacket::new
            );

    @Override
    public CustomPayload.Id<ChapterDeletePacket> getId() {
        return ID;
    }
}
