package vue;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModuleIHM {

	private Button ajouterProf;
	private TextField nom;
	private TextField nbSemaines;
	private TextField hTD;
	private TextField hTP;
	private TextField hCM;
	private Button supprimer;

	public ModuleIHM(Button ajouterProf, TextField nom, TextField nbSemaines, TextField hTD, TextField hTP, TextField hCM, Button supprimer) {
		this.ajouterProf = ajouterProf;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.hTD = hTD;
		this.hTP = hTP;
		this.hTP = hCM;
		this.supprimer = supprimer;
	}

// source action, generate setter, getter

}
/*
TableView<Module> table = new TableView<Module>();

// Editable
table.setEditable(true);

TableColumn<Module, String> nomCol //
		= new TableColumn<Module, String>("Full Name");

// ==== FULL NAME (TEXT FIELD) ===
nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));

nomCol.setCellFactory(TextFieldTableCell.<Module> forTableColumn());

nomCol.setMinWidth(200);

// On Cell edit commit (for Nom column)
nomCol.setOnEditCommit((CellEditEvent<Module, String> event) ->
{
	TablePosition<Module, String> pos = event.getTablePosition();

	String newNom = event.getNewValue();

	int row = pos.getRow();
	Module Module = event.getTableView().getItems().get(row);

	Module.setNom(newNom);
});
*/