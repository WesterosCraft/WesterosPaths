package space.ajcool.westerospaths.core.data.config;

import space.ajcool.westerospaths.core.data.config.server.ServerConfig;
import space.ajcool.westerospaths.core.data.config.shared.ChapterData;
import space.ajcool.westerospaths.core.data.config.shared.Color;
import space.ajcool.westerospaths.core.data.config.shared.PathData;

public class ServerConfigManager extends ConfigManager<ServerConfig>
{
    public ServerConfigManager(String configPath)
    {
        super(configPath);
    }

    @Override
    protected ServerConfig createDefault()
    {
        ServerConfig config = new ServerConfig();
        config.addPath(new PathData()
                .setId("eddard")
                .setName("Eddard Stark's Path")
                .setPrimaryColor(new Color(255, 215, 0))
                .setSecondaryColor(new Color(230, 194, 0))
                .setTertiaryColor(new Color(255, 227, 77))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("catelyn")
                .setName("Catelyn Stark's Path")
                .setPrimaryColor(new Color(0, 158, 96))
                .setSecondaryColor(new Color(0, 126, 77))
                .setTertiaryColor(new Color(77, 187, 144))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("bran")
                .setName("Bran Stark's Path")
                .setPrimaryColor(new Color(92, 64, 51))
                .setSecondaryColor(new Color(64, 45, 36))
                .setTertiaryColor(new Color(139, 109, 95))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("sansa")
                .setName("Sansa Stark's Path")
                .setPrimaryColor(new Color(199, 21, 133))
                .setSecondaryColor(new Color(139, 15, 93))
                .setTertiaryColor(new Color(222, 93, 170))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("arya")
                .setName("Arya Stark's Path")
                .setPrimaryColor(new Color(112, 128, 144))
                .setSecondaryColor(new Color(78, 90, 101))
                .setTertiaryColor(new Color(159, 171, 183))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("tyrion")
                .setName("Tyrions Lannister's Path")
                .setPrimaryColor(new Color(125, 249, 255))
                .setSecondaryColor(new Color(88, 174, 179))
                .setTertiaryColor(new Color(164, 251, 255))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("jon")
                .setName("Jon Snow's Path")
                .setPrimaryColor(new Color(227, 66, 52))
                .setSecondaryColor(new Color(159, 46, 36))
                .setTertiaryColor(new Color(235, 123, 113))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("theon")
                .setName("Theon Greyjoy's Path")
                .setPrimaryColor(new Color(0, 105, 148))
                .setSecondaryColor(new Color(0, 74, 104))
                .setTertiaryColor(new Color(77, 155, 184))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("davos")
                .setName("Davos Seaworth's Path")
                .setPrimaryColor(new Color(205, 133, 63))
                .setSecondaryColor(new Color(144, 93, 44))
                .setTertiaryColor(new Color(222, 170, 120))
                .setChapter(new ChapterData("default", "Default", "0", 0))
        );
        config.addPath(new PathData()
                .setId("info")
                .setName("Project Info")
                .setPrimaryColor(new Color(255, 255, 255))
                .setSecondaryColor(new Color(255, 255, 255))
                .setTertiaryColor(new Color(255, 255, 255))
                .setChapter(new ChapterData("info", "Info", "0", 0))
        );
        return config;
    }
}
