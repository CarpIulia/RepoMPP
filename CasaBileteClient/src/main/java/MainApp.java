import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

import java.io.IOException;

public class MainApp extends Application {

    private IServices server;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");

        try {
           ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
           this.server = (IServices)factory.getBean("casaBileteService") ;
           System.out.println("Obtained a reference to remote casa bilete server");

           initView(primaryStage);
           primaryStage.setWidth(400);
           primaryStage.setHeight(600);
           primaryStage.show();
       } catch (Exception e) {
           System.err.println("Casa bilete Initialization  exception:"+e);
           e.printStackTrace();
       }

    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader logInLoader = new FXMLLoader();
        logInLoader.setLocation(getClass().getResource("/views/logInView.fxml"));
        AnchorPane logInLayout = logInLoader.load();
        primaryStage.setScene(new Scene(logInLayout));
        primaryStage.getIcons().add(new Image("/images/baschet.png"));

        LogInController logInController = logInLoader.getController();
        logInController.setService(server);
        logInController.setStage(primaryStage);
    }
}
