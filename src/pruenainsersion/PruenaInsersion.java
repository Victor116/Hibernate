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
        addAlumnoConCiudad(); /* Crear e insertar alumnos */
        listAlumnos();
                
        listAlumnosPorCiudad(4); /* Traer Alumnos pertenecientes a una ciudad */
        
        listAlumnos20Anios(); /* Trae Alumnos menores a 20 años de edad */ 
        
        /* Actividad 3_1 Persistencia Hibernate 2/2*/
        insertAlumno("Batman", "M", 33, "La Baticueva");/* Ingresamos un nuevo Alumno */
        insertAlumno("Yu-Gi-Oh", "M", 18, "Desierto");/* Ingresamos un nuevo Alumno */
        listAlumnos();
        
        updateAlumno(3, "Barbie", "F", 20,"Coita"); /* Actualizar un Alumno */
        listAlumnos();
        
        deleteAlumno(8); /* Eliminar un Alumno */
        listAlumnos();
        
    }
    
    /* Crea nuevos alumnos */
    public static void addAlumnoConCiudad(){     
        
        System.err.println("Añadiendo Alumnos" );
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
        System.err.println("Listado de Alumnos pertenecientes a una Ciudad" );
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
        } finally {
            session.close();
        }
    }
    
    /* Listar Alumnos menores a 20 años de edad */
    public static void listAlumnos20Anios() {
        System.err.println("Listado de Alumnos menores de 20 años de edad" );
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
        } finally {
            session.close();
        }
    }
    
    /*Insertar un solo alumno*/
    public static void insertAlumno(String nombre, String sexo, int edad, String ciudad){
        System.err.println("Insertar un Alumno" );
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Ciudad ciudad2 = new Ciudad( ciudad );
            Alumno alumno = new Alumno( nombre, sexo, edad, ciudad2 );
            
            session.save(alumno); 
            System.out.println("Se añadio al Alumno: "+ nombre);
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
        }finally {
           session.close(); 
        }
    }
    
    /* Obtiene todos los alumnos */
    public static void listAlumnos( ){
        System.err.println("Listado de Alumnos existentes" );
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
        }finally {
           session.close(); 
        }
    }
    
    /* Actualiza la edad*/
    public static void updateAlumno( long AlumnoID, String nombre, String sexo, int edad, String city ){
        System.err.println("Actualizacion de un Alumno" );
        Session session = factory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            
            Ciudad ciudad = new Ciudad( city );
            Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID);
            String name = dao.getAlumnoName();
            dao.setAlumnoName( nombre );
            dao.setAlumnoSexo( sexo );
            dao.setAlumnoEdad( edad );
            dao.setAlumnoCiudad( ciudad );
            session.update(dao);
            System.out.println("Se actulizaron los del Alumno: "+ name +" a "+ nombre);

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
    }
    
    /* Borra alumno */
    public static void deleteAlumno(long AlumnoID){
        System.err.println("Eliminacion de un Alumno" );
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID); 
            session.delete(dao); 
            System.out.println("Se elimino al Alumno: "+ dao.getAlumnoName());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
    }
}
