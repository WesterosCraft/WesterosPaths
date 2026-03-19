package space.ajcool.westerospaths.core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.util.Tristate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.networking.PacketRegistry;

public class PermissionHelper {

    private static Boolean hasEditPermission = false;
    private static long lastPermissionCheckTime = 0;
    private static final long PERMISSION_CHECK_COOLDOWN_MS = 60_000; // 1 minute cooldown

    public static boolean hasEditPermission(@Nullable PlayerEntity player) {

        if (player == null) return false;

        if (WesterosPaths.amITheServer()) return serverEditPermissionCheck(player);
        else return clientEditPermissionCheck();
    }

    private static boolean clientEditPermissionCheck() {

        WesterosPaths.LOGGER.info("Client check edit permission for player {}", hasEditPermission);

        var currentTime = System.currentTimeMillis();
        var delta = currentTime - lastPermissionCheckTime;

        if (hasEditPermission == null || delta > PERMISSION_CHECK_COOLDOWN_MS) {

            WesterosPaths.LOGGER.info("Refreshing permissions");

            lastPermissionCheckTime = currentTime;
            PacketRegistry.PERMISSION_CHECK.send(response -> {hasEditPermission = response.hasPermission();});

            // Default to false until we get a response from the server
            return hasEditPermission != null ? hasEditPermission : false;
        }

        return hasEditPermission;
    }

    private static boolean serverEditPermissionCheck(@NotNull PlayerEntity player) {

        if (player instanceof ServerPlayerEntity serverPlayer) {

            LuckPerms luckpermsApi = LuckPermsProvider.get();
            User user = luckpermsApi.getPlayerAdapter(ServerPlayerEntity.class).getUser(serverPlayer);

            CachedPermissionData permissionData = user.getCachedData().getPermissionData();
            Tristate checkResult = permissionData.checkPermission(WesterosPaths.MOD_EDIT_PERMISSION);

            boolean result = checkResult.asBoolean();
            WesterosPaths.LOGGER.info("Server check edit permission for player {} - {}", player.getName(), result);

            return result;
        }

        return false;
    }
}
