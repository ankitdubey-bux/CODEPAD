import java.io.*;
import javax.swing.SwingUtilities;
import java.util.regex.*;

public class runcode {

    main main3;

    public runcode(main main) {
        this.main3 = main;
        
        main3.runBtn.addActionListener(e ->{ 
            if(main3.cpp.isSelected()){
                runCpp();
            }else if(main3.c.isSelected()){
                runC();
            }else if(main3.java.isSelected()){
                runJava();
            }else{
                runJava();

            }
        }
        );
        
    }


// for runing java program ----------------------------------------------------------

    void runJava() {
        main3.outputpane.setText("");
        

        try {
            String code = main3.textPane.getText();

            // Detect public class name
            String className = findClassName(code);
            if (className == null) {
                main3.outputpane.setText(
                        "Error: No public class found.\n" +
                        "Write: public class ClassName"
                );
                return;
            }

            // Create temp directory
            File dir = new File("temp");
            if (!dir.exists()) dir.mkdir();

            // Write correct .java file
            File file = new File(dir, className + ".java");
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(code);
            }

            // Compile
            ProcessBuilder compilePB =
                    new ProcessBuilder("cmd", "/c", "javac " + className + ".java");
            compilePB.directory(dir);
            compilePB.redirectErrorStream(true);

            Process compileProcess = compilePB.start();
            read(compileProcess);
            compileProcess.waitFor();

            // Stop if compilation failed
            if (compileProcess.exitValue() != 0) return;

            //  Run
            ProcessBuilder runPB =
                    new ProcessBuilder("cmd", "/c", "java " + className);
            runPB.directory(dir);
            runPB.redirectErrorStream(true);

            read(runPB.start());

        } catch (Exception e) {
            main3.outputpane.setText(e.toString());
        }
    }

    // ===============================
    // Detect public class name
    // ===============================
    String findClassName(String code) {
        Pattern p = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher m = p.matcher(code);
        return m.find() ? m.group(1) : null;
    }

    // ===============================
    // Terminal-like output reader1
    // ===============================
    void read(Process p) {
        new Thread(() -> {
            try (BufferedReader br =
                         new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                String line;
                while ((line = br.readLine()) != null) {
                    String text = line + "\n";

                    SwingUtilities.invokeLater(() -> {
                        try {
                            main3.outputpane.getDocument().insertString(
                                    main3.outputpane.getDocument().getLength(),
                                    text,
                                    null
                            );
                            main3.outputpane.setCaretPosition(
                                    main3.outputpane.getDocument().getLength()
                            );
                        } catch (Exception ignored) {}
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }




 // for c language------------------------------------------------------------------

    void runC(){
        //clear old output
        main3.outputpane.setText(" ");
        try{
            // read code from editor
            String code = main3.textPane.getText();
            // detect public class name
            

            if(!hasMainFunction(code)){
                main3.outputpane.setText("error :\n you have not main function");
                
                return;
            }

            File dir = new File("temp");
            if(!dir.exists()) dir.mkdir(); //dir.mkdir() → naya folder create karta hai agar exist nahi kare to


            File file = new File(dir,"program.c");
            try(FileWriter fw = new FileWriter(file)){
                fw.write(code);
            }
// now we compile our code and chek were the error is happpen it does not run our program only chek 
            ProcessBuilder CompilePB = new ProcessBuilder("cmd","/c","gcc program.c -o program");
                CompilePB.directory(dir);
                CompilePB.redirectErrorStream(true);
                
                Process compileProcess = CompilePB.start();

                read(compileProcess);
                compileProcess.waitFor();


            //chek compile sucess
            if(compileProcess.exitValue() != 0)
                return; // if compile process is wrong it return from here do not run forward

// now finaly from here we run our code and get output from here 
            ProcessBuilder runPB = new ProcessBuilder("cmd" , "/c" ,"program.exe");
                runPB.directory(dir);
                runPB.redirectErrorStream(true);
                read(runPB.start());


        }catch (Exception e) {
            main3.outputpane.setText(e.toString());
        }
    }

    boolean hasMainFunction(String code) {
        Pattern p = Pattern.compile("\\bint\\s+main\\s*\\(");
        Matcher m = p.matcher(code);
        return m.find();
}


// this method is used for c++ ------------------------------------------------------


void runCpp() {

    // clear old output
    main3.outputpane.setText("");

    try {
        // read code from editor
        String code = main3.textPane.getText();

        // detect main() function
        if (!hasMainFunction(code)) {
            main3.outputpane.setText(
                    "Error:\nYou have not written main() function"
            );
            return;
        }

        // create temp directory
        File dir = new File("temp");
        if (!dir.exists()) dir.mkdir();

        // write C++ file
        File file = new File(dir, "program.cpp");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(code);
        }

        // compile C++ code
        ProcessBuilder compilePB =
                new ProcessBuilder("cmd", "/c", "g++ program.cpp -o program_cpp");
        compilePB.directory(dir);
        compilePB.redirectErrorStream(true);

        Process compileProcess = compilePB.start();
        read(compileProcess);
        compileProcess.waitFor();

        // if compilation failed, stop
        if (compileProcess.exitValue() != 0)
            return;

        // run executable
        ProcessBuilder runPB =
                new ProcessBuilder("cmd", "/c", "program_cpp.exe");
        runPB.directory(dir);
        runPB.redirectErrorStream(true);

        read(runPB.start());

    } catch (Exception e) {
        main3.outputpane.setText(e.toString());
    }
}









}

