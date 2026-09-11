package dev.fixpot47.fixordium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FixordiumConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("fixordium.json");

    public boolean enabled = true;

    public static FixordiumConfig load() {
        if (!Files.exists(FILE)) {
            FixordiumConfig config = new FixordiumConfig();
            config.save();
            return config;
        }
        try (Reader reader = Files.newBufferedReader(FILE, StandardCharsets.UTF_8)) {
            FixordiumConfig config = GSON.fromJson(reader, FixordiumConfig.class);
            return config == null ? new FixordiumConfig() : config;
        } catch (Exception exception) {
            System.err.println("[Fixordium] Could not load config: " + exception.getMessage());
            return new FixordiumConfig();
        }
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Path temp = FILE.resolveSibling(FILE.getFileName() + ".tmp");
            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(this, writer);
            }
            try {
                Files.move(temp, FILE, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temp, FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            System.err.println("[Fixordium] Could not save config: " + exception.getMessage());
        }
    }
}
