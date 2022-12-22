package ru.aora.erp.repository.gateway;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.aora.erp.model.entity.business.Contract;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SpecContractQuery {
    private static String result="";
public String SpecContractQuery(Contract contract)  {
        Session session = null;

        try{
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session =sessionFactory.openSession();
            session.beginTransaction();
            String hql = "Select SUM(DbContract.contractSum) from DbContract where DbContract.counteragentId=:counteragentId";
            Query query = session.createQuery(hql);
            query.setParameter("counteragentId", contract.getCounteragentId());
            result = (query.toString());

        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    return result;
}
}
