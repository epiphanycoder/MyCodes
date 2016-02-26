package helloworld.fileManager;

import helloworld.AnswerGiver;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileExplorer extends JFrame {

    private JTextField filename;
    private JTextField inputField;
    private JTextField outputField;
    private FileSelector fileSelector;

    private JButton open;
    private JButton search;

    public FileExplorer() {

        filename = new JTextField();
        inputField = new JTextField();
        outputField = new JTextField(55);

        open = new JButton("Load Config File");
        search = new JButton("Search");

        outputField.setHorizontalAlignment(JTextField.CENTER);

        JPanel panel = new JPanel();
        panel.add(open);
        
        fileSelector = new FileSelector();

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int option = fileChooser.showOpenDialog(FileExplorer.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filesText = fileChooser.getSelectedFile().getAbsolutePath();
                    filename.setText(filesText);
                    initConfig();
                }
                if (option == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                }
            }
            private void initConfig() {
                fileSelector.loadConfig(filename.getText());
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AnswerGiver answerGiver = fileSelector.getAnswerGiver();

                if (answerGiver != null) {

                    String value = answerGiver.getMessages(inputField.getText());

                    if (value != null) {
                        outputField.setText(value);
                    } else {
                        outputField.setText("NOT FOUND");
                    }
                } else {
                    outputField.setText("NOT FOUND");
                }

            }
        });

        Container container = getContentPane();
        container.add(panel, BorderLayout.SOUTH);

        filename.setEditable(false);
        inputField.setEditable(true);
        outputField.setEditable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(filename);
        panel.add(inputField);
        panel.add(search);

        container.add(outputField, BorderLayout.CENTER);
        container.add(panel, BorderLayout.NORTH);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public FileSelector getFileSelector() {
        return fileSelector;
    }
}