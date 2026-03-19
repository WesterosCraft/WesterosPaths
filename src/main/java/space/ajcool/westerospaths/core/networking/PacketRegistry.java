package space.ajcool.westerospaths.core.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import space.ajcool.westerospaths.core.Fabric;
import space.ajcool.westerospaths.core.networking.handlers.server.*;
import space.ajcool.westerospaths.core.networking.packets.client.WesterosPathsPermissionCheckResponsePacket;
import space.ajcool.westerospaths.core.networking.packets.client.PathDataResponsePacket;
import space.ajcool.westerospaths.core.networking.packets.server.*;

public class PacketRegistry {

    public static final PlayerTeleportHandler PLAYER_TELEPORT = new PlayerTeleportHandler();
    public static final PathMarkerUpdateHandler PATH_MARKER_UPDATE = new PathMarkerUpdateHandler();
    public static final PathMarkerLinksUpdateHandler PATH_MARKER_LINKS_UPDATE = new PathMarkerLinksUpdateHandler();
    public static final PathDataRequestHandler PATH_DATA_REQUEST = new PathDataRequestHandler();
    public static final PathDataUpdateRequestHandler PATH_DATA_UPDATE_REQUEST = new PathDataUpdateRequestHandler();
    public static final ChapterUpdateHandler CHAPTER_UPDATE = new ChapterUpdateHandler();
    public static final ChapterDeleteHandler CHAPTER_DELETE = new ChapterDeleteHandler();
    public static final ChapterStartUpdateHandler CHAPTER_START_UPDATE = new ChapterStartUpdateHandler();
    public static final ChapterStartRemoveHandler CHAPTER_START_REMOVE = new ChapterStartRemoveHandler();
    public static final ChapterPlayerTeleportHandler CHAPTER_PLAYER_TELEPORT = new ChapterPlayerTeleportHandler();
    public static final WesterosPathsPermissionCheckHandler PERMISSION_CHECK = new WesterosPathsPermissionCheckHandler();

    public static void init() {
        // 1. Register C2S payload types
        PayloadTypeRegistry.playC2S().register(PlayerTeleportPacket.ID, PlayerTeleportPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PathMarkerUpdatePacket.ID, PathMarkerUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PathMarkerLinksUpdatePacket.ID, PathMarkerLinksUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PathDataUpdatePacket.ID, PathDataUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChapterUpdatePacket.ID, ChapterUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChapterDeletePacket.ID, ChapterDeletePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChapterStartUpdatePacket.ID, ChapterStartUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChapterStartRemovePacket.ID, ChapterStartRemovePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChapterPlayerTeleportPacket.ID, ChapterPlayerTeleportPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PermissionCheckRequestPayload.ID, PermissionCheckRequestPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PathDataRequestPayload.ID, PathDataRequestPayload.CODEC);

        // 2. Register S2C payload types
        PayloadTypeRegistry.playS2C().register(WesterosPathsPermissionCheckResponsePacket.ID, WesterosPathsPermissionCheckResponsePacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PathDataResponsePacket.ID, PathDataResponsePacket.CODEC);

        // 3. Register server-side receivers
        ServerPlayNetworking.registerGlobalReceiver(PlayerTeleportPacket.ID, PLAYER_TELEPORT::receive);
        ServerPlayNetworking.registerGlobalReceiver(PathMarkerUpdatePacket.ID, PATH_MARKER_UPDATE::receive);
        ServerPlayNetworking.registerGlobalReceiver(PathMarkerLinksUpdatePacket.ID, PATH_MARKER_LINKS_UPDATE::receive);
        ServerPlayNetworking.registerGlobalReceiver(PathDataUpdatePacket.ID, PATH_DATA_UPDATE_REQUEST::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChapterUpdatePacket.ID, CHAPTER_UPDATE::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChapterDeletePacket.ID, CHAPTER_DELETE::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChapterStartUpdatePacket.ID, CHAPTER_START_UPDATE::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChapterStartRemovePacket.ID, CHAPTER_START_REMOVE::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChapterPlayerTeleportPacket.ID, CHAPTER_PLAYER_TELEPORT::receive);
        ServerPlayNetworking.registerGlobalReceiver(PermissionCheckRequestPayload.ID, PERMISSION_CHECK::receiveOnServer);
        ServerPlayNetworking.registerGlobalReceiver(PathDataRequestPayload.ID, PATH_DATA_REQUEST::receiveOnServer);

        // 4. Register client-side receivers (respondable responses only)
        if (Fabric.isClient()) {
            ClientPlayNetworking.registerGlobalReceiver(
                    WesterosPathsPermissionCheckResponsePacket.ID, PERMISSION_CHECK::receiveOnClient);
            ClientPlayNetworking.registerGlobalReceiver(
                    PathDataResponsePacket.ID, PATH_DATA_REQUEST::receiveOnClient);
        }
    }
}
