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
package org.japo.java.layers.services;

import java.sql.SQLException;
import java.util.List;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Vehiculo;
import org.japo.java.exceptions.ConnectivityException;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public interface S3Data {

    public void abrirAccesoDatos(Credencial c)
            throws ConnectivityException;

    public void cerrarAccesoDatos() throws ConnectivityException;

    // Consultar Vehículo
    public Vehiculo consultarVehiculo(Integer id)
            throws NullPointerException, SQLException;

    // Insertar Vehiculo - Manual
    public List<Vehiculo> listarVehiculo()
            throws NullPointerException, SQLException;

    // Insertar Vehículo - Manual
    public boolean insertarVehiculo(Vehiculo v)
            throws NullPointerException, SQLException;

    // Modificar Vehículo
    public boolean modificarVehiculo(Vehiculo v)
            throws NullPointerException, SQLException;
    
    // Borrar Vehículo
    public int borrarVehiculo(String[] param) 
            throws NullPointerException, SQLException;
}