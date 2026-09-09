package BookingHomeStay.BookingHomeStay.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * EnvironmentPostProcessor chạy trong giai đoạn khởi động sớm của Spring Boot,
 * đảm bảo các giá trị trong .env luôn có sẵn cho application.properties và @Value.
 */
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final String PROPERTY_SOURCE_NAME = "dotenvProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> envMap = DotenvLoader.load();
        if (!envMap.isEmpty()) {
            if (!environment.getPropertySources().contains(PROPERTY_SOURCE_NAME)) {
                // Thêm vào đầu để các biến trong .env được ưu tiên giải quyết trước application.properties
                environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, envMap));
            }
        }
    }

    @Override
    public int getOrder() {
        // Chạy trước khi cấu hình application.properties được phân giải
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
