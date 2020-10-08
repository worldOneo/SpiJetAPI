package de.worldoneo.spijetapi.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class ConfigUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static YamlConfiguration load(File file, InputStream defaultConfig) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                Files.copy(defaultConfig, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return load(file);
    }

    public static YamlConfiguration load(File file, String defaultConfig) {
        return load(file, new ByteArrayInputStream(defaultConfig.getBytes()));
    }

    public static YamlConfiguration load(File file) {
        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.load(file);
            return yamlConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T loadJson(File file, Class<T> classOfT) {
        try (InputStream inputStream = new FileInputStream(file);
             Reader reader = new InputStreamReader(inputStream)) {
            return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T loadJson(File file, Class<T> classOfT, Object defaultData) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveJson(file, defaultData);
        }
        return loadJson(file, classOfT);
    }


    public static boolean saveJson(File file, Object dataObject) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        String data = gson.toJson(dataObject);
        try {
            Files.write(file.toPath(), data.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
