package vue;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	private static final Logger LOGGER = Logger.getLogger(ResourceManager.APP_NAME);
	
	// Initialisation et configuration du Logger
	private static void configureLogger() {
		try {
			String path = ResourceManager.PWD;
			if (path.endsWith(File.separator + "bin"))
				path = path.substring(0, path.lastIndexOf(File.separator + "bin"));
			FileHandler fh = new FileHandler(ResourceManager.getAppLocation() + File.separator + "output.log");
			fh.setFormatter(new SimpleFormatter());
			App.LOGGER.addHandler(fh);
			App.LOGGER.setUseParentHandlers(false);
			App.log(Level.INFO, "Logger initiated!");
		} catch (IOException e) {
			App.log(Level.SEVERE, e);
		}
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			App.log(Level.SEVERE, e);
		});
		App.log(Level.INFO, "Default Uncaught Exception Handler set!");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				App.log(Level.FINER, "exit");
			}
		});
		App.log(Level.INFO, "Shutdown Hook set!");
	}
	
	public static void log(Level level, String message) {
		App.LOGGER.log(level, message);
	}
	
	public static void log(Level level, Throwable e) {
		App.log(level, e, null);
	}
	
	public static void log(Level level, Throwable e, String enclosingMethod) {
		App.LOGGER.logp(level, App.class.getName(), enclosingMethod, e, () -> e.getMessage());
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(ResourceManager.ACCUEIL);
		stage.setTitle(ResourceManager.APP_NAME);
		
		stage.getIcons().add(new Image(ResourceManager.ICON.toExternalForm()));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		ResourceManager.setConfigs(args);
		App.configureLogger();
		Application.launch(args);
	}
}
