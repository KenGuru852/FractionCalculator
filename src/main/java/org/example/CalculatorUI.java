package org.example;

import javax.swing.*;
import java.awt.*;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CalculatorUI extends JFrame {

    private JTextField textField;

    public CalculatorUI() {
        setTitle("CalcuLALALALALAtor");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setPreferredSize(new Dimension(textField.getWidth(), 60));
        textField.setEditable(true);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 40));
        add(textField, BorderLayout.NORTH);

        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("[0-9+\\-*/|\\^]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9+\\-*/|\\^]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 20, 20));

        String[] buttons = {
                "M+", "MS", "MR", "MC",
                "SQR", "REV", "B", "-",
                "7", "8", "9", "+",
                "4", "5", "6", "*",
                "1", "2", "3", "/",
                "0", "|", "=", "CE"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(e -> onButtonClick(text));
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton buttonC = new JButton("C");
        buttonC.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonC.addActionListener(e -> onButtonClick("C"));
        bottomPanel.add(buttonC);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void onButtonClick(String buttonText) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorUI calculator = new CalculatorUI();
            calculator.setVisible(true);
        });
    }
}