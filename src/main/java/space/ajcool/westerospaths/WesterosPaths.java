package space.ajcool.westerospaths;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.ajcool.westerospaths.core.Client;
import space.ajcool.westerospaths.core.PermissionHelper;
import space.ajcool.westerospaths.core.data.config.ServerConfigManager;
import space.ajcool.westerospaths.core.data.config.server.ServerConfig;
import space.ajcool.westerospaths.core.networking.PacketRegistry;
import space.ajcool.westerospaths.mc.blocks.ModBlocks;
import space.ajcool.westerospaths.mc.blocks.entities.ModBlockEntities;
import space.ajcool.westerospaths.mc.blocks.entities.PathMarkerBlockEntity;
import space.ajcool.westerospaths.mc.items.ModItemGroups;
import space.ajcool.westerospaths.mc.items.ModItems;
import space.ajcool.westerospaths.mc.particles.ModParticles;
import space.ajcool.westerospaths.mc.sounds.ModSounds;

public class WesterosPaths implements ModInitializer
{
    public static final String MOD_ID = "westerospaths";
    public static final Logger LOGGER = LoggerFactory.getLogger("westerospaths");
    public static final String MOD_EDIT_PERMISSION = String.format("%s.edit", MOD_ID);
    public static ServerConfigManager CONFIG_MANAGER;
    public static ServerConfig CONFIG;

    @Override
    public void onInitialize()
    {
        CONFIG_MANAGER = new ServerConfigManager("./config/westeros-paths/server.json");
        CONFIG = CONFIG_MANAGER.getConfig();

        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();
        ModItemGroups.init();
        ModParticles.init();
        ModSounds.init();
        PacketRegistry.init();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {
            var blockEntity = world.getBlockEntity(hitResult.getBlockPos().offset(hitResult.getSide()));

            if ((blockEntity instanceof PathMarkerBlockEntity || player.getStackInHand(hand).isOf(ModBlocks.PATH_MARKER.asItem())) && !PermissionHelper.hasEditPermission(player))
                return ActionResult.FAIL;

            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            var itemsStack = player.getStackInHand(hand);

            if (itemsStack.isOf(ModBlocks.PATH_MARKER.asItem()) && !PermissionHelper.hasEditPermission(player))
                return TypedActionResult.fail(itemsStack);

            return TypedActionResult.pass(itemsStack);
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) ->
        {
            if (blockEntity instanceof PathMarkerBlockEntity && !PermissionHelper.hasEditPermission(player))
                return false;

            return true;
        });
    }

    public static boolean amITheServer() {

        var serverEnv = FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;

        if (!serverEnv) return Client.isInSinglePlayer();

        return true;
    }
}