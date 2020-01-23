package org.antislashn.contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.antislashn.contacts.entities.Contact;

public class ContactDao {
	private Dao dao;
	
	public ContactDao(Dao dao) {
		this.dao = dao;
	}
	
	public Contact findById(long id) throws SQLException{
		Contact contact = null;
		String sql = "SELECT * FROM personnes WHERE pk=?";
		Connection connection = dao.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			contact = mapToContact(rs);
		}
		dao.close(connection);
		return contact;
	}
	
	public List<Contact> findAll() throws SQLException{
		List<Contact> contacts = new ArrayList<Contact>();
		String sql = "SELECT * FROM personnes";
		Connection connection = dao.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			contacts.add(mapToContact(rs));
		}
		dao.close(connection);
		return contacts;
	}
	
	public boolean save(Contact contact) throws SQLException {
		String sql = "INSERT INTO personnes (civilite,nom,prenom) VALUES (?,?,?)";
		Connection connection = dao.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);		
		ps.setString(1, contact.getCivilite());
		ps.setString(2, contact.getNom());
		ps.setString(3, contact.getPrenom());
		long nb = ps.executeUpdate();
		if(nb==0) {
			dao.close(connection);
			return false;
		}
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			contact.setId(rs.getLong(1));
		}
		dao.close(connection);
		return true;
	}
	
	public boolean update(Contact contact) throws SQLException {
		String sql = "UPDATE personnes SET civilite=?,nom=?,prenom=? WHERE pk=?";
		Connection connection = dao.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);		
		ps.setString(1, contact.getCivilite());
		ps.setString(2, contact.getNom());
		ps.setString(3, contact.getPrenom());
		ps.setLong(4, contact.getId());
		long nb = ps.executeUpdate();
		if(nb==0) {
			dao.close(connection);
			return false;
		}
		dao.close(connection);
		return true;
	}	
	
	public boolean remove(Contact contact) throws SQLException {
		String sql = "DELETE FROM personnes WHERE pk=?";
		Connection connection = dao.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);		
		ps.setLong(1, contact.getId());
		long nb = ps.executeUpdate();
		if(nb==0) {
			dao.close(connection);
			return false;
		}
		contact.setId(0);
		dao.close(connection);
		return true;	
	}

	private Contact mapToContact(ResultSet rs) throws SQLException {
		Contact c = new Contact();
		c.setCivilite(rs.getString("civilite"));
		c.setId(rs.getLong("pk"));
		c.setNom(rs.getString("nom"));
		c.setPrenom(rs.getString("prenom"));
		return c;
	}
	
	
	

}
