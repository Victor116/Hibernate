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
//        addAlumnoConCiudad(); /* Crear e insertar alumnos */
//        listAlumnos();
//                
//        listAlumnosPorCiudad(4); /* Traer Alumnos pertenecientes a una ciudad */
//        
//        listAlumnos20Anios(); /* Trae Alumnos menores a 20 años de edad */ 
        
        /* Actividad 3_1 Persistencia Hibernate 2/2*/
//        insertAlumno("Batman", "M", 33, "La Baticueva");/* Ingresamos un nuevo Alumno */
//        insertAlumno("Yu-Gi-Oh", "M", 18, "Baticueva");/* Ingresamos un nuevo Alumno */
//        listAlumnos();
//        
//        updateAlumno(3, "Barbie", "F", 20,"Coita"); /* Actualizar un Alumno */
//        listAlumnos();
//        
//        deleteAlumno(8); /* Eliminar un Alumno */
//        listAlumnos();
        
        /* Actividad 3_2 Persistencia Hibernate */
        // Insercion de n cantidad de alumnos
        InsertarNAlumnos( 1000 );
        //listAlumnos();
        
        // Select * FROM ciudad, alumno where ciudad.idCiudad = alumno.idCiudad and idCiudad = ALEATORIO
        SeleccionarCiudades(1000);
        
        // Updatealumno set edad = ALEATORIO whereidAlumno=ALEATORIO
        ActualizarAlumnos(1000);
        
        EliminarAlumnos(1000);     
    }
    
    /* Insertar n cantidad de alumnos */
    public static void InsertarNAlumnos(int cantidadInserciones){
        
        //Insertamos 1100 alumnos con sus respectivas ciudad
        System.err.println("Insercion de alumnos");
        String name = "Batman", sex = "", ciudad = "";
        int edad = 0;
//        long startTime = System.currentTimeMillis();
        
        for(int a = 0; a < cantidadInserciones; a++){
            edad = (int) (Math.random() * 60) + 1;
            
            if( a % 2 == 0 )
                sex = "M";
            else 
                sex = "F";
            
            if( a < 200 )
                ciudad = "La Baticueva";
            else if ( a >= 200 && a < 500 )
                ciudad = "Asgardia";
            else if ( a >= 500 && a < 800 )
                ciudad = "Suchiapa";
            else if ( a >= 800 && a < 1000 )
                ciudad = "Alamedas";
            else if ( a >= 1000 )
                ciudad = "Puerto Rico";
            
            insertAlumno(name+a, sex, edad, ciudad);/* Ingresamos un nuevo Alumno */
        }
        
//        long endTime = System.currentTimeMillis() - startTime; // tiempo en que se ejecuta su for 
//        System.err.println("Tiempo ejecucion Seleccionar: "+ (endTime / 60) +"segundos");
    }
    
    public static void SeleccionarCiudades(int cantidadInserciones){
        System.err.println("Selccionar "+ cantidadInserciones +" ciudades");
        long ciudadId = 0;
        
        long startTime = System.currentTimeMillis(); // tiempo en que se ejecuta su for 
        
        for(int a = 0; a < cantidadInserciones; a++){
            ciudadId = (int) (Math.random() * cantidadInserciones) + 1;
            listAlumnosPorCiudad(ciudadId);
        }
        
        long endTime = System.currentTimeMillis() - startTime; // tiempo en que se ejecuta su for 
        System.err.println("Tiempo ejecucion Seleccionar Ciudades: "+ (endTime) +"milisegundos");
    }
       
    public static void ActualizarAlumnos(int cantidadInserciones){
        System.err.println("Actualizar "+ cantidadInserciones +" alumnos");
        int edad = 0;
        long alumno = 0;
        
        long startTime = System.currentTimeMillis(); // tiempo en que se ejecuta su for 
        
        for(int a = 0; a < cantidadInserciones; a++){
            edad = (int) (Math.random() * 60) + 1;
            alumno = (int) (Math.random() * cantidadInserciones) + 1;
            updateAlumnoEdad(alumno, edad);
        }
        
        long endTime = System.currentTimeMillis() - startTime; // tiempo en que se ejecuta su for 
        System.err.println("Tiempo ejecucion Actualizar Alumnos: "+ (endTime) +"milisegundos");
    }
    
    public static void EliminarAlumnos(int cantidadInserciones){
        System.err.println("Eliminar "+ cantidadInserciones +" alumnos");
        long alumnoid = 0;
        
        long startTime = System.currentTimeMillis(); // tiempo en que se ejecuta su for 
        
        for(int a = 0; a < cantidadInserciones; a++){
            alumnoid = (int) (Math.random() * cantidadInserciones) + 1;
            deleteAlumno(alumnoid);
        }
        
        long endTime = System.currentTimeMillis() - startTime; // tiempo en que se ejecuta su for 
        System.err.println("Tiempo ejecucion Eliminar Alumnos: "+ (endTime) +"milisegundos");
    }

    /* Crea nuevos alumnos */
    public static void addAlumnoConCiudad(){     
        
        System.err.println("Añadiendo Alumnos" );
        Session session = factory.openSession();
        Transaction tx = null;
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
    public static void listAlumnosPorCiudad(long idCiudad) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Ciudad dao = (Ciudad)session.get(Ciudad.class, idCiudad); 
            
            if(dao != null){
//                System.out.println("Nombre Ciudad:" + dao.getNombre() + "ID Ciudad:" + dao.getCiudadID() );
            }
            
//            List employees = session.createQuery( 
//                "FROM Alumno WHERE alumnociudad_ciudad_id=" + String.valueOf(idCiudad)).list();
//            
//            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
//                Alumno dao2 = (Alumno) iterator.next();
//                System.out.print("Nombre:" + dao2.getAlumnoName() );
//                System.out.print("  Sexo: " + dao2.getAlumnoSexo() );
//                System.out.println("  Edad: " + dao2.getAlumnoEdad() );
//            }

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

        Session session = factory.openSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();
            Ciudad ciudad2 = new Ciudad( ciudad );
            Alumno alumno1 = new Alumno(nombre, sexo, edad, ciudad2);
            session.save(alumno1);
            
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
        }finally {
           session.close(); 
        }
    }
    
    /* Obtiene todos los alumnos */
    public static void listAlumnos( ){
        //System.err.println("Listado de Alumnos existentes" );
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
        Session session = factory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            
            Ciudad ciudad = new Ciudad( city );
            Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID);
            dao.setAlumnoName( nombre );
            dao.setAlumnoSexo( sexo );
            dao.setAlumnoEdad( edad );
            dao.setAlumnoCiudad( ciudad );
            session.update(dao);

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
    }
    
    /* Actualiza la edad*/
    public static void updateAlumnoEdad( long AlumnoID, int edad ){
        Session session = factory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            
            Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID);
            if(dao != null){
                dao.setAlumnoEdad( edad );
                session.update(dao); 
            }
            
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
    }
    
    /* Borra alumno */
    public static void deleteAlumno(long AlumnoID){
        //System.err.println("Eliminacion de un Alumno" );
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Alumno dao = (Alumno)session.get(Alumno.class, AlumnoID); 
            if(dao != null)
                session.delete(dao); 
            
            //System.out.println("Se elimino al Alumno: "+ dao.getAlumnoName());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
    }
}
