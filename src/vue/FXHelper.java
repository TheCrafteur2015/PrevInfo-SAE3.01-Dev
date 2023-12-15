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
	
}