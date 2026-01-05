import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileTreePanel extends JPanel {

    private JTree tree;
    private DefaultMutableTreeNode root;
    private JScrollPane scroll;
    private main mainRef;

    // 🎨 COLORS
    // 🎨 COLORS (DARK THEME)
    private static final Color PANEL_BG   = new Color(74, 74, 74);
    private static final Color TREE_BG    = new Color(74, 74, 74);
    private static final Color SELECT_BG  = new Color(96, 96, 96); // slightly lighter for selection
    private static final Color FOLDER_TXT = Color.WHITE;
    private static final Color FILE_TXT   = Color.LIGHT_GRAY;

    public FileTreePanel(main mainRef) {

        this.mainRef = mainRef;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        // ROOT
        root = new DefaultMutableTreeNode("Workspace");

        tree = new JTree(root);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setBackground(TREE_BG);
        tree.setOpaque(true);

        // 🎨 ICONS (SYSTEM SAFE)
        Icon folderIcon = UIManager.getIcon("FileView.directoryIcon");
        Icon fileIcon   = UIManager.getIcon("FileView.fileIcon");

        // 🎨 RENDERER
        tree.setCellRenderer(new DefaultTreeCellRenderer() {

            @Override
            public Component getTreeCellRendererComponent(
                    JTree tree, Object value, boolean selected,
                    boolean expanded, boolean leaf,
                    int row, boolean hasFocus) {

                super.getTreeCellRendererComponent(
                        tree, value, selected, expanded, leaf, row, hasFocus);

                setOpaque(true);

                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode) value;

                Object obj = node.getUserObject();

                // background
                setBackground(selected ? SELECT_BG : TREE_BG);

                // ROOT
                if (obj instanceof String) {
                    setText(obj.toString());
                    setIcon(folderIcon);
                    setForeground(FOLDER_TXT);
                    return this;
                }

                // FILE / FOLDER
                if (obj instanceof File) {
                    File file = (File) obj;
                    setText(file.getName());

                    if (file.isDirectory()) {
                        setIcon(folderIcon);
                        setForeground(FOLDER_TXT);
                    } else {
                        setIcon(fileIcon);     // 📄 FILE ICON
                        setForeground(FILE_TXT);
                    }
                }

                return this;
            }
        });

        // SCROLL PANE
        scroll = new JScrollPane(tree);
        scroll.setBackground(PANEL_BG);
        scroll.getViewport().setBackground(TREE_BG);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(scroll, BorderLayout.CENTER);

        // 📁 OPEN MENU → FOLDER CHOOSER
        mainRef.open.addActionListener(e -> openFolderChooser()
        );

        // 📄 CLICK FILE → SHOW IN INPUT PANEL
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path == null) return;

                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode) path.getLastPathComponent();

                Object obj = node.getUserObject();

                if (obj instanceof File) {
                    File file = (File) obj;
                    if (file.isFile()) {
                        showFileData(file);
                    }
                }
            }
        });
    }

    // 📁 Folder chooser
    private void openFolderChooser() {

        File defaultDir = new File("C:\\");
        JFileChooser chooser = new JFileChooser(defaultDir);
        chooser.setPreferredSize(new Dimension(800, 500));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(mainRef) == JFileChooser.APPROVE_OPTION) {
            

            File folder = chooser.getSelectedFile();

            root.removeAllChildren();
            root.setUserObject(folder.getName());

            buildTree(folder, root);

            ((DefaultTreeModel) tree.getModel()).reload();
            tree.expandRow(0);
        }
    }

    // 🌲 Build tree
    private void buildTree(File dir, DefaultMutableTreeNode node) {

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            DefaultMutableTreeNode child =
                    new DefaultMutableTreeNode(f);
            node.add(child);

            if (f.isDirectory()) {
                buildTree(f, child);
            }
        }
    }

    // 📄 Show file in INPUT PANEL JTextPane
    private void showFileData(File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            mainRef.textPane.setText(sb.toString());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error reading file:\n" + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
