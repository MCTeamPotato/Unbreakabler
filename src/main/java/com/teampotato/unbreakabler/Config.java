package com.teampotato.unbreakabler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

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

    public static void syncConfig() {
        Property property;
        config.load();
        property = config.get(Configuration.CATEGORY_GENERAL, "enabled", true, "EnableMod");
        enabled = property.getBoolean();
        property = config.get(Configuration.CATEGORY_GENERAL, "items", new String[]{}, "Unbreakable tag blacklist/whitelist items");
        items = property.getStringList();
        property = config.get(Configuration.CATEGORY_GENERAL, "validMods", new String[]{}, "If this is defined, only the mod that has this modID will be unbreakable.");
        validMods = property.getStringList();
        property = config.get(Configuration.CATEGORY_GENERAL, "mode", "B", "Use 'B' for Blacklist mode, use any other word(s) (e.g.  I love you) for whitelist mode");
        mode = property.getString();

        config.save();
    }
}
