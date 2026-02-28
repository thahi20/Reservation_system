package dao;

import model.Guest;
import model.Reservation;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class ReservationStore {
    private static ReservationStore instance;
    private final Path file;

    private ReservationStore(Path file) {
        this.file = file;
    }

    public static synchronized ReservationStore getInstance(ServletContext ctx) {
        if (instance == null) {
            Path f = Paths.get(ctx.getRealPath("/WEB-INF/data/reservations.txt"));
            ensureFile(f, "");
            instance = new ReservationStore(f);
        }
        return instance;
    }

    public synchronized boolean exists(String reservationNo) throws IOException {
        return findByNo(reservationNo) != null;
    }

    public synchronized void add(Reservation r) throws IOException {
        if (exists(r.getReservationNo())) {
            throw new IllegalArgumentException("Reservation number already exists");
        }
        String row = toRow(r);
        appendLineAtomic(row);
    }

    public synchronized Reservation findByNo(String reservationNo) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        for (String ln : lines) {
            if (ln.trim().isEmpty()) continue;
            String[] p = ln.split("\\|", -1);
            if (p.length < 8) continue;
            if (p[0].trim().equalsIgnoreCase(reservationNo.trim())) {
                return fromRow(p);
            }
        }
        return null;
    }

    public synchronized List<Reservation> findAll() throws IOException {
        List<Reservation> list = new ArrayList<>();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        for (String ln : lines) {
            if (ln.trim().isEmpty()) continue;
            String[] p = ln.split("\\|", -1);
            if (p.length < 8) continue;
            list.add(fromRow(p));
        }
        return list;
    }

    private void appendLineAtomic(String line) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        lines.add(line);

        Path tmp = file.resolveSibling(file.getFileName().toString() + ".tmp");
        Files.write(tmp, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    private static Reservation fromRow(String[] p) {
        String no = p[0].trim();
        String name = p[1].trim();
        String address = p[2].trim();
        String contact = p[3].trim();
        String roomType = p[4].trim();
        LocalDate checkIn = LocalDate.parse(p[5].trim());
        LocalDate checkOut = LocalDate.parse(p[6].trim());
        String createdAt = p[7].trim();

        Guest g = new Guest(name, address, contact);
        return new Reservation(no, g, roomType, checkIn, checkOut, createdAt);
    }

    private static String toRow(Reservation r) {
        Guest g = r.getGuest();
        return String.join("|",
                safe(r.getReservationNo()),
                safe(g.getName()),
                safe(g.getAddress()),
                safe(g.getContactNumber()),
                safe(r.getRoomType()).toUpperCase(Locale.ROOT),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                safe(r.getCreatedAt())
        );
    }

    private static String safe(String s) {
        if (s == null) return "";
        // Prevent breaking the delimiter
        return s.replace("|", "/").trim();
    }

    private static void ensureFile(Path file, String seed) {
        try {
            Files.createDirectories(file.getParent());
            if (!Files.exists(file)) {
                Files.writeString(file, seed, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create reservations file: " + file, e);
        }
    }
}
