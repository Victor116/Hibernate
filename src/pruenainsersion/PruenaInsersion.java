package pruenainsersion;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/** @author VICTOR  */
public class PruenaInsersion {

    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    
    /** @param args the command line arguments */
    public static void main(String[] args) {
        System.err.println("Iniciando" );
        try{
            Configuration configuration = new Configuration();
            System.err.println("Leyendo configuracion." );
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
            factory = configuration.buildSessionFactory(serviceRegistry);
            System.err.println("Conexion estrablecida." );
        }catch (Throwable ex) { 
           System.err.println("No se puede crear la Sesion" + ex);
           throw new ExceptionInInitializerError(ex); 
        }
        
        /* Actividad 3_1 Persistencia Hibernate 1/2*/
        addAlumnoConCiudad(); //Crear e insertar alumnos
        listAlumnosPorCiudad(4); //Traer Alumnos pertenecientes a una ciudad
        listAlumnos20Anios(); //Trae Alumnos menores a 20 años de edad
        
        /* Actividad 3_1 Persistencia Hibernate 2/2*/
        insertAlumno("Santos", "M", 33, "El Jobo"); //Ingresamos un dato
        listAlumnos();
        
        
    }
    
    /* Crea nuevos alumnos */
    public static void addAlumnoConCiudad(){        
        Session session = factory.openSession();
        Transaction tx = null;
        Integer daoID = null;
        try{
            tx = session.beginTransaction();
            Ciudad ciudad = new Ciudad("Tuxtla");
            Ciudad ciudad1 = new Ciudad("Coatza");
            Ciudad ciudad2 = new Ciudad("Ecatepet");
            
            Alumno alumno1 = new Alumno("Luis", "M", 18, ciudad);
            Alumno alumno2 = new Alumno("Raul", "M", 20, ciudad1);
            Alumno alumno3 = new Alumno("Laura", "F", 19, ciudad1);
            Alumno alumno4 = new Alumno("Mary", "F", 20, ciudad2);
            
            session.save(alumno1);
            session.save(alumno2);
            session.save(alumno3);
            session.save(alumno4);
            
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
        }finally {
           session.close(); 
        }
    }
    
    /* Lista Alumnos dependiendo el id de una ciudad */
    public static void listAlumnosPorCiudad(int idCiudad) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Alumno WHERE alumnociudad_ciudad_id=" + String.valueOf(idCiudad)).list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Alumno dao = (Alumno) iterator.next();
                System.out.print("Nombre:" + dao.getAlumnoName() );
                System.out.print("  Sexo: " + dao.getAlumnoSexo());
                System.out.println("  Edad: " + dao.getAlumnoEdad());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /* Listar Alumnos menores a 20 años de edad */
    public static void listAlumnos20Anios() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Alumno WHERE edad < 20").list();
            
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Alumno dao = (Alumno) iterator.next();
                System.out.print("Nombre:" + dao.getAlumnoName());
                System.out.print("  Sexo: " + dao.getAlumnoSexo());
                System.out.print("  Edad: " + dao.getAlumnoEdad());
                System.out.println("  Ciudad " + dao.getAlumnoCiudad().getNombre());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /*Insertar un solo alumno*/
    public static void insertAlumno(String nombre, String sexo, int edad, String ciudad){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer daoID = null;
        try{
            tx = session.beginTransaction();
            Ciudad ciudad2 = new Ciudad( ciudad );
            Alumno alumno = new Alumno( nombre, sexo, edad, ciudad2 );

            session.save(alumno);
        tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
        }finally {
           session.close(); 
        }
    }
    
    /* Obtiene todos los alumnos */
    public static void listAlumnos( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           List employees = session.createQuery("FROM Alumno").list(); 
           for (Iterator iterator = 
                employees.iterator();
                iterator.hasNext();){
                Alumno dao = (Alumno) iterator.next(); 
                System.out.print("Nombre:" + dao.getAlumnoName());
                System.out.print("  Sexo: " + dao.getAlumnoSexo());
                System.out.println("  Edad: " + dao.getAlumnoEdad());
           }
         tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
    }
    
    /* Actualiza la edad*/
    public void updateAlumno( Integer AlumnoID, String nombre, String sexo, int edad ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID); 
           dao.setEdad( edad );
                session.update(dao); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
    }
}
