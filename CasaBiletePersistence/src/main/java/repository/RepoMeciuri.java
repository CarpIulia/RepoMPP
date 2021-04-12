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

public class RepoMeciuri implements IRepoMeciuri {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepoMeciuri(Properties properties) {
        logger.info("Initializare RepoMeciuri cu proprietatile: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Iterable<Meci> findByEmptySeatsDesc() {
        return null;
    }

    @Override
    public void save(Meci meci) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Meci otherMeci) {
        Integer nrLocuri = otherMeci.getNrLocuriDisponibile();
        logger.traceEntry("actualizare nr. locuri meci {}", id);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("update Meciuri set nr_locuri_disponibile=? where id=?;")) {
            preparedStatement.setInt(1,nrLocuri);
            preparedStatement.setInt(2, id);
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
    public Iterable<Meci> getAll() {
        Connection con = dbUtils.getConnection();
        List<Meci> meciuri = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Meciuri")) {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    Integer id = result.getInt("id");
                    String echipa1 = result.getString("echipa_1");
                    String echipa2 = result.getString("echipa_2");
                    TipMeci tip = TipMeci.valueOf(result.getString("tip_meci"));
                    Double pret = result.getDouble("pret_bilet");
                    Integer nrLocuri = result.getInt("nr_locuri_disponibile");
                    Meci meci = new Meci(echipa1, echipa2, tip, pret, nrLocuri);
                    meci.setID(id);
                    meciuri.add(meci);
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
        logger.traceExit(meciuri);
        return meciuri;
    }

    @Override
    public Meci getOne(Integer id) {
        return null;
    }
}
