package me.khajiitos.catloaf.common.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.khajiitos.catloaf.common.CatLoaf;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class CatLoafConfig {
    private static final File file = new File("config/cat_loaf.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Entry
    public static final CatLoafConfigValues.IntValue loafChance = new CatLoafConfigValues.IntValue(50).withMin(0).withMax(100);

    @Entry
    public static final CatLoafConfigValues.BooleanValue overrideChanceWithBread = new CatLoafConfigValues.BooleanValue(true);

    public static void init() {
        if (!file.exists()) {
            save();
        } else {
            load();
        }
    }

    public static void save() {
        if (!file.getParentFile().isDirectory() && !file.getParentFile().mkdirs()) {
            CatLoaf.LOGGER.error("Failed to create the config directory");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonObject jsonObject = new JsonObject();

            for (Field field : CatLoafConfig.class.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Entry.class)) {
                    continue;
                }

                Object object = field.get(null);

                if (!(object instanceof CatLoafConfigValues.Value<?> configValue)) {
                    continue;
                }

                jsonObject.add(field.getName(), configValue.write());
            }

            GSON.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            CatLoaf.LOGGER.error("Failed to save the Cat Loaf config", e);
        } catch (IllegalAccessException e) {
            CatLoaf.LOGGER.error("Error while saving the Cat Loaf config", e);
        }
    }

    public static void load() {
        if (!file.exists()) {
            return;
        }

        try (FileReader fileReader = new FileReader(file)) {
            JsonObject jsonObject = GSON.fromJson(fileReader, JsonObject.class);

            for (Field field : CatLoafConfig.class.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Entry.class)) {
                    continue;
                }

                String fieldName = field.getName();

                if (!jsonObject.has(fieldName)) {
                    continue;
                }

                Object object = field.get(null);

                if (!(object instanceof CatLoafConfigValues.Value<?> configValue)) {
                    continue;
                }

                JsonElement jsonElement = jsonObject.get(fieldName);
                configValue.setUnchecked(configValue.read(jsonElement));
            }
        } catch (IOException e) {
            CatLoaf.LOGGER.error("Failed to read the Cat Loaf config", e);
        } catch (IllegalAccessException e) {
            CatLoaf.LOGGER.error("Error while reading the Cat Loaf config", e);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface Entry { }
}