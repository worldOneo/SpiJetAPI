package de.worldoneo.spijetapi.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.*;
import java.nio.file.Files;

public class ConfigUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves an Object to a YAML file
     * @param file The file to save the object to
     * @param object The object written to the file
     * @throws IOException When the file couldn't be created/opened.
     */
    public static void save(File file, Object object) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        String content = new Yaml(options).dumpAsMap(object);
        fileWriter.write(content);
        fileWriter.close();
    }

    /**
     *
     * @param file The file to load the config from
     * @param defaultConfig The default settings.
     * @param clazz The class which is stored in the file
     * @return The loaded config (as class) from the file or null if the config could <b>not</b> be casted as the class
     * @throws org.yaml.snakeyaml.error.YAMLException When the config couldn't be parsed to an object of that class.
     */
    @Nullable
    public static <T> T load(File file, T defaultConfig, Class<T> clazz) throws IOException {
        if (!file.exists()) {
            save(file, defaultConfig);
        }
        T config = load(file, clazz);
        save(file, config);
        return config;
    }

    /**
     * @param file  The file to load the config from
     * @param clazz The class which is stored in the file
     * @return The loaded config (as class) from the file or null if the config could <b>not</b> be casted as the class
     * @throws org.yaml.snakeyaml.error.YAMLException When the config couldn't be parsed to an object of that class.
     */
    @Nullable
    public static <T> T load(File file, Class<T> clazz) throws IOException {
        try {
            Yaml yaml = new Yaml(new CustomClassLoaderConstructor(clazz.getClassLoader()));
            FileInputStream fileInputStream = new FileInputStream(file);
            Object o = yaml.loadAs(fileInputStream, clazz);
            fileInputStream.close();
            return clazz.cast(o);
        } catch (ClassCastException ignored) {
            return null;
        }
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
