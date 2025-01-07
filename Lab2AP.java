import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Lab2AP extends JFrame {
    private JTextField textField;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JLabel imageLabel;
    private JRadioButton highPriority;
    private JRadioButton mediumPriority;
    private JRadioButton lowPriority;

    public Lab2AP() {
        setTitle("Advanced To-Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input field and label
        textField = new JTextField(20);
        JLabel addLabel = new JLabel("Add Task:");
        JPanel panel = new JPanel();
        panel.add(addLabel);
        panel.add(textField);
        add(panel, BorderLayout.NORTH);

        // List model and JList
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel with add, remove, save, load buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        saveButton.addActionListener(e -> saveTasks());
        loadButton.addActionListener(e -> loadTasks());
        

        // Priority selection panel
        JPanel priorityPanel = new JPanel();
        highPriority = new JRadioButton("High");
        mediumPriority = new JRadioButton("Medium");
        lowPriority = new JRadioButton("Low");
        ButtonGroup priorityGroup = new ButtonGroup();
        priorityGroup.add(highPriority);
        priorityGroup.add(mediumPriority);
        priorityGroup.add(lowPriority);
        priorityPanel.add(highPriority);
        priorityPanel.add(mediumPriority);
        priorityPanel.add(lowPriority);
        add(priorityPanel, BorderLayout.EAST);

        // Set up image label
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("place the path here")); 
        add(imageLabel, BorderLayout.WEST);

        pack();
        setVisible(true);
    }

    private void addTask() {
        String item = textField.getText();
        if (!item.isEmpty()) {
            String priority = "Medium"; // Default priority
            if (highPriority.isSelected()) {
                priority = "High";
            } else if (lowPriority.isSelected()) {
                priority = "Low";
            }
            listModel.addElement(item + " [" + priority + "]");
            textField.setText("");
            highPriority.setSelected(false);
            mediumPriority.setSelected(true); // Reset to default
            lowPriority.setSelected(false);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task.");
        }
    }

    private void removeTask() {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("To-Do-List.txt"))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.getElementAt(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("To-Do-List.txt"))) {
            String line;
            listModel.clear(); // Clear existing tasks
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
SwingUtilities.invokeLater(Lab2AP::new);
    }
}