package vue;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public class AlternatingColorTableView<T> extends TableView<T> {

    public AlternatingColorTableView(int ligne) {
        init(ligne);
    }

    private void init(int ligne) {
        // Ajoute un écouteur aux changements dans la liste des colonnes
        getColumns().addListener(new ListChangeListener<TableColumn<T, ?>>() {
            @Override
            public void onChanged(Change<? extends TableColumn<T, ?>> c) {
                resetColumnStyles();
            }
        });

        // Ajoute un écouteur aux changements dans la liste des items
        itemsProperty().addListener((observable, oldValue, newValue) -> {
            resetColumnStyles();
        });

        this.scrollTo(ligne);
    }

    private void resetColumnStyles() {
        Color couleurPick = FrameModule.colorPicker.getValue();
        String couleurClair = new Color(((couleurPick.getRed() - 0.05) <= 0 ? 0 : (couleurPick.getRed() - 0.05)),
                (couleurPick.getGreen() - 0.05) <= 0 ? 0 : (couleurPick.getGreen() - 0.05),
                (couleurPick.getBlue() - 0.05) <= 0 ? 0 : (couleurPick.getBlue() - 0.05), 1)
                .toString().replace("0x", "#");
        String couleurSombre = new Color(((couleurPick.getRed() - 0.1) <= 0 ? 0.08 : (couleurPick.getRed() - 0.1)),
                (couleurPick.getGreen() - 0.1) <= 0 ? 0.08 : (couleurPick.getGreen() - 0.1),
                (couleurPick.getBlue() - 0.1) <= 0 ? 0.08 : (couleurPick.getBlue() - 0.1), 1)
                .toString().replace("0x", "#");
        int columnIndex = 0;
        for (TableColumn<T, ?> col : getColumns()) {
            col.setStyle("-fx-background-color: " + (columnIndex % 2 == 0 ? couleurClair
                    : couleurSombre) + ";");
            if (col.getText().equals("supprimer"))
                col.setStyle(col.getStyle() +
                        ";-fx-alignment:CENTER;-fx-padding: 2 0 2 0px;");
            columnIndex++;
        }
    }

}
