package persistence;

//import model.Patient;
//import model.Unit;
import bean.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UnitDAO implements DAO<Unit> {

    java.sql.Connection conn;
    private static final String INSERT_SQL = "insert into unit values (?,?);";
    private static final String UPDATE_SQL = "update unit set unitID = ?, name = ? where unitID = ?;";
    private static final String DELETE_SQL = "delete from unit where unitID = ?;";

    public UnitDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<Unit> get(String ID) throws SQLException {

        List<Unit> units = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from unit where unitID = \"" + ID + "\"");

        return getUnitList(units, statement, resultSet);
    }

    @Override
    public List<Unit> getAll() throws SQLException {

        List<Unit> units = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from unit");

        return getUnitList(units, statement, resultSet);
    }

    private List<Unit> getUnitList(List<Unit> units, Statement statement, ResultSet resultSet) throws SQLException {

        while(resultSet.next()) {

            String unitID = resultSet.getString("unitID");
            String unitName = resultSet.getString("name");
            Unit unit = new Unit();
            unit.setID(unitID);
            unit.setName(unitName);
            units.add(unit);
        }

        statement.close();
        resultSet.close();

        return units;
    }

    @Override
    public void save(Unit unit) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);

        statement.setString(1, unit.getID());
        statement.setString(2, unit.getName());

        statement.executeUpdate();
    }

    @Override
    public void update(Unit Unit) throws SQLException {

    }

    @Override
    public void delete(Unit unit) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(DELETE_SQL);

        statement.setString(1, unit.getID());

        statement.executeUpdate();
    }
}
