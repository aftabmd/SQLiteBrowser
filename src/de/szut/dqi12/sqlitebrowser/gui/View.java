/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui;

import de.szut.dqi12.sqlitebrowser.Controller;
import de.szut.dqi12.sqlitebrowser.gui.listeners.EditDeleteTableListener;
import de.szut.dqi12.sqlitebrowser.gui.listeners.ExecuteQueryListener;
import de.szut.dqi12.sqlitebrowser.gui.listeners.FileOpenListener;
import de.szut.dqi12.sqlitebrowser.util.SettingsUtil;
import de.szut.dqi12.sqlitebrowser.gui.listeners.NodeSelectionListener;
import de.szut.dqi12.sqlitebrowser.gui.listeners.SettingsChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author Till Schlechtweg
 */
public class View extends JFrame {
    
    public SettingsUtil settings;

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
    private JMenu executeMenu;
    
    private JMenuItem editDeleteTableItem;
    private JMenuItem editCreateTableItem;
    private JMenuItem executeQueryItem;
    private JMenuItem fileSaveItem;
    private JMenuItem fileOpenItem;
    private JMenuItem infoChangeItem;
    private JMenuItem infoCreditsItem;
    
    private JTable dbTable;
    
    private JTextField sqlField;
    
    private DefaultMutableTreeNode topNode;
    
    private static View instance = null;
    
    protected View() {
        
        settings = new SettingsUtil();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        int x = Integer.valueOf(Controller.getSettings().getProperty(SettingsUtil.VIEW_X_POSITION));
        int y = Integer.valueOf(Controller.getSettings().getProperty(SettingsUtil.VIEW_Y_POSITION));
        int width = Integer.valueOf(Controller.getSettings().getProperty(SettingsUtil.VIEW_HEIGHT));
        int height = Integer.valueOf(Controller.getSettings().getProperty(SettingsUtil.VIEW_WIDTH));
        
        setBounds(x, y, width, height);
        
        initComponents();
        initMenuBar();
        initTree();
        initTable();
        
        registerListeners();
        
        sqlField.setBounds(100, 100, 0, 0);
        leftPane.setViewportView(dbTree);
        rightPane.setViewportView(dbTable);
        getContentPane().add(sqlField);
        
        setContentPane(mainPane);
        setJMenuBar(bar);
        pack();
        
        setVisible(true);
    }
    
    public static View getView() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    /**
     * @author Till Schlechtweg
     */
    private void initComponents() {
        bar = new JMenuBar();
        
        executeMenu = new JMenu();
        infoMenu = new JMenu();
        fileMenu = new JMenu();
        editMenu = new JMenu();
        
        editDeleteTableItem = new JMenuItem();
        editCreateTableItem = new JMenuItem();
        executeQueryItem = new JMenuItem();
        fileOpenItem = new JMenuItem();
        fileSaveItem = new JMenuItem();
        infoCreditsItem = new JMenuItem();
        infoChangeItem = new JMenuItem();
        
        topNode = new DefaultMutableTreeNode(Controller.getModel().getDatabaseName());
        
        dbTree = new JTree(topNode);
        dbTable = new JTable();
        
        leftPane = new JScrollPane();
        rightPane = new JScrollPane();
        
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        
        sqlField = new JTextField();
    }

    /**
     *
     * @author Till Schlechtweg
     */
    private void initTree() {
        dbTree.setShowsRootHandles(true);
        
        for (String name : Controller.getModel().getTableNames()) {
            topNode.add(new DefaultMutableTreeNode(name));
        }
    }

    /**
     *
     * @author Till Schlechtweg
     */
    private void initTable() {
        dbTable.setEnabled(false);
        
        HashMap<String, ArrayList> data = Controller.getModel().getTable(Controller.getModel().getTableNames().get(0), null);
        
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        ArrayList<String> tableNames = new ArrayList<>();
        
        for (Object name : data.keySet().toArray()) {
            tableData.add(data.get(name.toString()));
            tableNames.add(name.toString());
        }
        
        DefaultTableModel tableModel = new DefaultTableModel();
        int i = 0;
        
        for (ArrayList<String> temp : tableData) {
            tableModel.addColumn(tableNames.get(i), temp.toArray());
            i++;
        }
        dbTable.setModel(tableModel);
    }

    /**
     * Initialisiert die MenuBar und ihre Komponenten. Fügt die JMenuItems den
     * JMenus hinzu und die JMenus der MenuBar.
     *
     * @author Till Schlechtweg
     */
    private void initMenuBar() {
        //Texte für die Menüs
        editMenu.setText("edit");
        fileMenu.setText("file");
        infoMenu.setText("settings");
        executeMenu.setText("execute");
        
        editDeleteTableItem.setText("delete table");
        editCreateTableItem.setText("create table");
        fileOpenItem.setText("open file");
        fileSaveItem.setText("save file in");
        infoCreditsItem.setText("about");
        infoChangeItem.setText("change settings");
        executeQueryItem.setText("execute SELECT");

        //füge die MenuItems den Menus hinzu
        editMenu.add(editDeleteTableItem);
        editMenu.add(editCreateTableItem);
        fileMenu.add(fileSaveItem);
        fileMenu.add(fileOpenItem);
        infoMenu.add(infoChangeItem);
        infoMenu.add(infoCreditsItem);
        executeMenu.add(executeQueryItem);

        //In welcher Rheinfolge ich die Items adde bestimmt die ebenso die tatsächliche Rheinfolge
        bar.add(fileMenu);
        bar.add(editMenu);
        bar.add(executeMenu);
        bar.add(infoMenu);
    }

    /**
     *
     * @author Till Schlechtweg
     * @param nodes die TabellenNodes die unter der DatenbankNode angezeigt
     * werden.
     * @param rootNode WurzelNode oder auch DatenbankNode
     */
    public void updateTree(ArrayList<String> nodes, DefaultMutableTreeNode rootNode) {
        rootNode.removeAllChildren();
        topNode.removeAllChildren();
        nodes.stream().forEach((node) -> {
            topNode.add(new DefaultMutableTreeNode(node));
        });
        topNode.setUserObject(rootNode.toString());
        dbTree.updateUI();
    }
    
    public void updateTable(HashMap<String, ArrayList> data) {
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        ArrayList<String> tableNames = new ArrayList<>();
        
        for (Object name : data.keySet().toArray()) {
            tableData.add(data.get(name.toString()));
            tableNames.add(name.toString());
        }
        
        DefaultTableModel tableModel = new DefaultTableModel();
        int i = 0;
        
        for (ArrayList<String> temp : tableData) {
            tableModel.addColumn(tableNames.get(i), temp.toArray());
            i++;
        }
        dbTable.setModel(tableModel);
    }

    /**
     *
     * @author Till Schlechtweg
     */
    private void registerListeners() {
        dbTree.addTreeSelectionListener(new NodeSelectionListener());
        fileOpenItem.addActionListener(new FileOpenListener());
        infoChangeItem.addActionListener(new SettingsChangeListener());
        executeQueryItem.addActionListener(new ExecuteQueryListener());
        editDeleteTableItem.addActionListener(new EditDeleteTableListener());
    }
}
