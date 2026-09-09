package BookingHomeStay.BookingHomeStay.config;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DotenvLoaderTest {

    @Test
    void testLoadEnvProperties() {
        Map<String, Object> props = DotenvLoader.load();
        assertNotNull(props);
        assertFalse(props.isEmpty(), ".env should be loaded and not empty");

        assertTrue(props.containsKey("MAIL_USERNAME"));
        assertTrue(props.containsKey("MAIL_PASSWORD"));
        assertTrue(props.containsKey("DB_URL"));
        assertTrue(props.containsKey("DB_USERNAME"));
        assertTrue(props.containsKey("DB_PASSWORD"));

        assertEquals("vutrggiang@gmail.com", props.get("MAIL_USERNAME"));
        assertEquals("pbav uqpg cqkw nnkd", props.get("MAIL_PASSWORD"));
        assertEquals("root", props.get("DB_USERNAME"));
    }
}
