import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

public class StartSpringServer {

    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartSpringServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        System.out.println("Server started...");
    }
}
