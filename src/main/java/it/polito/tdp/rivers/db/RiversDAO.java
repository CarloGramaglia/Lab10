package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer,River>fiumi) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r = new River(res.getInt("id"), res.getString("name"));
				rivers.add(r);
				fiumi.put(res.getInt("id"), r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public List<Flow> getTutteLeMisurazioniPerFimue(River river, Map<Integer,River>fiumi) {
		String sql = "SELECT r.id, r.name, f.day, f.flow, f.river "
				+ "FROM river r, flow f "
				+ "WHERE r.id = ? AND f.river = ? AND r.id = f.river";
		
		List<Flow> misurazioni = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			st.setInt(2,river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				misurazioni.add(new Flow(res.getDate("day").toLocalDate(),res.getDouble("flow"),fiumi.get(res.getInt("river"))));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}
}
