package com.teampotato.unbreakabler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {
    public static boolean enabled = true;
    public static String[] items, validMods;
    public static String mode;

    public static Configuration config;

    public static void initConfig(File file)
    {
        config = new Configuration(file);
        syncConfig();
    }

    public static final Set<String> ITEMS = new HashSet<>(), VALID_MODS = new HashSet<>();

    public static void syncConfig() {
        Property property;
        config.load();
        property = config.get(Configuration.CATEGORY_GENERAL, "enabled", true, "EnableMod");
        enabled = property.getBoolean();
        property = config.get(Configuration.CATEGORY_GENERAL, "items", new String[]{}, "Unbreakable tag blacklist/whitelist items (write items' translation key here)");
        items = property.getStringList();
        ITEMS.addAll(Arrays.asList(items));
        property = config.get(Configuration.CATEGORY_GENERAL, "validMods", new String[]{}, "If this is defined, only the items that has these modIDs in their translation key will be unbreakable.");
        validMods = property.getStringList();
        VALID_MODS.addAll(Arrays.asList(validMods));
        property = config.get(Configuration.CATEGORY_GENERAL, "mode", "B", "Use 'B' for Blacklist mode, use any other word(s) (e.g.  I love you) for whitelist mode");
        mode = property.getString();

        config.save();
    }
}
