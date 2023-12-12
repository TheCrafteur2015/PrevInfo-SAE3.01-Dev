package vue;

import java.net.URL;

public final class ResourceManager {

	public static final URL ACCUEIL = ResourceManager.class.getResource("Accueil.fxml");

	public static final URL STYLESHEET = ResourceManager.class.getResource("style.css");
	
	private ResourceManager() {}

}