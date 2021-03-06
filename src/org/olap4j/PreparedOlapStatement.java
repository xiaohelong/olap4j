/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.olap4j;

import org.olap4j.metadata.Cube;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An object that represents a precompiled OLAP statement.
 *
 * <p>An OLAP statement is precompiled and stored in a
 * <code>PreparedOlapStatement</code> object. This object can then be used to
 * efficiently execute this statement multiple times.</p>
 *
 * <p>A <code>PreparedOlapStatement</code> is generally created using
 * {@link OlapConnection#prepareOlapStatement(String)}.</p>
 *
 * <p><B>Note:</B> The setter methods (<code>setShort</code>,
 * <code>setString</code>, and so on) for setting IN parameter values
 * must specify types that are compatible with the defined type of
 * the input parameter. For instance, if the IN parameter has type
 * <code>INTEGER</code>, then the method <code>setInt</code> should be used.</p>
 *
 * <p>If a parameter has Member type, use the {@link #setObject(int, Object)}
 * method to set it. A {@link OlapException} will be thrown if the object is not
 * an instance of {@link org.olap4j.metadata.Member} or does not belong to the
 * correct {@link org.olap4j.metadata.Hierarchy}.</p>
 *
 * <p>The method {@link #getParameterMetaData()} returns a description of the
 * parameters, as in JDBC. The result is an {@link OlapParameterMetaData}.
 *
 * <p>Unlike JDBC, it is not necessary to assign a value to every parameter.
 * This is because OLAP parameters have a default value. Parameters have their
 * default value until they are set, and then retain their new values for each
 * subsequent execution of this <code>PreparedOlapStatement</code>.
 *
 * @see OlapConnection#prepareOlapStatement(String)
 * @see CellSet
*
 * @author jhyde
 * @since Aug 22, 2006
 */
public interface PreparedOlapStatement
    extends PreparedStatement, OlapStatement
{
    /**
     * Executes the MDX query in this <code>PreparedOlapStatement</code> object
     * and returns the <code>CellSet</code> object generated by the query.
     *
     * @return an <code>CellSet</code> object that contains the data produced
     *         by the query; never <code>null</code>
     * @exception OlapException if a database access error occurs
     */
    CellSet executeQuery()  throws OlapException;

    /**
     * Retrieves the number, types and properties of this
     * <code>PreparedOlapStatement</code> object's parameters.
     *
     * @return an <code>OlapParameterMetaData</code> object that contains
     *         information about the number, types and properties of this
     *         <code>PreparedOlapStatement</code> object's parameters
     * @exception OlapException if a database access error occurs
     * @see OlapParameterMetaData
     */
    OlapParameterMetaData getParameterMetaData() throws OlapException;

    /**
     * Retrieves a <code>CellSetMetaData</code> object that contains
     * information about the axes and cells of the <code>CellSet</code> object
     * that will be returned when this <code>PreparedOlapStatement</code> object
     * is executed.
     *
     * @return the description of this <code>CellSet</code>'s axes
     * and cells
     * @exception OlapException if a database access error occurs
     */
    CellSetMetaData getMetaData() throws SQLException;

    /**
     * Returns the cube (or virtual cube) which this statement is based upon.
     *
     * @return cube this statement is based upon
     */
    Cube getCube();

    /**
     * Returns whether the value of the designated parameter is set.
     *
     * <p>Note that you cannot tell whether the parameter is set by looking to
     * see whether the value is {@code null}, because {@code null} is a valid
     * parameter value. When a parameter is not set, its value is derived by
     * evaluating its default expression.
     *
     * <p>To set the value call one of the {@link #setObject setXxx} methods. To
     * unset the value, call {@link #unset}.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @return whether the parameter's value has been set
     * @exception java.sql.SQLException if a database access error occurs
     */
    boolean isSet(int parameterIndex) throws SQLException;

    /**
     * Unsets the value of the designated parameter.
     *
     * @see #isSet(int)
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @exception java.sql.SQLException if a database access error occurs
     */
    void unset(int parameterIndex) throws SQLException;
}

// End PreparedOlapStatement.java
