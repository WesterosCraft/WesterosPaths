package space.ajcool.westerospaths.core.networking.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChapterPlayerTeleportPacket(String pathId, String chapterId) implements CustomPayload {

    public static final CustomPayload.Id<ChapterPlayerTeleportPacket> ID =
            new CustomPayload.Id<>(Identifier.of("westerospaths", "chapter_player_teleport"));

    public static final PacketCodec<? super RegistryByteBuf, ChapterPlayerTeleportPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ChapterPlayerTeleportPacket::pathId,
                    PacketCodecs.STRING, ChapterPlayerTeleportPacket::chapterId,
                    ChapterPlayerTeleportPacket::new
            );

    @Override
    public CustomPayload.Id<ChapterPlayerTeleportPacket> getId() {
        return ID;
    }
}
