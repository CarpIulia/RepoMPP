package controller;

import domain.*;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.IObserver;
import services.IServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindowController extends UnicastRemoteObject implements IObserver, Serializable {
    ObservableList<Meci> modelMeciuri = FXCollections.observableArrayList();
    @FXML
    private Spinner spinnerNrLocuri;
    @FXML
    private TableView tableViewMeciuri;
    @FXML
    private TableColumn<Meci, String> columnEch1;
    @FXML
    private TableColumn<Meci, String> columnEch2;
    @FXML
    private TableColumn<Meci, TipMeci> columnTip;
    @FXML
    private TableColumn<Meci, Double> columnPret;
    @FXML
    private TableColumn<Meci, Integer> columnLocDisp;
    @FXML
    private TextField textFieldMeci;
    @FXML
    private TextField textFieldNumeClient;
    @FXML
    private CheckBox checkBoxSoratre;
    @FXML
    private Button buttonCumpara;

    private IServices srv;
    private Stage currentStage;
    private Stage primaryStage;
    private String user;

    public MainWindowController() throws RemoteException {
    }

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1);
        spinnerNrLocuri.setValueFactory(spinnerValueFactory);

        columnEch1.setCellValueFactory(new PropertyValueFactory<Meci, String>("echipa1"));
        columnEch2.setCellValueFactory(new PropertyValueFactory<Meci, String>("echipa2"));
        columnTip.setCellValueFactory(new PropertyValueFactory<Meci, TipMeci>("tip"));
        columnPret.setCellValueFactory(new PropertyValueFactory<Meci, Double>("pretBilet"));
        columnLocDisp.setCellValueFactory(new PropertyValueFactory<Meci, Integer>("nrLocuriDisponibile"));
        tableViewMeciuri.setItems(modelMeciuri);
    }
    public void setService(IServices srv) {
        this.srv = srv;
        initModelMeciuri();
    }

    private void initModelMeciuri() {
        Iterable<Meci> meciuri = srv.getAllMeciuri();
        List<Meci> meciuriList = StreamSupport.stream(meciuri.spliterator(), false)
                .collect(Collectors.toList());
        modelMeciuri.setAll(meciuriList);
    }

    public void handleMeciSelectat(MouseEvent mouseEvent) {
        Meci meci= (Meci) tableViewMeciuri.getSelectionModel().getSelectedItem();
        if(meci.getNrLocuriDisponibile() == 0) {
            this.textFieldMeci.setText("SOLD OUT!");
            this.textFieldMeci.setStyle("-fx-text-fill: red");
            this.textFieldNumeClient.setEditable(false);
            this.buttonCumpara.setDisable(true);
        }
        else {
            this.textFieldMeci.setStyle("-fx-text-fill: black");
            this.textFieldMeci.setText(meci.getEchipa1() + " VS " + meci.getEchipa2());
            this.textFieldMeci.setEditable(false);
            textFieldNumeClient.setEditable(true);
            this.buttonCumpara.setDisable(false);
        }

    }

    public void handleStergeTextFielduri() {
        this.textFieldNumeClient.clear();
        this.spinnerNrLocuri.getValueFactory().setValue(1);
        this.tableViewMeciuri.getSelectionModel().clearSelection();
        this.textFieldMeci.clear();
    }

    public void handleCumparaBilet(ActionEvent actionEvent) {
        Meci meci= (Meci) tableViewMeciuri.getSelectionModel().getSelectedItem();
        if(meci != null) {
            Integer locuriCumparate = (Integer) this.spinnerNrLocuri.getValueFactory().getValue();
            Integer idMeci = meci.getID();
            String numeClient = this.textFieldNumeClient.getText();
            srv.updateMeci(idMeci, meci.getNrLocuriDisponibile() - locuriCumparate);
            initModelMeciuri();
            srv.addBilet(idMeci, numeClient, locuriCumparate);
            Message.showMessage(null, Alert.AlertType.CONFIRMATION,"Achizitionare bilet","Biletul a fost cumparat.");
            handleStergeTextFielduri();
        }
        else
            Message.showErrorMessage(null, "Trebuie sa selectati un meci.");
    }

    public void handleSortare(ActionEvent actionEvent) {
        if(checkBoxSoratre.isSelected()) {
            Iterable<Meci> meciuri = srv.getAllMeciuri();
            List<Meci> meciuriList = StreamSupport.stream(meciuri.spliterator(), false)
                    .sorted(Comparator.comparingInt(Meci::getNrLocuriDisponibile).reversed())
                    .collect(Collectors.toList());
            modelMeciuri.setAll(meciuriList);
        }
        else
            initModelMeciuri();
    }

    public void handleDelogare(ActionEvent actionEvent) {
        currentStage.close();
        primaryStage.show();
        srv.logOut(user);
    }

    public void setStages(Stage primaryStage, Stage currentStage) {
        this.primaryStage = primaryStage;
        this.currentStage = currentStage;
    }

    public void setUser(String numeUtilizator) {
        this.user = numeUtilizator;
    }


    @Override
    public void actualizareLocuri(Meci meci) {
        Platform.runLater(() -> {
                initModelMeciuri();
        });
    }
}