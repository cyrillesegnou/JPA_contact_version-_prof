package org.antislashn.contacts;

import java.sql.SQLException;
import java.util.List;

import org.antislashn.contacts.dao.ContactDao;
import org.antislashn.contacts.dao.Dao;
import org.antislashn.contacts.entities.Contact;

public class ContactMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/contacts";
		String user = "root";
		String pswd = "P@ssw0rd";
		
		ContactDao dao = new ContactDao(new Dao(driver, url, user, pswd));
		
		List<Contact> contacts = dao.findAll();
		
		contacts.forEach(System.out::println);

	}

}
