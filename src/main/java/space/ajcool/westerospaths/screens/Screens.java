package space.ajcool.westerospaths.screens;

import net.minecraft.client.MinecraftClient;
import space.ajcool.westerospaths.core.Fabric;
import space.ajcool.westerospaths.mc.blocks.entities.PathMarkerBlockEntity;

public class Screens
{
    public static void openEditorScreen(PathMarkerBlockEntity pathMarkerBlockEntity)
    {
        if (Fabric.isClient())
        {
            MinecraftClient.getInstance().setScreen(new MarkerEditScreen(pathMarkerBlockEntity));
        }
    }

    public static void openSelectionScreen()
    {
        if (Fabric.isClient())
        {
            MinecraftClient.getInstance().setScreen(new PathSelectionScreen());
        }
    }
}
