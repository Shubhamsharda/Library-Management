/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Shubham
 */
public class ResultSetTableModel extends AbstractTableModel{
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase=false;
    
    public ResultSetTableModel(String query) throws SQLException
    {
       connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib", "root", "password");
       statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
       connectedToDatabase=true;
       setQuery(query);   //implement this function
       
    }
    public Class getColumClass(int column) throws IllegalStateException
    {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not connected to Database");
        try{
            String className = metaData.getColumnClassName(column + 1);
            return Class.forName(className);
        } catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return Object.class;
    }

    @Override
    public int getRowCount() throws IllegalStateException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    if(!connectedToDatabase)
         throw new IllegalStateException( "Not Connected to Database" ); 
    return numberOfRows;
        
    }

    @Override
    public int getColumnCount() throws IllegalStateException {
       // throw new UnsupportedOperationException("Not supported yet.");
        try{
                 return metaData.getColumnCount();//To change body of generated methods, choose Tools | Templates.
    }catch(SQLException sqlException)
    {
        sqlException.printStackTrace();
    }
        return 0;
    }
    public String getColumnName(int column) throws IllegalStateException
    {
         if ( !connectedToDatabase ) 
           throw new IllegalStateException( "Not Connected to Database" ); 
         try{
              return metaData.getColumnName( column + 1 ); 
         } catch ( SQLException sqlException )
         {
             sqlException.printStackTrace();
         }
         return "";
    }

    @Override
    public Object getValueAt(int row, int column) throws IllegalStateException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     if ( !connectedToDatabase ) 
        throw new IllegalStateException( "Not Connected to Database" ); 
     try{
         resultSet.absolute(row+1);
         return resultSet.getObject(column +1);
         
     } catch(SQLException sqlException)
     {
         sqlException.printStackTrace();
         
     }
     return "";
     
    }
    
    public void setQuery(String query)
            throws SQLException, IllegalStateException
    {
        if ( !connectedToDatabase ) 
        throw new IllegalStateException( "Not Connected to Database" ); 
        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();
        resultSet.last();
        numberOfRows = resultSet.getRow();
        fireTableStructureChanged();
        
        
    }
    
    public void disconnectFromDatabase()
    {
        if(connectedToDatabase)
        {
            try{
                resultSet.close();
                statement.close();
                connection.close();
                
            }catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            finally
            {
                connectedToDatabase = false;
            }
        }
    }
    
}
