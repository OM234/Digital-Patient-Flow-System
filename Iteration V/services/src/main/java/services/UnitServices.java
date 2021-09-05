package services;

import bean.Unit;
import persistence.DAO;

import java.sql.SQLException;
import java.util.List;

public class UnitServices {

    private final DAO<Unit> unitDAO;

    public UnitServices(DAO<Unit> unitDAO) {
        this.unitDAO = unitDAO;
    }

    public List<Unit> getAllUnits() throws SQLException {
        return unitDAO.getAll();
    }

    public Unit getUnit(String unitID) throws SQLException {
        if(!hasUnit(unitID)) {
            return null;
        } else {
            return unitDAO.get(unitID).get(0);
        }
    }

    public boolean addUnit(Unit unit) throws SQLException {
        if(hasUnit(unit)) {
            return false;
        } else {
            unitDAO.save(unit);
            return true;
        }
    }

    public boolean removeUnit(Unit unit) throws SQLException {
        if(!hasUnit(unit)) {
            return false;
        } else {
            unitDAO.delete(unit);
            return true;
        }
    }

    public boolean hasUnit(Unit unit) throws SQLException {
        if(!unitDAO.get(unit.getID()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasUnit(String unitID) throws SQLException {
        if(!unitDAO.get(unitID).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
