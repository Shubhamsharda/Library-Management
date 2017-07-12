/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Shubham
 */

public class Manager extends JFrame {
    private ResultSetTableModel tableModel;
    static final String defaultQuery="select * from books";
    public Manager(String query )
    {
        this.setLocationRelativeTo(null);
        try{
            tableModel = new ResultSetTableModel(query);
            JTable resultTable = new JTable(tableModel);
            add(new JScrollPane(resultTable), BorderLayout.CENTER);
            
        } catch(SQLException e)
        {
            e.printStackTrace();
            tableModel.disconnectFromDatabase();
            
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(        
        new WindowAdapter()
        {
            public void windowClosed(WindowEvent event)
            {
             tableModel.disconnectFromDatabase();   
             System.exit(0);
            }
        });
    }
    
    }

