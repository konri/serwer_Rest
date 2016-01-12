/*
package com.engineer.hibernate;

import com.engineer.model.Faculty;
import com.engineer.model.Message;
import com.engineer.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Konrad on 2015-05-31.
 *//*

public class SchoolServicesImp implements SchoolServices {

    private final SessionFactory factory = SessionUtil.getInstance().getFactory();

  */
/*  public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }
*//*

    @Override
    public void addTest(String msg) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Message msgObj = new Message();
        msgObj.setText(msg);
        session.save(msgObj);
        tx.commit();
        session.close();

    }

    @Override
    public void addFaculty(String name, String description) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Faculty faculty = new Faculty(name,description);
        session.save(faculty);
        tx.commit();
        session.close();
    }

    @Override
    public Faculty getFaculty(String name) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Faculty f where f.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Faculty faculty = (Faculty) query.uniqueResult();
        return faculty;
    }

    @Override
    public List<String> getFaculties() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Faculty f");
        List<Faculty> faculties = query.list();
        List<String> facultiesString = new ArrayList<>();
        for(Faculty f : faculties)
            facultiesString.add(f.getName());
        return facultiesString;
    }


    @Override
    public void addStudent(String name, String lastName, String facultyName) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student student = new Student();
        student.setFirstName(name);
        student.setLastName(lastName);
        student.setFaculty(saveFaculty(facultyName));
        session.save(student);

        tx.commit();
        session.close();

    }

    @Override
    public void addStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
    }

    @Override
    public void removeStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        if(student != null)
            session.delete(student);

        tx.commit();
        session.close();
    }

    @Override
    public Student getStudent(Integer id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Student s where s.idStudent=:idStudent");
        query.setParameter("idStudent",id);
        query.setMaxResults(1);
        Student student = (Student) query.uniqueResult();
        System.out.println(student);
        session.close();
        return student;
    }

    @Override
    public List<Student> getStudents(String faculty) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query;
        if(faculty.equals("all"))
            query = session.createQuery("from Student");
        else {
            query = session.createQuery("from Student s where s.faculty.name=:nameFaculty");
            query.setParameter("nameFaculty", faculty);
        }
        List<Student> students = query.list();
        session.close();
        return students;
    }


    private Faculty saveFaculty(String facultyName) {
        Faculty faculty = getFaculty(facultyName);
        if(faculty == null){
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            faculty = new Faculty(facultyName,"default description");
            session.save(faculty);
            tx.commit();
            session.close();
        }
        return faculty;
    }


}
*/
/*
package com.engineer.hibernate;

import com.engineer.model.Faculty;
import com.engineer.model.Message;
import com.engineer.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Konrad on 2015-05-31.
 *//*

public class SchoolServicesImp implements SchoolServices {

    private final SessionFactory factory = SessionUtil.getInstance().getFactory();

  */
/*  public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }
*//*

    @Override
    public void addTest(String msg) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Message msgObj = new Message();
        msgObj.setText(msg);
        session.save(msgObj);
        tx.commit();
        session.close();

    }

    @Override
    public void addFaculty(String name, String description) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Faculty faculty = new Faculty(name,description);
        session.save(faculty);
        tx.commit();
        session.close();
    }

    @Override
    public Faculty getFaculty(String name) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Faculty f where f.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Faculty faculty = (Faculty) query.uniqueResult();
        return faculty;
    }

    @Override
    public List<String> getFaculties() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Faculty f");
        List<Faculty> faculties = query.list();
        List<String> facultiesString = new ArrayList<>();
        for(Faculty f : faculties)
            facultiesString.add(f.getName());
        return facultiesString;
    }


    @Override
    public void addStudent(String name, String lastName, String facultyName) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student student = new Student();
        student.setFirstName(name);
        student.setLastName(lastName);
        student.setFaculty(saveFaculty(facultyName));
        session.save(student);

        tx.commit();
        session.close();

    }

    @Override
    public void addStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
    }

    @Override
    public void removeStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        if(student != null)
            session.delete(student);

        tx.commit();
        session.close();
    }

    @Override
    public Student getStudent(Integer id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Student s where s.idStudent=:idStudent");
        query.setParameter("idStudent",id);
        query.setMaxResults(1);
        Student student = (Student) query.uniqueResult();
        System.out.println(student);
        session.close();
        return student;
    }

    @Override
    public List<Student> getStudents(String faculty) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query query;
        if(faculty.equals("all"))
            query = session.createQuery("from Student");
        else {
            query = session.createQuery("from Student s where s.faculty.name=:nameFaculty");
            query.setParameter("nameFaculty", faculty);
        }
        List<Student> students = query.list();
        session.close();
        return students;
    }


    private Faculty saveFaculty(String facultyName) {
        Faculty faculty = getFaculty(facultyName);
        if(faculty == null){
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            faculty = new Faculty(facultyName,"default description");
            session.save(faculty);
            tx.commit();
            session.close();
        }
        return faculty;
    }


}
*/
