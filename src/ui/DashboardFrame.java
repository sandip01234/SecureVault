package ui;

import auth.User;
import vault.FileMeta;
import vault.VaultManager;
import vault.VersionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DashboardFrame extends JFrame {

    private JTable fileTable;
    private DefaultTableModel tableModel;

    public DashboardFrame(User user) {
        setTitle("Secure Vault - " + user.getUsername());
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Buttons
        JButton uploadBtn = new JButton("Upload File");
        JButton downloadBtn = new JButton("Download Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        // Button panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.add(uploadBtn);
        topPanel.add(downloadBtn);
        topPanel.add(deleteBtn);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"File Name", "Latest Version", "Uploaded At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fileTable = new JTable(tableModel);
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileTable.setRowHeight(25);

        // Header styling
        fileTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        fileTable.getTableHeader().setBackground(new Color(220, 220, 220));

        // Zebra striping
        fileTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Load files into table
        loadFiles();

        // Upload button action
        uploadBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int res = fc.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    VaultManager.storeFile(file.toPath());
                    JOptionPane.showMessageDialog(this, "File uploaded successfully!");
                    loadFiles(); // refresh table
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        // Download button action
        downloadBtn.addActionListener(e -> {
            int selectedRow = fileTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a file to download!");
                return;
            }

            String fileName = tableModel.getValueAt(selectedRow, 0).toString();
            List<FileMeta> versions = VersionManager.getAllVersions(fileName);
            Integer[] versionNumbers = versions.stream().map(f -> f.version).toArray(Integer[]::new);

            Integer selectedVersion = (Integer) JOptionPane.showInputDialog(
                    this,
                    "Select version",
                    "File Versions",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    versionNumbers,
                    versionNumbers[versionNumbers.length - 1]
            );

            if (selectedVersion != null) {
                JFileChooser fc = new JFileChooser();
                int saveRes = fc.showSaveDialog(this);
                if (saveRes == JFileChooser.APPROVE_OPTION) {
                    File destFile = fc.getSelectedFile();
                    try {
                        VaultManager.retrieveFile(fileName, selectedVersion, destFile.toPath());
                        JOptionPane.showMessageDialog(this, "File downloaded successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                    }
                }
            }
        });

//DELETE BUTTON ACTION
        // Delete button action
        deleteBtn.addActionListener(e -> {
            int selectedRow = fileTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a file to delete!");
                return;
            }

            String fileName = tableModel.getValueAt(selectedRow, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete '" + fileName + "' and all its versions?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    VaultManager.deleteFile(fileName);
                    JOptionPane.showMessageDialog(this, "File deleted successfully!");
                    loadFiles(); // refresh table
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

    }


    private void loadFiles() {
        tableModel.setRowCount(0);
        Map<String, List<FileMeta>> allFiles = VaultManager.getAllFiles();
        for (String fileName : allFiles.keySet()) {
            List<FileMeta> versions = allFiles.get(fileName);
            FileMeta latest = versions.get(versions.size() - 1);
            tableModel.addRow(new Object[]{fileName, latest.version, latest.uploadedAt});
        }
    }
}
