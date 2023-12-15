package vue;

import java.net.URL;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import java.nio.file.Paths;

public final class ResourceManager {

	// Fichiers FXML
	public static final URL ACCUEIL = ResourceManager.class.getResource("Accueil.fxml");

	// Fichiers CSS
	public static final URL STYLESHEET = ResourceManager.class.getResource("style.css");
	public static final URL STYLESHEET_POPUP = ResourceManager.class.getResource("stylePopup.css");

	// Fichiers SVG
	public static final URL BOOK = ResourceManager.class.getResource("book.svg");

	// Images
	public static final URL HOUSE       = ResourceManager.class.getResource("/images/accueil_icone.png");
	public static final URL INTERVENANT = ResourceManager.class.getResource("/images/prof_icon.png");
	public static final URL MODULE      = ResourceManager.class.getResource("/images/ressource_icone.png");
	public static final URL DOWNLOAD    = ResourceManager.class.getResource("/images/download.png");

	private static final Map<String, String> DATA = new HashMap<>();

	static {
		for (Field field : ResourceManager.class.getFields()) {
			try {
				if (field.get(null) == null) {
					String name = field.getName();
					throw new MissingResourceException("Resource file missing: " + name, ResourceManager.class.getSimpleName(), name);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		File folder = null;
		try {
			folder = Paths.get(ResourceManager.class.getResource("").toURI()).toFile();
		} catch (Exception e) {

		}
		for (File file : folder.listFiles()) {
			if (!file.isFile())
				continue;
			String name = file.getName();
			String suffix = name.substring(name.lastIndexOf("."));
			if (suffix.equals(".data")) {
				String data = "";
				try (Scanner sc = new Scanner(file)) {
					while (sc.hasNextLine())
						data += sc.nextLine() + "\n";
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					ResourceManager.DATA.put(name.substring(0, name.length() - suffix.length()).toLowerCase(), data);
				}
			}
		}
	}

	private ResourceManager() {
	}

	public static String getData(String key) {
		return ResourceManager.DATA.get(key);
	}

}