package dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.servlet.ServletContext;

public class UserStore {
    private static UserStore instance;

    private final Path usersFile;

    private UserStore(Path usersFile) {
        this.usersFile = usersFile;
    }

    public static synchronized UserStore getInstance(ServletContext ctx) {
        if (instance == null) {
            Path file = Paths.get(ctx.getRealPath("/WEB-INF/data/users.txt"));
            ensureFile(file, "admin|admin123\n");
            instance = new UserStore(file);
        }
        return instance;
    }

    public boolean validate(String username, String password) throws IOException {
        List<String> lines = Files.readAllLines(usersFile, StandardCharsets.UTF_8);
        for (String ln : lines) {
            if (ln.trim().isEmpty()) continue;
            String[] p = ln.split("\\|", -1);
            if (p.length < 2) continue;
            if (p[0].trim().equals(username) && p[1].trim().equals(password)) return true;
        }
        return false;
    }

    private static void ensureFile(Path file, String seed) {
        try {
            Files.createDirectories(file.getParent());
            if (!Files.exists(file)) {
                Files.writeString(file, seed, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create users file: " + file, e);
        }
    }
}
