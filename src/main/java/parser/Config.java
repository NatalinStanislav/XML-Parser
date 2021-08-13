package parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String DB_PROPS = "/db/postgres.properties";
    private static final Config INSTANCE = new Config();

    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream dbStream = Config.class.getResourceAsStream(DB_PROPS)) {
            Properties dbProps = new Properties();
            dbProps.load(dbStream);
            databaseUrl = dbProps.getProperty("database.url");
            databaseUsername = dbProps.getProperty("database.username");
            databasePassword = dbProps.getProperty("database.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + DB_PROPS);
        }
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
