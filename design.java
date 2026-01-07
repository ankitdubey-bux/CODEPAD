
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;





public class design {
     int fontSize;
     main main2;

    design(main main){
        this.main2 = main;
        
        increasefontSize();
        keyEnter();
    }

// size increase function 
        void increasefontSize(){
        fontSize = main2.textPane.getFont().getSize();
    main2.textPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                if (e.isControlDown()) {
                    
                    int rotate = e.getWheelRotation();
                    
                    if (rotate < 0) {
                        fontSize += 2;   
                    } else {
                        fontSize -= 2;   
                    }
                    main2.textPane.setFont(new Font("Consolas", Font.PLAIN, fontSize));
                    
                    if(fontSize >35) fontSize = 35;
                    if(fontSize <16)  fontSize =16;
                }
            }
        });
    }



// some special key add on textpane
void keyEnter(){
    main2.textPane.addKeyListener(new KeyAdapter() {

        @Override
        public void keyTyped(KeyEvent e) {

            if (e.getKeyChar() == '[') {

                e.consume(); // stop default '[' insertion

                try {
                    JTextPane tp = main2.textPane; // store the main text pane in new tp vaiable
                    StyledDocument doc = tp.getStyledDocument();//All text editing in JTextPane is done via Document

                    int pos = tp.getCaretPosition();// store curent curser position

                    // insert paired brackets
                    doc.insertString(pos, "[ ]", null);

                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

        
            if (e.getKeyChar() == '('){
                e.consume();
            try{
                JTextPane tp2 = main2.textPane;
                StyledDocument sd = tp2.getStyledDocument();
                int pos2 = tp2.getCaretPosition();
                sd.insertString(pos2,"( )",null);
                tp2.setCaretPosition(pos2 + 1);

            }catch(BadLocationException ex) {
                    ex.printStackTrace();
                };
            }

             if (e.getKeyChar() == '"'){
                    e.consume();
                try{
                    JTextPane tp2 = main2.textPane;
                    StyledDocument sd = tp2.getStyledDocument();
                    int pos2 = tp2.getCaretPosition();
                    sd.insertString(pos2,"\"\"",null);
                    tp2.setCaretPosition(pos2 + 1);

                }catch(BadLocationException ex) {
                        ex.printStackTrace();
                    };
                }


        
    }
        
    });
}













}


