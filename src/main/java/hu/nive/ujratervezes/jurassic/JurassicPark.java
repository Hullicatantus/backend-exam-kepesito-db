package hu.nive.ujratervezes.jurassic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JurassicPark {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public JurassicPark(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <E> List<E> checkOverpopulation() {
        List <E> dinosaurNames = new ArrayList<>();

        try(Connection connection = getConnection()){
            String SQL = "SELECT breed FROM dinosaur WHERE actual > expected GROUP BY breed";

            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                dinosaurNames.add((E) resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return dinosaurNames;
    }
}

