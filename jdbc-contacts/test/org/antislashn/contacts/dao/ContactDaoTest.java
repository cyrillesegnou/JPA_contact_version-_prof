package org.antislashn.contacts.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.antislashn.contacts.entities.Contact;
import org.junit.Before;
import org.junit.Test;

public class ContactDaoTest {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/contacts";
	String user = "root";
	String pswd = "P@ssw0rd";
	
	Dao d = null;
	
	@Before
	public void init() throws ClassNotFoundException {
		d  = new Dao(driver, url, user, pswd);
	}

	@Test
	public void testContactDao() {
		ContactDao dao = new ContactDao(d);
		assertNotNull(dao);
	}

	@Test
	public void testFindById() throws SQLException {
		ContactDao dao = new ContactDao(d);
		List<Contact> contacts = dao.findAll();
		Contact c1 = contacts.get(0);
		if(c1!=null) {
			Contact c2 = dao.findById(c1.getId());
			assertNotNull(c2);
			assertEquals(c1, c2);
		}
		else {
			fail("Base vide imposible de vérifier");
		}
	}

	@Test
	public void testFindAll() throws SQLException {
		ContactDao dao = new ContactDao(d);
		List<Contact> contacts = dao.findAll();
		assertNotNull(contacts);
		if(contacts.isEmpty()) {
			fail("Base vide imposible de vérifier");
			return;
		}
		assertTrue(contacts.size()!=0);
	}

	@Test
	public void testSave() throws SQLException {
		ContactDao dao = new ContactDao(d);
		Contact c1 = new Contact("M", "Foo", "Toto");
		assertTrue(dao.save(c1));
		assertNotEquals(0, c1.getId());
	}

	@Test
	public void testUpdate() throws SQLException {
		ContactDao dao = new ContactDao(d);
		List<Contact> contacts = dao.findAll();
		Contact c1 = contacts.get(contacts.size()-1);
		c1.setCivilite("FOO");
		c1.setNom("FOOBAR");
		c1.setPrenom("BARBAR");
		assertTrue(dao.update(c1));
		Contact c2 = dao.findById(c1.getId());
		assertEquals(c1, c2);
	}

	@Test
	public void testRemove() throws SQLException {
		ContactDao dao = new ContactDao(d);
		Contact c1 = new Contact("M", "Foo", "Toto");
		dao.save(c1);
		long id = c1.getId();
		assertTrue(dao.remove(c1));
		assertNull(dao.findById(id));
		assertEquals(0, c1.getId());
		
	}

}
