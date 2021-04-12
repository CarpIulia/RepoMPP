package repository;

import domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoBilete implements IRepoBilete{
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepoBilete(Properties properties) {
        logger.info("Initializare RepoBilete cu proprietatile: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public void save(Bilet bilet) {
        logger.traceEntry("adaugare bilet nou {}", bilet);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Bilete values (?,?,?,?)")) {
            //preparedStatement.setInt(1, bilet.getID());
            preparedStatement.setInt(2, bilet.getMeci());
            preparedStatement.setString(3, bilet.getNumeClient());
            preparedStatement.setInt(4, bilet.getNrLocuriCumparate());
            int result = preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            logger.error("Exceptie: " + ex);
            System.out.printf("Eroare BD " + ex);
        }
        finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Exceptie: " + ex);
                System.out.printf("Eroare BD " + ex);
            }
        }
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Bilet otherE) {

    }

    @Override
    public Iterable<Bilet> getAll() {
        Connection con = dbUtils.getConnection();
        List<Bilet> bilete = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Bilete")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    Integer id = result.getInt("id");
                    Integer meci = result.getInt("meci");
                    String numeClient = result.getString("nume_client");
                    Integer nrLocuriCumparate = result.getInt("nr_locuri_cumparate");
                    Bilet bilet = new Bilet(meci, numeClient, nrLocuriCumparate);
                    bilet.setID(id);
                    bilete.add(bilet);
                }
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
        logger.traceExit(bilete);
        return bilete;
    }

    @Override
    public Bilet getOne(Integer id) {
        return null;
    }
}
