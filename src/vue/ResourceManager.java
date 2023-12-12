package vue;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;import

import java.nio.file.Paths;

public final class ResourceManager {

	public static final URL ACCUEIL = ResourceManager.class.getResource("Accueil.fxml");

	public static final URL STYLESHEET = ResourceManager.class.getResource("style.css");

	public static final URL BOOK = ResourceManager.class.getResource("book.svg");

	private static final Map<String, String> DATA = new HashMap<>();

	static {
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