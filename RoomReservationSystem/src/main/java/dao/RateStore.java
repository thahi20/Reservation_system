package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.servlet.ServletContext;

public class RateStore {
    private static RateStore instance;
    private final Path ratesFile;

    private RateStore(Path ratesFile) {
        this.ratesFile = ratesFile;
    }

    public static synchronized RateStore getInstance(ServletContext ctx) {
        if (instance == null) {
            Path file = Paths.get(ctx.getRealPath("/WEB-INF/data/rates.txt"));
            ensureFile(file,
                    "STANDARD|12000\n" +
                    "DELUXE|18000\n" +
                    "SUITE|25000\n");
            instance = new RateStore(file);
        }
        return instance;
    }

    public Map<String, Integer> getRates() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        List<String> lines = Files.readAllLines(ratesFile, StandardCharsets.UTF_8);
        for (String ln : lines) {
            if (ln.trim().isEmpty()) continue;
            String[] p = ln.split("\\|", -1);
            if (p.length < 2) continue;
            map.put(p[0].trim().toUpperCase(Locale.ROOT), Integer.parseInt(p[1].trim()));
        }
        return map;
    }

    public int getRate(String roomType) throws IOException {
        return getRates().getOrDefault(roomType.toUpperCase(Locale.ROOT), 0);
    }

    private static void ensureFile(Path file, String seed) {
        try {
            Files.createDirectories(file.getParent());
            if (!Files.exists(file)) {
                Files.writeString(file, seed, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create rates file: " + file, e);
        }
    }
}
