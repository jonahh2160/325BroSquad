// IG 11/10

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.*;

public class InfoPageGui {
    private JFrame frame;
    private JPanel infoPanel;
    private TransactionDatabase transactionDatabase;
    private DefaultTableModel transactionTableModel, infoTableModel;
    private JTable transactionTable, infoTable;
    private JScrollPane scrollPane, infoScrollPane;
    private JButton editButton, closeButton;
    private JLabel infoLabel, transactionLabel;
    private ManagementPageGUI managementPage;
    final int tableX = 20;
    final int tableY = 150;
    final int tableWidth = 480;
    final int tableHeight = 270;
    final int infoX = 20;
    final int infoY = 50;
    final int infoWidth = 480;
    final int infoHeight = 37;
    final String[] transactionColumnNames = {"User","Book","Date Borrowed","Date Returned","ID"};
    final String[] bookColumnNames = {"Title","Author","Genre","ID","Available"};
    final String[] userColumnNames = {"Name","Username","ID","Status","Balance"};
    private String[] columnNames = bookColumnNames;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private SearchPageGui searchPage;

    public InfoPageGui(TransactionDatabase transactionDatabase, SearchPageGui searchPage) {
        this.transactionDatabase = transactionDatabase;
        this.searchPage = searchPage;
        managementPage = new ManagementPageGUI();
        frame = new JFrame("Info Page");
        frame.setSize(640,480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        columnNames = bookColumnNames;

        infoPanel = new JPanel();
        infoPanel.setLayout(null);

        // Creating info table
        infoTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return(false);
            }
        };
        infoTable = new JTable(infoTableModel);
        infoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Creating the scroll pane for info
        infoScrollPane = new JScrollPane(infoTable);
        infoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        infoScrollPane.setBounds(infoX,infoY,infoWidth,infoHeight);
        infoScrollPane.setVisible(true);

        // Creating the transaction table
        transactionTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return(false);
            }
        };
        transactionTable = new JTable(transactionTableModel);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionTableModel.setColumnCount(transactionColumnNames.length);
        for (int i=0;i<transactionColumnNames.length;i++) {
            transactionTable.getColumnModel().getColumn(i).setHeaderValue(transactionColumnNames[i]);
        }
        transactionTable.getTableHeader().repaint();
        // Creating the scroll pane to be able to scroll through table
        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(tableX,tableY,tableWidth,tableHeight);
        scrollPane.setVisible(true);

        //Buttons
        closeButton = new JButton("Close");
        closeButton.setSize(100,30);
        closeButton.setLocation(510,390);
        editButton = new JButton("Edit");
        editButton.setSize(100,30);
        editButton.setLocation(infoX + infoWidth+10,infoY);

        //Lables
        infoLabel = new JLabel("");
        infoLabel.setLocation(infoX,infoY-20);
        infoLabel.setSize(100,20);
        transactionLabel = new JLabel("Transaction History:");
        transactionLabel.setLocation(tableX,tableY-20);
        transactionLabel.setSize(200,20);

        // Close button logic
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


    }

    private void refreshColumnNames() {
        infoTableModel.setRowCount(1);
        infoTableModel.setColumnCount(0);
        infoTableModel.setColumnCount(columnNames.length);
        for (int i = 0;i<columnNames.length;i++) {
            infoTable.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }
        infoTable.getTableHeader().repaint();
    }

    private void getBookInfo(Book book) {
        // Input book data into the cells
        infoLabel.setText("Book Info:");
        columnNames = bookColumnNames;
        refreshColumnNames();
        infoTable.setValueAt(book.getTitle(),0,0);
        infoTable.setValueAt(book.getAuthor(),0,1);
        infoTable.setValueAt(book.getGenre(),0,2);
        infoTable.setValueAt(book.getPrimaryKey(),0,3);
        infoTable.setValueAt(String.valueOf(book.getAvailability()),0,4);
        transactions.clear();
        transactions = transactionDatabase.getBookTransactions(book);
        getTransactionData(transactions);
    }

    private void getUserInfo(User user) {
        // Input user data into the cells
        infoLabel.setText("User Info:");
        columnNames = userColumnNames;
        refreshColumnNames();
        infoTable.setValueAt(user.getFullName(),0,0);
        infoTable.setValueAt(user.getUsername(),0,1);
        infoTable.setValueAt(user.getPrimaryKey(),0,2);
        infoTable.setValueAt(String.valueOf(user.getActive()),0,3);
        infoTable.setValueAt(user.getAccountBalance(),0,4);
        transactions.clear();
        transactions = transactionDatabase.getUserTransactions(user);
        getTransactionData(transactions);
    }

    // This inputs the transaction data into the cells
    private void getTransactionData(ArrayList<Transaction> database) {
        // Reset row count to clear the data
        transactionTableModel.setRowCount(0);
        transactionTableModel.setRowCount(database.size());
        // Input book data into the cells
        for(int i = 0; i < database.size();i++) {
            transactionTable.setValueAt(database.get(i).getUser().getUsername(),i,0);
            transactionTable.setValueAt(database.get(i).getBook().getTitle(),i,1);
            transactionTable.setValueAt(database.get(i).getDateBorrowed(),i,2);
            transactionTable.setValueAt(database.get(i).getDateReturned(),i,3);
            transactionTable.setValueAt(database.get(i).getPrimaryKey(),i,4);
        }
    }

    public void bookInfoPage(Book book) {
        
        infoPanel.removeAll();
        getBookInfo(book);

        // Add Info Pane
        infoPanel.add(infoScrollPane);

        // Edit button logic
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                managementPage.changeBookInfo(book);
            }
        });

        // Add transaction window and edit button
        if (searchPage.getUser() != null && searchPage.getUser().getPerms() > 0) {
            infoPanel.add(transactionLabel);
            infoPanel.add(scrollPane);
            infoPanel.add(editButton);
        }

        // Add buttons
        infoPanel.add(closeButton);

        // Add labels
        infoPanel.add(infoLabel);

        frame.add(infoPanel);
        frame.setVisible(true);
    }

    public void userInfoPage(User user) {
        
        infoPanel.removeAll();
        getUserInfo(user);

        // Add Info Pane
        infoPanel.add(infoScrollPane);

        // Add transaction window and edit button
        if (searchPage.getUser() != null && (searchPage.getUser().getPerms() > 0 || searchPage.getUser() == user)) {
            infoPanel.add(transactionLabel);
            infoPanel.add(scrollPane);
        }

        // Add edit button
        if (searchPage.getUser() != null && searchPage.getUser().getPerms() > 0) {
            infoPanel.add(editButton);
        }

        // Add close button
        infoPanel.add(closeButton);

        // Add labels
        infoPanel.add(infoLabel);

        frame.add(infoPanel);
        frame.setVisible(true);
    }

}