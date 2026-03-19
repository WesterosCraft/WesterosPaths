package space.ajcool.westerospaths.core.data.config;

import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import space.ajcool.westerospaths.WesterosPaths;
import space.ajcool.westerospaths.core.Client;
import space.ajcool.westerospaths.core.data.Json;
import space.ajcool.westerospaths.core.data.config.client.ClientConfig;
import space.ajcool.westerospaths.core.data.config.shared.Color;
import space.ajcool.westerospaths.core.data.config.shared.PathData;
import space.ajcool.westerospaths.core.networking.PacketRegistry;
import space.ajcool.westerospaths.mc.items.ModItems;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientConfigManager extends ConfigManager<ClientConfig>
{
    public ClientConfigManager(String configPath)
    {
        super(configPath);
    }

    @Override
    protected ClientConfig createDefault()
    {
        ClientConfig config = new ClientConfig();
        config.showProximityMessages(true);
        config.showChapterTitles(false);
        return config;
    }

    /**
     * Update the path data from the server.
     */
    public void updatePathData()
    {
        if (Client.isInSinglePlayer())
        {
            ServerConfigManager serverConfigManager = WesterosPaths.CONFIG_MANAGER;
            this.onPathData(serverConfigManager.getConfig().getPaths());
        }
        else
        {
            PacketRegistry.PATH_DATA_REQUEST.send(response ->
            {
                String json = response.json();

                Type listType = new TypeToken<ArrayList<PathData>>()
                {
                }.getType();

                List<PathData> paths = Json.fromJson(json, listType);

                if (paths != null)
                {
                    WesterosPaths.LOGGER.info("Updating path data");

                    this.onPathData(paths);
                }
            });
        }
    }

    /**
     * Called when path data is received from the server.
     *
     * @param paths The path data
     */
    public void onPathData(List<PathData> paths)
    {
        this.config.setPaths(paths);

        if (this.config.getSelectedPathId().isEmpty() && !paths.isEmpty())
        {
            this.config.setSelectedPath(paths.get(0).getId());
        }

        this.save();

        ColorProviderRegistry.ITEM.register((itemStack, i) ->
        {
            for (PathData path : paths)
            {
                if (!path.getId().equalsIgnoreCase(this.config.getSelectedPathId())) continue;
                return path.getPrimaryColor().asHex();
            }
            return Color.fromRgb(100, 100, 100).asHex();
        }, ModItems.PATH_REVEALER);
    }
}
