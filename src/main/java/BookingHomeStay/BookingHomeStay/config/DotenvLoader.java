package BookingHomeStay.BookingHomeStay.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Tự động tìm và nạp các biến cấu hình từ file .env vào System Properties và Spring Environment.
 */
public class DotenvLoader {

    private static Map<String, Object> loadedProperties = null;

    public static synchronized Map<String, Object> load() {
        if (loadedProperties != null) {
            return loadedProperties;
        }

        Map<String, Object> props = new HashMap<>();
        Path envPath = findEnvFile();

        if (envPath != null && Files.exists(envPath)) {
            try (BufferedReader reader = Files.newBufferedReader(envPath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int eqIdx = line.indexOf('=');
                    if (eqIdx > 0) {
                        String key = line.substring(0, eqIdx).trim();
                        String value = line.substring(eqIdx + 1).trim();

                        // Xử lý dấu ngoặc kép / đơn nếu có
                        if ((value.startsWith("\"") && value.endsWith("\"")) ||
                            (value.startsWith("'") && value.endsWith("'"))) {
                            if (value.length() >= 2) {
                                value = value.substring(1, value.length() - 1);
                            }
                        }

                        props.put(key, value);

                        // Đưa vào System Property nếu chưa có để Spring ${...} tự động nhận diện
                        if (System.getProperty(key) == null) {
                            System.setProperty(key, value);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("[DotenvLoader] Cảnh báo: Không thể đọc file .env: " + e.getMessage());
            }
        }

        loadedProperties = Collections.unmodifiableMap(props);
        return loadedProperties;
    }

    private static Path findEnvFile() {
        // 1. Kiểm tra tại thư mục làm việc hiện tại
        Path p = Paths.get(".env");
        if (Files.exists(p)) {
            return p;
        }

        // 2. Kiểm tra tại user.dir
        String userDir = System.getProperty("user.dir");
        if (userDir != null) {
            p = Paths.get(userDir, ".env");
            if (Files.exists(p)) {
                return p;
            }

            // 3. Kiểm tra tại thư mục cha nếu chạy từ subfolder
            p = Paths.get(userDir, "..", ".env");
            if (Files.exists(p)) {
                return p;
            }
        }

        return null;
    }
}
