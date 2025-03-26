package main.kassy.bahou.akandan.reunion.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    static {
        // Chargement du fichier config.properties à partir du classpath (resources)
        try (InputStream input = Configuration.class.getResourceAsStream("/ressources/config.properties")) {
            if (input == null) {
                throw new IOException("Fichier config.properties introuvable dans le classpath.");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la valeur d'une propriété en chaîne de caractères.
     * @param key Clé de la propriété.
     * @return La valeur associée à la clé, ou null si elle n'existe pas.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Récupère la valeur d'une propriété et la convertit en entier.
     * @param key Clé de la propriété.
     * @return La valeur entière associée à la clé, ou -1 si elle n'existe pas ou n'est pas convertible.
     */
    public static int getIntProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
