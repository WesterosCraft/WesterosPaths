package space.ajcool.westerospaths.core.networking.handlers.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import space.ajcool.westerospaths.core.networking.packets.server.PlayerTeleportPacket;

public class PlayerTeleportHandler {

    public void send(PlayerTeleportPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    public void receive(PlayerTeleportPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        MinecraftServer server = context.server();

        server.execute(() -> {
            if (payload.worldId() != null) {
                RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, payload.worldId());
                ServerWorld serverWorld = server.getWorld(key);

                if (serverWorld != null) {
                    player.teleport(serverWorld, payload.x(), payload.y(), payload.z(), player.getYaw(), player.getPitch());
                    return;
                }
            }

            player.teleport(payload.x(), payload.y(), payload.z(), false);
        });
    }
}
