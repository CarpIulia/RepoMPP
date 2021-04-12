package repository;

import domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RepoCasieri implements IRepoCasieri {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepoCasieri(Properties properties) {
        logger.info("Initializare RepoCasieri cu proprietatile: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Boolean checkUsernameAndPassword(Casier casier) {
        String numeUtilizator = casier.getNumeUtilizator();
        String parola = casier.getParola();
        int nr=0;
        logger.traceEntry("verificare existenta casier cu credentialele {}", casier);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select count(*) as nr from Casieri where nume_utilizator=? and parola=?")) {
            preparedStatement.setString(1, numeUtilizator);
            preparedStatement.setString(2, parola);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                nr = resultSet.getInt("nr");
            }
        } catch (SQLException ex) {
            logger.error("Exceptie: " + ex);
            System.out.println("Eroare BD " + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Exceptie: " + ex);
                System.out.printf("Eroare BD " + ex);
            }
        }
        if(nr == 0) {
            logger.traceExit("nu s-a gasit un casier cu credentialele {}", casier);
            return false;
        }
        logger.traceExit("s-a gasit un casier cu credentialele {}", casier);
        return true;
    }

    @Override
    public void save(Casier casier) {
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public void update(Integer id, Casier otherCasier) {

    }

    @Override
    public Iterable<Casier> getAll() {
        return null;
    }

    @Override
    public Casier getOne(Integer id) {
        return null;
    }
}
