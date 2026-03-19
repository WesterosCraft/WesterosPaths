package space.ajcool.westerospaths.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import space.ajcool.westerospaths.WesterosPathsClient;
import space.ajcool.westerospaths.core.data.Journal;
import space.ajcool.westerospaths.core.networking.PacketRegistry;
import space.ajcool.westerospaths.core.networking.packets.server.PlayerTeleportPacket;
import space.ajcool.westerospaths.paths.rendering.ProximityRenderer;
import space.ajcool.westerospaths.screens.builders.TextBuilder;
import space.ajcool.westerospaths.screens.widgets.JournalListEntry;
import space.ajcool.westerospaths.screens.widgets.JournalListWidget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the journal screen
 */
@Environment(value = EnvType.CLIENT)
public class JournalScreen extends Screen {

    /**
     * Constructs a new JournalScreen instance.
     */
    protected JournalScreen() {
        super(Text.literal(Text.translatable("westerospaths.client.journal.screen.title").getString()));
    }

    /**
     * Initializes the journal screen.
     */
    @Override
    protected void init() {

        super.init();

        int totalUiWidth = 500;

        int center = width / 2;
        int y = (height / 2);

        List<JournalListEntry> entries = new ArrayList<>();
        List<Journal.Entry> journalEntries = new ArrayList<>(Journal.getEntries());
        Collections.reverse(journalEntries);

        for (Journal.Entry entry : journalEntries) {

            switch (entry.type()) {
                case CHAPTER_START -> entries.add(new JournalListEntry(
                        Text.translatable("westerospaths.client.journal.screen.entry.type.chapter"),
                        Text.literal(entry.text()),
                        Text.translatable("westerospaths.client.journal.screen.teleport"),
                        entry.color(),
                        button -> handleTeleportRequest(entry.pathId(), entry.chapterId(), entry.teleportPacket())
                ));
                case PROXIMITY_MESSAGE -> entries.add(new JournalListEntry(
                        Text.translatable("westerospaths.client.journal.screen.entry.type.entry"),
                        Text.literal(entry.text()),
                        Text.translatable("westerospaths.client.journal.screen.teleport"),
                        entry.color(),
                        button -> handleTeleportRequest(entry.pathId(), entry.chapterId(), entry.teleportPacket())
                ));
              }
        }

        int rowWidth = totalUiWidth - 40;
        int totalContentHeight = 0;
        for (JournalListEntry entry : entries) {
            totalContentHeight += entry.getHeight(rowWidth);
        }
        // Clamp list height to screen bounds
        int maxListHeight = height - 120;
        int listHeight = Math.min(totalContentHeight + 8, maxListHeight);

        // Center vertically
        int listTop = (height - listHeight) / 2 + 20;

        // Title above list
        int titleY = listTop - 25;
        this.addDrawableChild(TextBuilder.create()
                .setPosition(center - 75, titleY)
                .setSize(150, 20)
                .setText(Text.literal(Text.translatable("westerospaths.client.journal.screen.title").getString()))
                .build()
        );

        JournalListWidget listWidget = new JournalListWidget(
                this.client, totalUiWidth, listHeight, listTop, 32
        );

        // Center horizontally
        listWidget.setPosition((width - totalUiWidth) / 2, listTop);

        // Add pre-built entries
        for (JournalListEntry entry : entries) {
            listWidget.addJournalEntry(entry);
        }

        this.addDrawableChild(listWidget);
    }

    /**
     * Teleport the player using the provided teleport packet. Also sets the selected path and chapter if provided.
     *
     * @param pathId         The ID of the path
     * @param chapterId      The ID of the chapter
     * @param teleportPacket The teleport packet containing teleportation data
     */
    private void handleTeleportRequest(String pathId, String chapterId, PlayerTeleportPacket teleportPacket) {

        if (pathId != null && chapterId != null) {

            WesterosPathsClient.CONFIG.setSelectedPath(pathId);
            WesterosPathsClient.CONFIG.setCurrentChapter(chapterId);
        }

        if (teleportPacket != null) {

            ProximityRenderer.clear();
            PacketRegistry.PLAYER_TELEPORT.send(teleportPacket);
        }
    }

    /**
     * Renders the screen.
     *
     * @param context The draw context
     * @param mouseX  The mouse X position
     * @param mouseY  The mouse Y position
     * @param delta   The delta time
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);
    }
}