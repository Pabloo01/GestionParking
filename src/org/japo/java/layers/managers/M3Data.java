/* 
 * Copyright 2021 Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.layers.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Vehiculo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public final class M3Data implements S3Data {

    //<editor-fold defaultstate="collapsed" desc="--- Data Access Manager ---">
    // Conexión BBDD - Propiedades 
    public static final String PRP_CONN_CPAT = "jdbc.conn.cpat";
    public static final String PRP_CONN_PROT = "jdbc.conn.prot";
    public static final String PRP_CONN_HOST = "jdbc.conn.host";
    public static final String PRP_CONN_PORT = "jdbc.conn.port";
    public static final String PRP_CONN_DBNM = "jdbc.conn.dbnm";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Sentencia BBDD - Tipo de Acceso
    public static final String STMT_ACCT_TFLY = "TYPE_FORWARD_ONLY";
    public static final String STMT_ACCT_TSIN = "TYPE_SCROLL_INSENSITIVE";
    public static final String STMT_ACCT_TSSE = "TYPE_SCROLL_SENSITIVE";
    private static final String DEF_STMT_TACC = STMT_ACCT_TSSE;
    public static final String PRP_STMT_TACC = "jdbc.stmt.tacc";

    // Sentencia BBDD - Concurrencia
    public static final String STMT_CONC_RNLY = "CONCUR_READ_ONLY";
    public static final String STMT_CONC_UPDT = "CONCUR_UPDATABLE";
    private static final String DEF_STMT_CONC = STMT_CONC_UPDT;
    public static final String PRP_STMT_CONC = "jdbc.stmt.conc";

    // Referencias
    private final Properties prp;

    // Artefactos Base de Datos
    private Connection conn = null;
    private Statement stmt = null;

    // Constructor Parametrizado - Properties
    public M3Data(Properties prp) {
        this.prp = prp;
    }

    @Override
    public final void abrirAccesoDatos(Credencial c)
            throws ConnectivityException {
        try {
            // Propiedades > Parámetros Conexión
            String cpat = prp.getProperty(PRP_CONN_CPAT);
            String prot = prp.getProperty(PRP_CONN_PROT);
            String host = prp.getProperty(PRP_CONN_HOST);
            String port = prp.getProperty(PRP_CONN_PORT);
            String dbnm = prp.getProperty(PRP_CONN_DBNM);

            // Credencial > Parámetros Conexión
            String user = c.getUser();
            String pass = c.getPass();

            // Cadena de Conexión
            String cstr = String.format(cpat, prot, host, port, dbnm, user, pass);

            // Conexión BD
            conn = DriverManager.getConnection(cstr);

            // Tipo de Acceso
            int tacc = obtenerTipoAcceso(prp);

            // Concurrencia
            int conc = obtenerConcurrencia(prp);

            // Sentencia BD
            stmt = conn.createStatement(tacc, conc);
        } catch (NullPointerException | SQLException e) {
            throw new ConnectivityException("Acceso Denegado: "
                    + e.getMessage());
        }
    }

    // Statement - Tipo de Acceso
    private int obtenerTipoAcceso(Properties prp) {
        // ---- TIPOS DE ACCESO ----
        // ResultSet.TYPE_FORWARD_ONLY (*) - Indica que el objeto ResultSet se
        //      puede recorrer unicamente hacia adelante.
        // ResultSet.TYPE_SCROLL_INSENSITIVE - Indica que el objeto ResultSet se
        //      puede recorrer, pero en general no es sensible a los cambios en
        //      los datos que subyacen en él.
        // ResultSet.TYPE_SCROLL_SENSITIVE - Indica que el objeto ResultSet se
        //      puede  recorrer, y además, los cambios en él repercuten
        //      en la base de datos subyacente.
        //
        // Properties > Selector Tipo de Acceso 
        String selTacc = prp != null ? prp.getProperty(PRP_STMT_TACC, DEF_STMT_TACC) : DEF_STMT_TACC;

        // Tipo de Acceso
        int tacc;

        // Obtener valor
        switch (selTacc) {
            case STMT_ACCT_TFLY:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
                break;
            case STMT_ACCT_TSIN:
                tacc = ResultSet.TYPE_SCROLL_INSENSITIVE;
                break;
            case STMT_ACCT_TSSE:
                tacc = ResultSet.TYPE_SCROLL_SENSITIVE;
                break;
            default:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
        }

        // Devolver Tipo de Acceso
        return tacc;
    }

    // Statement - Concurrencia
    private int obtenerConcurrencia(Properties prp) {
        // ---- MODOS DE CONCURRENCIA ----
        // ResultSet.CONCUR_READ_ONLY (*) - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste no puede ser actualizado.
        // ResultSet.CONCUR_UPDATABLE - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste podria ser actualizado.
        //        
        // Properties > Selector Concurrencia
        String selConc = prp != null ? prp.getProperty(PRP_STMT_CONC, DEF_STMT_CONC) : DEF_STMT_CONC;

        // Concurrencia
        int conc;
        switch (selConc) {
            case STMT_CONC_RNLY:
                conc = ResultSet.CONCUR_READ_ONLY;
                break;
            case STMT_CONC_UPDT:
                conc = ResultSet.CONCUR_UPDATABLE;
                break;
            default:
                conc = ResultSet.CONCUR_READ_ONLY;
        }

        // Devolver Concurrencia
        return conc;
    }

    // Cerrar Artefactos BD
    @Override
    public final void cerrarAccesoDatos() throws ConnectivityException {
        // Cerrar Sentencia de Base de datos
        try {
            stmt.close();
        } catch (SQLException | NullPointerException e) {
            throw new ConnectivityException("Error en el Cierre de la Sentencia: " + e.getMessage());
        }

        // Cerrar Conexión con Base de datos
        try {
            conn.close();
        } catch (SQLException | NullPointerException e) {
            throw new ConnectivityException("Error en el Cierre de Conexión: " + e.getMessage());
        }
    }
    //</editor-fold>

    // Propiedad SQL - Consultar Vehículo
    public static final String PRP_CONSULTAR_VEH = "parking.vehiculo.consulta";

    // Propiedad SQL - Listar Vehículo
    public static final String PRP_LISTAR_VEH = "parking.vehiculo.listar";

    // Propiedad SQL - Insertar Vehículo
    public static final String PRP_INSERTAR_VEH = "parking.vehiculo.insercion";

    // Propiedad SQL > Modificar Vehículo
    public static final String PRP_MODIFICAR_VEH = "parking.vehiculo.modificacion";

    // Propiedad SQL > Modificar Vehículo
    public static final String PRP_BORRAR_VEH = "parking.vehiculo.borrado";
    
    // Consultar Vehículo
    @Override
    public Vehiculo consultarVehiculo(Integer id)
            throws NullPointerException, SQLException {
        // Propiedad SQL
        String sql = prp.getProperty(PRP_CONSULTAR_VEH);

        // Nueva Entidad
        Vehiculo v = null;

        //Abre Sentencia SQL con Parámetros
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, id.toString());

            // Ejecutar SQL
            ResultSet rs = ps.executeQuery();

            // Conversión
            if (rs.next()) {
                String matricula = rs.getString("matricula");
                String tipoVehiculo = rs.getString("tipoVehiculo");
                Date fechaEntrada = rs.getDate("fechaEntrada");
                Date fechaSalida = rs.getDate("fechaSalida");
                int importe = rs.getInt("importe");

                // Instanciar nuevo Vehículo
                v = new Vehiculo(id, matricula, tipoVehiculo, fechaEntrada,
                        fechaSalida, importe);
            } else {
                throw new NullPointerException(" - No hay datos con esa ID");
            }
        }
        return v;
    }

    // Listar Vehículos
    @Override
    public List<Vehiculo> listarVehiculo()
            throws NullPointerException, SQLException {
        // Propiedad SQL
        String sql = prp.getProperty(PRP_LISTAR_VEH);

        return obtenerVehiculos(sql);
    }

    // Base de Datos > Vehículos 
    private List<Vehiculo> obtenerVehiculos(String sql)
            throws NullPointerException, SQLException {
        // Colección de Alumnos
        List<Vehiculo> lista = new ArrayList<>();

        //Proceso de listado 
        try (ResultSet rs = stmt.executeQuery(sql)) {
            //Generación del listado Fila a Fila
            while (rs.next()) {
                //Alumno actual
                Vehiculo v = obtenerVehiculo(rs);

                //Añade a la lista 
                lista.add(v);
            }
            return lista;
        }
    }

    // Base de Datos > Obtener Datos
    private Vehiculo obtenerVehiculo(ResultSet rs)
            throws NullPointerException, SQLException {

        // Resulset - Puntero > Alumno
        int id = rs.getInt("id");
        String matricula = rs.getString("matricula");
        String tipoVehiculo = rs.getString("tipoVehiculo");
        Date fechaEntrada = rs.getDate("fechaEntrada");
        Date fechaSalida = rs.getDate("fechaSalida");
        int importe = rs.getInt("importe");

        // Instanciar nuevo Vehículo
        return new Vehiculo(id, matricula, tipoVehiculo, fechaEntrada,
                fechaSalida, importe);
    }

    // Insertar Vehículo - Manual
    @Override
    public boolean insertarVehiculo(Vehiculo v)
            throws NullPointerException, SQLException {
        // SQL Inserción
        String sql = prp.getProperty(PRP_INSERTAR_VEH);

        // Filas Afectadas
        int filas;

        // Extraer parámetros
        String id = v.getId() + "";
        String matricula = v.getMatricula();
        String tipoVehiculo = v.getTipoVehiculo();
        String fechaEntrada = new SimpleDateFormat("yyyy-MM-dd").
                format(v.getFechaEntrada());

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(1, id);
            ps.setString(2, matricula);
            ps.setString(3, tipoVehiculo);
            ps.setString(4, fechaEntrada);

            // Ejecutar SQL
            filas = ps.executeUpdate();
        } catch (Exception e) {
            filas = 0;
        }

        return filas == 1;
    }

    // Modificar Vehículo
    @Override
    public boolean modificarVehiculo(Vehiculo v)
            throws NullPointerException, SQLException {
        // Properties > SQL
        String sql = prp.getProperty(PRP_MODIFICAR_VEH);

        // Variable
        int filas;

        // Extraer Parámetros
        String id = v.getId() + "";
        String fechaSalida = new SimpleDateFormat("yyyy-MM-dd").
                format(v.getFechaSalida());
        String importe = v.getImporte() + "";

        // Sentencia Preparada
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Asignar Parámetros
            ps.setString(3, id);
            ps.setString(1, fechaSalida);
            ps.setString(2, importe);

            // Ejecturar SQL
            filas = ps.executeUpdate();

        } catch (Exception e) {
            filas = 0;
        }

        // Devolver Semáforo
        return filas == 1;
    }
    
    // Borrar Vehículo
    @Override
    public int borrarVehiculo(String[] param) 
            throws NullPointerException, SQLException {
        //SQL Borrado      
        String sql = prp.getProperty(PRP_BORRAR_VEH);

        //Crear PreparedStatement
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < param.length; i++) {
            ps.setString(i + 1, param[i]);
        }

        return ps.executeUpdate();
    }

}
