package space.ajcool.westerospaths.mc.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import space.ajcool.westerospaths.WesterosPaths;

public class ModSounds
{
    public static final SoundEvent TRAIL = register("trail_sound");

    /**
     * Register a sound.
     *
     * @param id The sound's ID.
     */
    private static SoundEvent register(final String id)
    {
        final Identifier identifier = Identifier.of(WesterosPaths.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init()
    {
    }
}
