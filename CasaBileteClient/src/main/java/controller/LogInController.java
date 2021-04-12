package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import services.IServices;


import java.io.IOException;
import java.io.Serializable;

public class LogInController implements Serializable {
    @FXML
    private TextField textFieldNumeUtilizator;
    @FXML
    private PasswordField passwordFieldParola;

    private MainWindowController mainWindowController;

    private IServices srv;

    private Stage currentStage;

    private Stage dialogStage;

    @FXML
    public void initialize() {
        FXMLLoader mainWindowLoader = new FXMLLoader();
        mainWindowLoader.setLocation(getClass().getResource("/views/mainWindowView.fxml"));
        AnchorPane mainWindowLayout = null;
        try {
            mainWindowLayout = mainWindowLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogStage = new Stage();
        dialogStage.setTitle("Vanzare bilete");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(mainWindowLayout);
        dialogStage.setScene(scene);

        dialogStage.getIcons().add(new Image("/images/baschet.png"));

        this.mainWindowController = mainWindowLoader.getController();
    }

    public void setService(IServices srv) {
        this.srv = srv;
    }

    public void setStage(Stage primaryStage) {
        this.currentStage = primaryStage;
    }

    public void handleConecatre(ActionEvent actionEvent) {
        String numeUtilizator = textFieldNumeUtilizator.getText();
        String parola = passwordFieldParola.getText();
        if(srv.checkUsernameAndPassword(numeUtilizator, parola, mainWindowController)) {
            showMainWindow(numeUtilizator);
            this.currentStage.close();
            textFieldNumeUtilizator.clear();
            passwordFieldParola.clear();
        }
        else
        {
            Message.showErrorMessage(null, "Nume de utilizator sau parola incorecta!");
            textFieldNumeUtilizator.clear();
            passwordFieldParola.clear();
        }

    }

    private void showMainWindow(String numeUtilizator) {
        mainWindowController.setService(this.srv);
        mainWindowController.setStages(this.currentStage, dialogStage);
        mainWindowController.setUser(numeUtilizator);

        dialogStage.show();
    }

}
