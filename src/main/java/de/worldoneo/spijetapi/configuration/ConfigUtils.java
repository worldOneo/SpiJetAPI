package de.worldoneo.spijetapi.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;

@UtilityClass
public class ConfigUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves an Object to a YAML file
     *
     * @param file   The file to save the object to
     * @param object The object written to the file
     * @throws IOException When the file couldn't be created/opened.
     */
    public static void save(File file, Object object) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        ensureFile(file);
        BufferedWriter fileWriter = Files.newBufferedWriter(file.toPath());
        String content = new Yaml(options).dumpAsMap(object);
        fileWriter.write(content);
        fileWriter.close();
    }

    /**
     * @param <T>           The class defined by the third arg
     * @param file          The file to load the config from
     * @param defaultConfig The default settings.
     * @param clazz         The class which is stored in the file
     * @return The loaded config (as class) from the file or null if the config could <b>not</b> be casted as the class
     * @throws org.yaml.snakeyaml.error.YAMLException When the config couldn't be parsed to an object of that class.
     * @throws IOException                            When the file couldn't be read or written.
     */
    @NotNull
    public static <T> T load(File file, T defaultConfig, Class<T> clazz) throws IOException {
        if (!file.exists()) {
            save(file, defaultConfig);
        }
        T config = load(file, clazz);
        save(file, config);
        return config;
    }

    /**
     * Only works with JavaBean objects
     *
     * @param <T>   Class defined by second arg
     * @param file  The file to load the config from
     * @param clazz The class which is stored in the file
     * @return The loaded config (as class) from the file or null if the config could <b>not</b> be casted as the class
     * @throws org.yaml.snakeyaml.error.YAMLException When the config couldn't be parsed to an object of that class.
     * @throws IOException                            When the file couldn't be read.
     */
    @NotNull
    public static <T> T load(File file, Class<T> clazz) throws IOException {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(clazz.getClassLoader()), representer);
        FileInputStream fileInputStream = new FileInputStream(file);
        T o = yaml.loadAs(fileInputStream, clazz);
        fileInputStream.close();
        return o;
    }

    @NotNull
    public static <T> T loadJson(File file, Class<T> classOfT) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file.toPath());
             Reader reader = new InputStreamReader(inputStream)) {
            return GSON.fromJson(reader, classOfT);
        }
    }

    /**
     * Loads a JSON object from a file
     *
     * @param file        the file to load the object from
     * @param classOfT    the class of the object
     * @param defaultData default data to use and write if no file exists
     * @param <T>         Class defined by second arg
     * @return the loaded class or null if an IOException occurred
     * @throws IOException when the file couldn't be written/read
     */
    @NotNull
    public static <T> T loadJson(File file, Class<T> classOfT, Object defaultData) throws IOException {
        if (ensureFile(file)) {
            saveJson(file, defaultData);
        }
        T data = loadJson(file, classOfT);
        saveJson(file, data);
        return data;
    }

    /**
     * Saves an object as JSON to a file
     *
     * @param file       the file to write the object to
     * @param dataObject the object to write to the file
     * @throws IOException when the file couldn't be written
     */
    public static void saveJson(File file, Object dataObject) throws IOException {
        ensureFile(file);
        String data = GSON.toJson(dataObject);
        Files.write(file.toPath(), data.getBytes());
    }

    /**
     * Ensures the existence of the given file.
     * If the file doesn't exists the required parent directories and the file will be created
     *
     * @param file the file to ensure the existence of
     * @return whether the file was newly created
     * @throws IOException if there was an error in creating the file
     */
    public static boolean ensureFile(File file) throws IOException {
        if (!file.exists()) {
            if ((file.getParentFile().exists() || file.getParentFile().mkdirs())
                    && file.createNewFile()) {
                return true;
            }
            throw new IOException("Unable to create the file " + file.getAbsolutePath());
        }
        return false;
    }
}
