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

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Правка");
        JMenuItem copyItem = new JMenuItem("Копировать");
        JMenuItem pasteItem = new JMenuItem("Вставить");

        copyItem.addActionListener(e -> copyInBuffer());
        pasteItem.addActionListener(e -> pasteFromBuffer());

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        menuBar.add(editMenu);
        menuPanel.add(menuBar);

        JButton helpButton = new JButton("Справка");
        helpButton.addActionListener(e -> showHelp());
        menuPanel.add(helpButton);

        topPanel.add(menuPanel, BorderLayout.NORTH);

        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setPreferredSize(new Dimension(100, 50));
        textField.setEditable(true);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 36));
        topPanel.add(textField, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("[0-9+\\-*/¿|\\^]+") || string.isEmpty()) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9+\\-*/¿|\\^]+") || text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 10, 10));

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton buttonC = new JButton("C");
        buttonC.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonC.addActionListener(e -> onButtonClick("C"));
        bottomPanel.add(buttonC);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void onButtonClick(String buttonText) {
        String currentText = textField.getText();

        switch (buttonText) {
            case "REV" -> {
                textField.setText(currentText + "¿");
            }
            case "SQR" -> {
                textField.setText(currentText + "^");
            }
            case "9" -> {
                textField.setText(currentText + "9");
            }
            case "8" -> {
                textField.setText(currentText + "8");
            }
            case "7" -> {
                textField.setText(currentText + "7");
            }
            case "6" -> {
                textField.setText(currentText + "6");
            }
            case "5" -> {
                textField.setText(currentText + "5");
            }
            case "4" -> {
                textField.setText(currentText + "4");
            }
            case "3" -> {
                textField.setText(currentText + "3");
            }
            case "2" -> {
                textField.setText(currentText + "2");
            }
            case "1" -> {
                textField.setText(currentText + "1");
            }
            case "0" -> {
                textField.setText(currentText + "0");
            }
            case "+" -> {
                textField.setText(currentText + "+");
            }
            case "-" -> {
                textField.setText(currentText + "-");
            }
            case "/" -> {
                textField.setText(currentText + "/");
            }
            case "*" -> {
                textField.setText(currentText + "*");
            }
            case "|" -> {
                textField.setText(currentText + "|");
            }
            case "B" -> {
                if (!currentText.isEmpty()) {
                    textField.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
            case "C" -> {
                textField.setText("");
                Buffer.clear();
                Memory.clear();
            }
            case "CE" -> {
                textField.setText("");
            }
            case "MR" -> {
                FractionNumber fromMemory = Memory.read();
                textField.setText(currentText + fromMemory.numerator + "|" + fromMemory.denominator);
            }
            case "MS" -> {
                Memory.save(parseFraction(currentText));
                System.out.print(Memory.read().numerator);
                System.out.print("/");
                System.out.println(Memory.read().denominator);
            }
            case "MC" -> {
                Memory.clear();
            }
            case "M+" -> {
                try {
                    FractionNumber currentValue = parseFraction(currentText);
                    Memory.add(currentValue);
                    System.out.print(Memory.read().numerator);
                    System.out.print("/");
                    System.out.println(Memory.read().denominator);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "=" -> {
                try {
                    String toHistory = currentText + "=";
                    FractionNumber result = Calculate.calculate(currentText + "=");
                    textField.setText(result.numerator + "|" + result.denominator);
                    History.writeToFile(toHistory + result.numerator + "|" + result.denominator);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    // 4/1 + 11/2 = 19/2
    private void copyInBuffer() {
        Buffer.copy(textField.getText());
    }

    private void pasteFromBuffer() {
        textField.setText(textField.getText() + Buffer.read());
    }

    private void showHelp() {
        JFrame helpFrame = new JFrame("Справка");
        helpFrame.setSize(300, 200);
        helpFrame.setLocationRelativeTo(this);

        JTextArea helpText = new JTextArea(
                """
                        КАЛЬКУЛЯТОР сделал
                        Студент группы ИП-112
                        Притула Алексей Дмитриевич
                        """
        );
        helpText.setEditable(false);
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);

        helpFrame.add(new JScrollPane(helpText), BorderLayout.CENTER);
        helpFrame.setVisible(true);
    }

    private FractionNumber parseFraction(String text) {
        String[] parts = text.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Некорректный формат дроби. Используйте формат 'числитель|знаменатель'.");
        }
        try {
            int numerator = Integer.parseInt(parts[0]);
            int denominator = Integer.parseInt(parts[1]);
            return new FractionNumber(numerator, denominator);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorUI calculator = new CalculatorUI();
            calculator.setVisible(true);
        });
    }
}