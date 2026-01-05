
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;

import javax.swing.JFileChooser;

public class Actionitem {
    // class level variable 
    public String fileData;
    main main1;
    File saveFile;
    


    // constructor here ---------

// file all action on JMenuitemlistener

   public Actionitem(main main){
        this.main1 = main;
        main1.save.addActionListener(e->{
            savefun();
        });
         main1.clear.addActionListener(e->{
            main1.textPane.setText("");
        });

        main1.bgWhite.addActionListener(e->{
            backgroundLight();
        });

        main1.bgBlack.addActionListener(e->{
            backgroundDark();
        });

        main1.Cut.addActionListener(e->{
            main1.textPane.cut();
        });
        main1.Copy.addActionListener(e->{
            main1.textPane.copy();
        });
        main1.Paste.addActionListener(e->{
            main1.textPane.paste();
        });

        main1.Exist.addActionListener(e->{
            System.exit(0);
        });




        
//page all action listener on Jmenu list

        main1.Serif.addActionListener(e->{
            main1.textPane.setFont(new Font("Serif",Font.BOLD,25));
        });
        main1.SansSerif.addActionListener(e->{
            main1.textPane.setFont(new Font("SansSerif",Font.PLAIN,25));
        });
        main1.Monospaced.addActionListener(e->{
            main1.textPane.setFont(new Font("Monospaced",Font.ITALIC,20));
        });
        main1.Dialog.addActionListener(e->{
            main1.textPane.setFont(new Font("Dialog",Font.PLAIN ,20));
        });
        main1.DialogInput.addActionListener(e->{
            main1.textPane.setFont(new Font("Times New Roman",Font.PLAIN,20));
        });
        main1.ComicSans.addActionListener(e->{
            main1.textPane.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        });

        

        

    }




 void savefun(){
    try{
        String textOut = main1.textPane.getText();
        
        File defaultDir = new File("C:\\");
        JFileChooser file1 = new JFileChooser(defaultDir);
        file1.setPreferredSize(new Dimension(800, 500));
        //Is condition ka use isliye hota hai taaki file tabhi save ho jab user Save par click kare; Cancel hone par koi action na ho.
        if (file1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
     saveFile = file1.getSelectedFile();                                                
        

        try(BufferedWriter b1 = new BufferedWriter(new FileWriter(saveFile))){

        b1.append(textOut);

            }
        }

        }catch(Exception e){
            System.out.println(e);
        }

    }

 
    void backgroundLight(){
    main1.textPane.setBackground(new Color(247, 231, 225));
    main1.textPane.setForeground(Color.BLACK);
    main1.textPane.setCaretColor(Color.BLACK);
    
    }

    void backgroundDark(){
        main1.textPane.setBackground(new Color(250 ,247 ,243));
        main1.textPane.setForeground(Color.WHITE);
        main1.textPane.setCaretColor(Color.RED);

    }



    







    
 }
















