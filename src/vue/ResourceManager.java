package vue;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Scanner;
import java.util.logging.Level;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public final class ResourceManager {
	
	public static final String APP_NAME = "PrevInfo";
	public static final String PWD = System.getProperty("user.dir");
	
	private static String appLocation = null;
	private static String idFilePath = null;
	private static String dbHost = "woody";
	
	// Fichiers FXML
	public static final URL ACCUEIL = ResourceManager.class.getResource("Accueil.fxml");
	
	// Fichiers CSS
	public static final URL STYLESHEET = ResourceManager.class.getResource("style.css");
	public static final URL STYLESHEET_POPUP = ResourceManager.class.getResource("stylePopup.css");
	
	// Images
	public static final URL HOUSE       = ResourceManager.class.getResource("/images/accueil_icone.png");
	public static final URL INTERVENANT = ResourceManager.class.getResource("/images/prof_icon.png");
	public static final URL MODULE      = ResourceManager.class.getResource("/images/ressource_icone.png");
	public static final URL DOWNLOAD    = ResourceManager.class.getResource("/images/download.png");
	public static final URL CHECK       = ResourceManager.class.getResource("/images/check.png");
	public static final URL DELETE      = ResourceManager.class.getResource("/images/delete.png");
	public static final URL INFO        = ResourceManager.class.getResource("/images/information.png");
	public static final URL ICON        = ResourceManager.class.getResource("/images/LogoNUMIT.png");
	
	private static final Map<String, String> DATA = new HashMap<>();
	
	static {
		for (Field field : ResourceManager.class.getFields()) {
			try {
				if (field.get(null) == null) {
					String name = field.getName();
					throw new MissingResourceException("Resource file missing: " + name, ResourceManager.class.getSimpleName(), name);
				} 
			} catch (IllegalArgumentException | IllegalAccessException e) {
				App.log(Level.SEVERE, e);
			}
		}
	}
	
	public static Button getSupButton() {
		SVGPath supsvg = new SVGPath();
		supsvg.setContent(
				"M13.7766 10.1543L13.3857 21.6924M7.97767 21.6924L7.58686 10.1543M18.8458 6.03901C19.2321 6.10567 19.6161 6.17618 20.0001 6.25182M18.8458 6.03901L17.6395 23.8373C17.5902 24.5619 17.3018 25.2387 16.8319 25.7324C16.362 26.2261 15.7452 26.5002 15.1049 26.5H6.25856C5.61824 26.5002 5.00145 26.2261 4.53152 25.7324C4.0616 25.2387 3.77318 24.5619 3.72395 23.8373L2.51764 6.03901M18.8458 6.03901C17.5422 5.81532 16.2318 5.64555 14.9174 5.53005M2.51764 6.03901C2.13135 6.10439 1.74731 6.1749 1.36328 6.25054M2.51764 6.03901C3.82124 5.81532 5.13158 5.64556 6.44606 5.53005M14.9174 5.53005V4.35572C14.9174 2.84294 13.8895 1.58144 12.5567 1.534C11.307 1.48867 10.0564 1.48867 8.80673 1.534C7.47391 1.58144 6.44606 2.84422 6.44606 4.35572V5.53005M14.9174 5.53005C12.0978 5.28272 9.26562 5.28272 6.44606 5.53005");
		supsvg.setStroke(Color.BLACK);
		supsvg.setFill(Color.WHITE);
		supsvg.setStrokeWidth(1.5);
		supsvg.setStrokeLineCap(StrokeLineCap.ROUND);
		supsvg.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		Button supbtn = new Button();
		supbtn.setGraphic(supsvg);
		supbtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		supbtn.getStyleClass().add("info-btn");
		return supbtn;
	}
	
	private ResourceManager() {}
	
	public static void setConfigs(String[] args) {
		if (args == null || args.length == 0)
			return;
		switch (args.length) {
			case 1 -> ResourceManager.idFilePath = args[0];
			case 2 -> {
				ResourceManager.idFilePath = args[0];
				ResourceManager.dbHost = args[1];
			}
		}
		ResourceManager.appLocation = ResourceManager.idFilePath.substring(0, ResourceManager.idFilePath.indexOf("/identifiants.txt"));
	}
	
	public static String getAppLocation() {
		return ResourceManager.appLocation;
	}
	
	public static String getIdFile() {
		return ResourceManager.idFilePath;
	}
	
	public static String getDBHost() {
		return ResourceManager.dbHost;
	}
	
	public static String loadFile(URL url) {
		try (Scanner sc = new Scanner(new File(url.toURI()))) {
			String content = "";
			while (sc.hasNextLine())
				content += sc.nextLine() + "\n";
			return content;
		} catch (Exception e) {
			System.err.println("Error loading file: " + e.getMessage());
			App.log(Level.WARNING, e);
			return null;
		}
	}
	
	public static String loadFileFromSource(String path) {
		try {
			URL url = ResourceManager.class.getResource(path);
			url = new URL(url.toString().replace("bin", "src"));
			String content = "";
			try (Scanner sc = new Scanner(new File(url.toURI()))) {
				while (sc.hasNextLine())
					content += sc.nextLine() + "\n";
			}
			return content;
		} catch (Exception e) {
			App.log(Level.WARNING, e);
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private static void saveFile(String path, String content) {
		try (PrintWriter pw = new PrintWriter(new File(path))) {
			pw.write(content);
		} catch (Exception e) {
			App.log(Level.SEVERE, e);
		}
	}
	
	
	public static String getData(String key) {
		return ResourceManager.DATA.get(key);
	}
	
}