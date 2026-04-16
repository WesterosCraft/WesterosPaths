package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import space.ajcool.westerospaths.core.data.config.shared.ChapterData;

public record ChapterUpdatePacket(
        String pathId,
        String chapterId,
        String chapterName,
        String chapterDate,
        int chapterIndex,
        String warp,
        String book
) implements CustomPayload {

    public ChapterUpdatePacket(String pathId, ChapterData chapter) {
        this(pathId, chapter.getId(), chapter.getName(), chapter.getDate(), chapter.getIndex(), chapter.getWarp(), chapter.getBook());
    }

    public static final CustomPayload.Id<ChapterUpdatePacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "path_chapter_update"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterUpdatePacket> CODEC =
            PacketCodec.of(
                    (value, buf) -> {
                        buf.writeString(value.pathId());
                        buf.writeString(value.chapterId());
                        buf.writeString(value.chapterName());
                        buf.writeString(value.chapterDate());
                        buf.writeInt(value.chapterIndex());
                        buf.writeString(value.warp());
                        buf.writeString(value.book());
                    },
                    buf -> new ChapterUpdatePacket(
                            buf.readString(),
                            buf.readString(),
                            buf.readString(),
                            buf.readString(),
                            buf.readInt(),
                            buf.readString(),
                            buf.readString()
                    )
            );

    @Override
    public CustomPayload.Id<ChapterUpdatePacket> getId() {
        return ID;
    }
}
