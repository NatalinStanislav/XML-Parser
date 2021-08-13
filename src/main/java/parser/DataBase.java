package parser;

import java.sql.*;
import java.util.Collection;

public class DataBase {

    public static Connection getConnection() throws SQLException {
        String username = Config.get().getDatabaseUsername();
        String password = Config.get().getDatabasePassword();
        String dbUrl = Config.get().getDatabaseUrl();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void saveCompanies(Collection<Company> companies) {
        try (PreparedStatement ps = getConnection().prepareStatement(
                "INSERT INTO companies (id, name, location) VALUES (?,?,?) ON CONFLICT ON CONSTRAINT company_duplicate DO NOTHING")) {
            for (Company company : companies) {
                ps.clearParameters();
                ps.setString(1, company.getId());
                ps.setString(2, company.getName());
                ps.setString(3, company.getLocation());
                ps.addBatch();
            }
            ps.clearParameters();
            int[] results = ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
