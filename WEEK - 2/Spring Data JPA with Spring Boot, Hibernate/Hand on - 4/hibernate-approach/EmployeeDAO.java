package com.cognizant.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmployeeDAO {

    private static SessionFactory factory;

    static {
        try {

            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Integer addEmployee(Employee employee) {
        Session session = factory.openSession();   
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();       
            employeeID = (Integer) session.save(employee);  
            tx.commit();                           
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();         
            e.printStackTrace();
        } finally {
            session.close();                       
        }
        return employeeID;
    }

    public Employee getEmployee(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Employee employee = null;

        try {
            tx = session.beginTransaction();
            employee = session.get(Employee.class, id);  
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }

    public void updateEmployee(Employee employee) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(employee);             
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteEmployee(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);         
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
