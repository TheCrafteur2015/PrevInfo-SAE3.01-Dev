package vue;

import java.lang.reflect.Method;
import java.net.URL;

import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXHelper {
	
	private FXHelper() {}
	
	public static Stage createPopup(String title, double height, double width, boolean isResizable) {
		Stage popupStage = new Stage();
		popupStage.centerOnScreen();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle(title);
		popupStage.setHeight(height);
		popupStage.setWidth(width);
		popupStage.setResizable(isResizable);
		return popupStage;
	}
	
	public static void addStylesheet(Scene scene, URL stylesheet) {
		FXHelper.addStylesheet(scene, stylesheet.toExternalForm());
	}
	
	public static void addStylesheet(Scene scene, String stylesheet) {
		scene.getStylesheets().add(stylesheet);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean append(Parent parent, Node child) {
		if (parent == null || child == null)
			return false;
		if (parent == child)
			return false;
		try {
			Method m = parent.getClass().getMethod("getChildren");
			ObservableList<Node> children = (ObservableList<Node>) m.invoke(parent);
			children.add(child);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean addClass(Styleable node, String className) {
		if (node == null || className == null)
			return false;
		node.getStyleClass().add(className);
		return true;
	}
	
}