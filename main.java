import java.awt.*;
import javax.swing.*;

public class main extends JFrame{

    JButton runBtn;
    JTextPane textPane;
    JTextPane outputpane;
    JRadioButtonMenuItem  c ,cpp, java;
    public JMenu language, Page ,edit;
    JMenuItem open, save, clear, Exist ;
    JMenuItem Cut, Copy, Paste ,bgGray,bgWhite,bgBlack;
    JMenuItem Serif,SansSerif,Monospaced,Dialog,DialogInput, ComicSans;
    FileTreePanel filePanel;
    JSplitPane split2, split;


    main(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CODE PADE (Created by Ankit)");
        



// JMenu itms on JFrame -----------------------------------------------------------

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(0, 30));
        menuBar.setBackground(new Color(255,133,89));
        
        // File menu
        JMenu file = new JMenu("File");
         open = new JMenuItem("Open");
         save = new JMenuItem("save");
         clear = new JMenuItem("Clear All");
         Exist = new JMenuItem("Exist");

        file.add(open);
        file.add(save);
        file.add(clear);
        file.add(Exist);
        

        // page menu
        Page = new JMenu("Font");
        Serif = new JMenuItem("Serif");
        SansSerif = new JMenuItem("SansSerif");
        Monospaced = new JMenuItem("Monospaced");
        Dialog = new JMenuItem("Dialog");
        DialogInput= new JMenuItem("Times New");
        ComicSans = new JMenuItem("Comic Sans");
        
        Page.add(Serif);
        Page.add(SansSerif);
        Page.add(Monospaced);
        Page.add(Dialog);
        Page.add(DialogInput);
        Page.add(ComicSans);







        // Edit menu 
         edit = new JMenu("Edit");
         Cut = new JMenuItem("Cut");
         Copy = new JMenuItem("Copy");
         Paste = new JMenuItem("Paste");
         bgWhite = new JMenuItem("Thems LIGHT");
         bgBlack = new JMenuItem("Thems DARK");

        edit.add(Cut);
        edit.add(Copy);
        edit.add(Paste);
        edit.add(bgWhite);
        edit.add(bgBlack);


        language = new JMenu("language");
        c = new JRadioButtonMenuItem("C");
        cpp = new JRadioButtonMenuItem("C++");
        java = new JRadioButtonMenuItem("Java");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(java);
        bg.add(c);
        bg.add(cpp);

        c.setBackground(new Color(250, 200, 147));
        cpp.setBackground(new Color(250, 200, 147));
        java.setBackground(new Color(250, 200, 147));
        language.add(java);
        language.add(c);
        language.add(cpp);


        menuBar.add(file);
        menuBar.add(Page);
        menuBar.add(edit);
        menuBar.add(language);
        

        // Run Button
        runBtn = new JButton("Run");
        runBtn.setBackground(Color.RED);
        runBtn.setForeground(Color.WHITE);
        menuBar.add(Box.createHorizontalGlue()); // push button to right
        menuBar.add(runBtn);

        setJMenuBar(menuBar);


//inputPanel -------------------------------------------------------------------------------------------------------------
        JPanel inputPanel = new JPanel(new BorderLayout());
        textPane = new JTextPane();
        textPane.setFont(new Font("Consolas", Font.PLAIN, 18));
        textPane.setBackground(new Color(56,73,89));
        textPane.setForeground(Color.WHITE);
        textPane.setCaretColor(Color.RED);
        inputPanel.add(new JScrollPane(textPane), BorderLayout.CENTER);

// outputPanel ------------------------------------------------------------------------------------------------------       
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputpane = new JTextPane();
        outputpane.setEditable(false);
        outputpane.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputpane.setBackground(new Color(74,74,74));
        outputpane.setForeground(Color.GREEN);
        outputPanel.add(new JScrollPane(outputpane), BorderLayout.CENTER);
// filePanel ---------------------------------------------------------------------------------------------------------------
        filePanel = new FileTreePanel(this);



        
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            filePanel,
            inputPanel
        );
         split2 = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            split,
            outputPanel
        );
        split.getLeftComponent().setVisible(false);
        split2.getRightComponent().setVisible(false);
        split2.setOneTouchExpandable(true);
        
        add(split2);

        runBtn.addActionListener(e ->{ 
                split2.getRightComponent().setVisible(true);
                split2.setDividerLocation(870);
        });
        
        open.addActionListener(e ->{ 
                split.getLeftComponent().setVisible(true);
                split.setDividerLocation(150);
        });


        
// object of other classes in this class 
        new runcode(this);
        new Actionitem(this);
        new design(this);
       
        setVisible(true);



        
    }

    



    public static void main (String args[]){
        new main();
    }

    }