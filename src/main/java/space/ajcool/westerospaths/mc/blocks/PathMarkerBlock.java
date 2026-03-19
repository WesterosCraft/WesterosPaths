package space.ajcool.westerospaths.mc.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.WesterosPathsClient;
import space.ajcool.westerospaths.core.Client;
import space.ajcool.westerospaths.core.networking.PacketRegistry;
import space.ajcool.westerospaths.core.networking.packets.server.PathMarkerUpdatePacket;
import space.ajcool.westerospaths.mc.blocks.entities.ModBlockEntities;
import space.ajcool.westerospaths.mc.blocks.entities.PathMarkerBlockEntity;
import space.ajcool.westerospaths.mc.items.ModItems;
import space.ajcool.westerospaths.screens.Screens;

@SuppressWarnings("deprecation")
public class PathMarkerBlock extends BlockWithEntity
{
    public static BlockPos selectedBlockPosition = null;

    public PathMarkerBlock(AbstractBlock.Settings properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PathMarkerBlock::new);
    }

    @Override
    protected ActionResult onUse(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, BlockHitResult blockHitResult)
    {
        BlockEntity selectedBlockEntity = level.getBlockEntity(blockPos);

        if (selectedBlockEntity == null) return ActionResult.PASS;
        if (!player.isHolding(ModItems.PATH_MARKER) || !(selectedBlockEntity instanceof PathMarkerBlockEntity pathMarkerBlockEntity)) return ActionResult.PASS;
        if (!level.isClient()) return ActionResult.CONSUME;

        PacketRegistry.PERMISSION_CHECK.send(response -> {
            if (response.hasPermission()) this.validateOnUse(level, blockPos, pathMarkerBlockEntity, player);
        });

        return ActionResult.CONSUME;
    }

    public void validateOnUse(World level, BlockPos blockPos, PathMarkerBlockEntity pathMarkerBlockEntity, PlayerEntity player){
        MinecraftClient.getInstance().execute(() -> {
            if (Client.isCtrlDown()) {
                Screens.openEditorScreen(pathMarkerBlockEntity);
                return;
            }

            if (selectedBlockPosition == null) {
                selectedBlockPosition = blockPos;

                var message = Text.empty()
                        .append(Text.literal("WesterosPaths: ").formatted(Formatting.DARK_AQUA))
                        .append(Text.literal("Selected origin block.").formatted(Formatting.BLUE));

                player.sendMessage(message);

            } else {

                BlockEntity blockEntity = level.getBlockEntity(selectedBlockPosition);

                if (blockEntity instanceof PathMarkerBlockEntity pathMarker) {
                    MutableText message;

                    if (selectedBlockPosition.equals(blockPos)) {
                        message = Text.empty()
                                .append(Text.literal("WesterosPaths: ").formatted(Formatting.DARK_AQUA))
                                .append(Text.literal("Target block removed.").formatted(Formatting.RED));

                        PathMarkerBlockEntity.ChapterNbtData data = pathMarker.getChapterData(WesterosPathsClient.CONFIG.getSelectedPathId(), WesterosPathsClient.CONFIG.getCurrentChapterId());
                        data.removeTarget();
                    } else {
                        message = Text.empty()
                                .append(Text.literal("WesterosPaths: ").formatted(Formatting.DARK_AQUA))
                                .append(Text.literal("Target block set.").formatted(Formatting.GREEN));

                        PathMarkerBlockEntity.ChapterNbtData data = pathMarker.getChapterData(WesterosPathsClient.CONFIG.getSelectedPathId(), WesterosPathsClient.CONFIG.getCurrentChapterId());
                        data.setTarget(blockPos.subtract(selectedBlockPosition));
                    }

                    PathMarkerUpdatePacket packet = new PathMarkerUpdatePacket(pathMarker.getPos(), pathMarker.createNbt(level.getRegistryManager()));
                    PacketRegistry.PATH_MARKER_UPDATE.send(packet);
                    player.sendMessage(message);
                    WesterosPaths.LOGGER.info("Sending Update Packet");
                }

                selectedBlockPosition = null;
            }
        });
    }

    public boolean isTransparent(BlockState blockState, BlockView blockGetter, BlockPos blockPos)
    {
        return true;
    }

    public BlockRenderType getRenderType(BlockState blockState)
    {
        return BlockRenderType.INVISIBLE;
    }

    public float getAmbientOcclusionLightLevel(BlockState blockState, BlockView blockGetter, BlockPos blockPos)
    {
        return 1.0F;
    }

    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockGetter, BlockPos blockPos, ShapeContext collisionContext)
    {
        return collisionContext.isHolding(ModItems.PATH_MARKER) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new PathMarkerBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World level, BlockState blockState, BlockEntityType<T> blockEntityType)
    {
        return validateTicker(blockEntityType, ModBlockEntities.PATH_MARKER, PathMarkerBlockEntity::tick);
    }
}