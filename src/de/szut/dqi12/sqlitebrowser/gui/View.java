/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui;

import de.szut.dqi12.sqlitebrowser.Settings;
import de.szut.dqi12.sqlitebrowser.gui.listeners.NodeRightClickListener;
import de.szut.dqi12.sqlitebrowser.gui.listeners.NodeSelectionListener;
import de.szut.dqi12.sqlitebrowser.gui.listeners.TableRightClickListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author tsc
 */
public class View extends JFrame {
    
    public Settings settings;

    //Component Defintionen
    private JSplitPane mainPane;
    private JScrollPane leftPane;
    private JScrollPane rightPane;
    
    private JTree dbTree;

    //Menubar Definitionen (Menüleiste oben im Programm)
    private JMenuBar bar;
    
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu infoMenu;
    
    private JMenuItem fileSaveItem;
    private JMenuItem fileOpenItem;
    
    private JMenuItem infoCreditsItem;
    
    private JTable dbTable;
    
    private JTextField sqlField;
    
    private DefaultMutableTreeNode topNode;
    
    private static View instance = null;
    
    protected View() {
        
        settings = new Settings();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        initComponents();
        initMenuBar();
        initTree();
        initTable();
        
        registerListeners();
        
        getContentPane().add(sqlField);
        sqlField.setBounds(100, 100, 0, 0);
        leftPane.setViewportView(dbTree);
        rightPane.setViewportView(dbTable);
        
        setContentPane(mainPane);
        setJMenuBar(bar);
        pack();
        
        setVisible(true);
    }
    
    public static View getView(){
        if(instance == null) instance = new View();
        return instance;
    }

    /**
     * @author Till Schlechtweg
     */
    private void initComponents() {
        bar = new JMenuBar();
        
        infoMenu = new JMenu();
        fileMenu = new JMenu();
        editMenu = new JMenu();
        
        fileOpenItem = new JMenuItem();
        fileSaveItem = new JMenuItem();
        infoCreditsItem = new JMenuItem();
        
        topNode = new DefaultMutableTreeNode("Hello");
        
        dbTree = new JTree(topNode);
        dbTable = new JTable();
        
        leftPane = new JScrollPane();
        rightPane = new JScrollPane();
        
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        
        sqlField = new JTextField();
    }
    
    private void initTree() {
        
    }
    
    private void initTable() {
        dbTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "1", "2", "3", "4"
                }
        ));
    }

    /**
     * Initialisiert die MenuBar und ihre Komponenten.
     *
     * @author Till Schlechtweg
     */
    private void initMenuBar() {

        //
        editMenu.setText("Edit");
        fileMenu.setText("File");
        infoMenu.setText("Settings");
        
        fileOpenItem.setText("open File");
        fileSaveItem.setText("save File in");
        infoCreditsItem.setText("About");

        //
        fileMenu.add(fileSaveItem);
        fileMenu.add(fileOpenItem);
        infoMenu.add(infoCreditsItem);
        
        bar.add(fileMenu);
        bar.add(editMenu);
        bar.add(infoMenu);
    }
    
    public void updateTree(ArrayList<String> nodes, DefaultMutableTreeNode rootNode) {
        nodes.stream().forEach((node) -> {
            rootNode.add(new DefaultMutableTreeNode(node));
        });
        topNode.add(rootNode);
    }

    /**
     *
     */
    private void registerListeners() {
        dbTree.addTreeSelectionListener(new NodeSelectionListener());
        dbTree.addMouseListener(new NodeRightClickListener());
        dbTable.addMouseListener(new TableRightClickListener());
    }
}
