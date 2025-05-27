package projekt2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TDietSwing extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField calorieField;
    private JTextArea planTextArea;
    private JButton generateButton;
    private JLabel calorieLabel;

    public TDietSwing() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Plan Dietetyczny (Accessible Version)");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //panel wejściowy z layoutem FlowLayout
        JPanel inputPanel = new JPanel(new FlowLayout());
        calorieLabel = new JLabel("Podaj kalorie (1600-2000):");
        calorieField = new JTextField(5);
        generateButton = new JButton("Generuj plan");

        inputPanel.add(calorieLabel);
        inputPanel.add(calorieField);
        inputPanel.add(generateButton);

        //pole tekstowe z obszarem przewijania, służące do wyświetlania planu
        planTextArea = new JTextArea();
        planTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(planTextArea);

        //ustawianie kontekstu dostępności (AccessibleContext) dla komponentów
        calorieLabel.getAccessibleContext().setAccessibleName("Etykieta kalorii");
        calorieLabel.getAccessibleContext().setAccessibleDescription("Etykieta informująca, jakie kalorie należy wpisać.");

        calorieField.getAccessibleContext().setAccessibleName("Pole wejściowe kalorii");
        calorieField.getAccessibleContext().setAccessibleDescription("Wpisz wartość dziennego zapotrzebowania kalorycznego (1600-2000).");

        generateButton.getAccessibleContext().setAccessibleName("Przycisk generowania planu");
        generateButton.getAccessibleContext().setAccessibleDescription("Kliknij, aby wygenerować plan dietetyczny.");

        planTextArea.getAccessibleContext().setAccessibleName("Obszar wyświetlania planu");
        planTextArea.getAccessibleContext().setAccessibleDescription("Tutaj pojawi się wygenerowany plan dietetyczny.");

        //ułożenie komponentów w głównym oknie
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //dodanie akcji przycisku
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatePlanAction();
            }
        });
    }

    private void generatePlanAction() {
        String calorieText = calorieField.getText();
        int targetCalories;
        try {
            targetCalories = Integer.parseInt(calorieText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Podaj poprawną liczbę kalorii.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (targetCalories < 1600 || targetCalories > 2000) {
            JOptionPane.showMessageDialog(this,
                "Wartość musi być w zakresie 1600 - 2000.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        TDietProcess process = new TDietProcess();
        String plan = process.generatePlan(targetCalories);
        planTextArea.setText(plan);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TDietSwing().setVisible(true));
    }
}