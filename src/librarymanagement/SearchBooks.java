/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Shubham
 */
public class SearchBooks extends JFrame{
    private ResultSetTableModel tableModel;
    private JTextField search;
    static final String defaultQuery="select bookname,bookno,IssueStatus from books order by bookname";
    public SearchBooks()
    {
        this.setLocationRelativeTo(null);
        try{
            tableModel = new ResultSetTableModel(defaultQuery);
            search = new JTextField("Search here..",10);
            search.addFocusListener(new CustomFocusListener());
            JScrollPane scrollPane = new JScrollPane(search,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            JButton submitButton = new JButton("Search!");
            Box boxNorth = Box.createHorizontalBox();
            boxNorth.add(scrollPane);
            boxNorth.add(submitButton);
            JTable resultTable = new JTable(tableModel);
            add( boxNorth,BorderLayout.NORTH);
            add(new JScrollPane(resultTable), BorderLayout.CENTER);
            submitButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    try{
                        tableModel.setQuery("select bookname,bookno,IssueStatus from books where bookname like '%"+search.getText()+"%' order by bookname");
                    } catch(SQLException sqlException)
                    {
                        JOptionPane.showMessageDialog( null,
                                sqlException.getMessage(), "Database error",
                                 JOptionPane.ERROR_MESSAGE );
                        try{
                            tableModel.setQuery( defaultQuery );
                             search.setText( defaultQuery ); 
                        }catch(SQLException sqlException2){
                            JOptionPane.showMessageDialog( null,
                                    sqlException2.getMessage(), "Database error",
                                    JOptionPane.ERROR_MESSAGE );
                            tableModel.disconnectFromDatabase();
                            System.exit( 1 );

                        }
                        
                    }    
                    }
                }       
            );
        } catch(SQLException e)
        {
            e.printStackTrace();
            tableModel.disconnectFromDatabase();
            
        }
        
         setVisible( true );
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
    class CustomFocusListener implements FocusListener {
        public void focusGained(FocusEvent e){
        search.setText("");
        }
        public void focusLost(FocusEvent e )
        {
            //search.setText("Search here..");
        }
        }
    }

