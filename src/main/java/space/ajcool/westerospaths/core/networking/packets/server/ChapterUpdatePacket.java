package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import space.ajcool.westerospaths.core.data.config.shared.ChapterData;

public record ChapterUpdatePacket(
        String pathId,
        String chapterId,
        String chapterName,
        String chapterDate,
        int chapterIndex,
        String warp
) implements CustomPayload {

    public ChapterUpdatePacket(String pathId, ChapterData chapter) {
        this(pathId, chapter.getId(), chapter.getName(), chapter.getDate(), chapter.getIndex(), chapter.getWarp());
    }

    public static final CustomPayload.Id<ChapterUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_chapter_update"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterUpdatePacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ChapterUpdatePacket::pathId,
                    PacketCodecs.STRING, ChapterUpdatePacket::chapterId,
                    PacketCodecs.STRING, ChapterUpdatePacket::chapterName,
                    PacketCodecs.STRING, ChapterUpdatePacket::chapterDate,
                    PacketCodecs.INTEGER, ChapterUpdatePacket::chapterIndex,
                    PacketCodecs.STRING, ChapterUpdatePacket::warp,
                    ChapterUpdatePacket::new
            );

    @Override
    public CustomPayload.Id<ChapterUpdatePacket> getId() {
        return ID;
    }
}
