import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TaskCheckerPanel extends JFrame implements ItemListener {
    private JPanel taskPanel = new JPanel();
    private JLabel pickName,pickTask,selectedUser,selectedTask;
    private JComboBox<String> nameDropDown, taskDropDown;
    private JButton checkButton;
    public TaskCheckerPanel(){
        setTitle("Complete Tasks");
        taskPanel.setSize(500,300);
        taskPanel.setLayout(new GridLayout(8,1,1,3));
        setResizable(false);
        setLayout(null);

        //ComboBox for the user that is completing their task
        pickName = new JLabel("Which user completed a task:");
        nameDropDown = new JComboBox<String>();
        for (String t: Tasks.ArrofNames){
            nameDropDown.addItem(t);
        }
        nameDropDown.addItemListener(this);
        selectedUser = new JLabel(nameDropDown.getSelectedItem() + " selected",JLabel.CENTER);

        //ComboBox to select which task was completed
        pickTask = new JLabel("Which task was completed:");
        taskDropDown = new JComboBox<String>();
        for (Tasks t: Tasks.ArrofTasks){
            if (t.getName().equals((String) nameDropDown.getSelectedItem())) {
                taskDropDown.addItem(t.getTaskOutline());
            }
        }
        taskDropDown.addItemListener(this);
        selectedTask = new JLabel(taskDropDown.getSelectedItem() + " selected",JLabel.CENTER);

        taskPanel.add(pickName);
        taskPanel.add(nameDropDown);
        taskPanel.add(selectedUser);
        taskPanel.add(pickTask);
        taskPanel.add(taskDropDown);
        taskPanel.add(selectedTask);
        checkButton = new JButton("Check");
        checkButton.addActionListener(new CheckButtonListener());
        taskPanel.add(checkButton);
        add(taskPanel);
        setMinimumSize(taskPanel.getSize());
        pack();
        setVisible(true);
    }
    // iterates through list to find specific task of a specific user then sets that task to completed
    private class CheckButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            for (Tasks t : Tasks.ArrofTasks){
                if (t.getName().equals((String) nameDropDown.getSelectedItem()))
                    if (t.getTaskOutline().equals((String) taskDropDown.getSelectedItem()))
                        t.setCompleted(true);
            }
            PanelListItems saver = new PanelListItems();
            saver.saveNames("names.txt");
            saver.saveTasks("tasks.txt");
            saver.table.getRowCount();
            for (int i=saver.table.getRowCount()-1;i>=0;i--)
                saver.model.removeRow(i);
            saver.showTable();
            dispose();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == nameDropDown){
            selectedUser.setText(nameDropDown.getSelectedItem() + " selected");
            taskDropDown.removeAllItems();;
            for (Tasks t: Tasks.ArrofTasks){
                if (t.getName().equals((String) nameDropDown.getSelectedItem())) {
                    taskDropDown.addItem(t.getTaskOutline());
                }
            }
        }
        if (e.getSource() == taskDropDown){
            selectedTask.setText(taskDropDown.getSelectedItem() + " selected");
        }

    }
}
