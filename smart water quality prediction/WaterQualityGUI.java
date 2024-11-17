import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WaterQualityGUI extends JFrame {

    // Define UI components
    private JTextField phField;
    private JTextField hardnessField;
    private JTextField solidsField;
    private JTextField chloraminesField;
    private JTextField sulfateField;
    private JTextField conductivityField;
    private JTextField organicCarbonField;
    private JTextField trihalomethanesField;
    private JTextField turbidityField;
    private JLabel resultLabel;
    private JButton predictButton;
    private Image backgroundImage;

    public WaterQualityGUI() {
        // Loading the background image
        backgroundImage = new ImageIcon("background.jpg").getImage();

        // Initializing components
        phField = new JTextField(5);
        hardnessField = new JTextField(5);
        solidsField = new JTextField(5);
        chloraminesField = new JTextField(5);
        sulfateField = new JTextField(5);
        conductivityField = new JTextField(5);
        organicCarbonField = new JTextField(5);
        trihalomethanesField = new JTextField(5);
        turbidityField = new JTextField(5);
        resultLabel = new JLabel("Potability:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Increase font size
        predictButton = new JButton("Predict");

        // Setting layout
        setContentPane(new BackgroundPanel());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding all labels with WHO-specified ranges
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("pH [6.5-8.5]:"), gbc);
        gbc.gridx = 1;
        add(phField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Hardness [50-300 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(hardnessField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Solids [0-500 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(solidsField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Chloramines [0-4 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(chloraminesField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Sulfate [0-250 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(sulfateField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Conductivity [0-2000 µS/cm]:"), gbc);
        gbc.gridx = 1;
        add(conductivityField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Organic Carbon [0-5 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(organicCarbonField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Trihalomethanes [0-0.1 mg/L]:"), gbc);
        gbc.gridx = 1;
        add(trihalomethanesField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Turbidity [0-5 NTU]:"), gbc);
        gbc.gridx = 1;
        add(turbidityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(predictButton, gbc);
        gbc.gridy = 10;
        add(resultLabel, gbc);

        // Adding button action listener
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get input values
                    String ph = phField.getText();
                    String hardness = hardnessField.getText();
                    String solids = solidsField.getText();
                    String chloramines = chloraminesField.getText();
                    String sulfate = sulfateField.getText();
                    String conductivity = conductivityField.getText();
                    String organicCarbon = organicCarbonField.getText();
                    String trihalomethanes = trihalomethanesField.getText();
                    String turbidity = turbidityField.getText();

                    // Constructing the command to execute the Python script
                    String command = String.format("python3 predict.py %s %s %s %s %s %s %s %s %s",
                            ph, hardness, solids, chloramines, sulfate, conductivity, organicCarbon, trihalomethanes, turbidity);

                    // Executing the command
                    Process process = Runtime.getRuntime().exec(command);

                    // Read the output from the Python script
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String result = reader.readLine();

                    // Check if extreme values
                    boolean isExtreme = checkExtremeValues(Float.parseFloat(ph), Float.parseFloat(hardness), Float.parseFloat(solids),
                            Float.parseFloat(chloramines), Float.parseFloat(sulfate), Float.parseFloat(conductivity),
                            Float.parseFloat(organicCarbon), Float.parseFloat(trihalomethanes), Float.parseFloat(turbidity));

                    // Display the result
                    if (isExtreme) {
                        showMessage("Extreme values detected! The water cannot be used for drinking considering WHO  and Other Studies parameters.",
                                "WHO & Studies  Guidelines");
                    } else {
                        // Change potability display text
                        resultLabel.setText("Potability: " + ("1".equals(result) ? "water can be used for drinking" : "water cannot be used for drinking"));
                    }

                    // Show the explanation if the prediction is potable but extreme
                    if ("1".equals(result) && isExtreme) {
                        showMessage(" the dataset used in machine learning is not trained for extreme values. " +
                                        "Please note the following values are outside WHO and Other Studies recommended range:\n" +
                                        getExtremeValuesExplanation(ph, hardness, solids, chloramines, sulfate, conductivity, organicCarbon, trihalomethanes, turbidity),
                                "Prediction Explanation");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        // Frame settings
        setTitle("Water Quality Predictor");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Custom JPaneling to draw background image
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
    
    
    
    
    
    
  




    private boolean checkExtremeValues(float ph, float hardness, float solids, float chloramines, float sulfate,
                                       float conductivity, float organicCarbon, float trihalomethanes, float turbidity) {
        // Example WHO and other studies limits 
        return ph < 6.5 || ph > 8.5 ||
               hardness < 50 || hardness > 300 ||
               solids < 0 || solids > 500 ||
               chloramines < 0 || chloramines > 4 ||
               sulfate < 0 || sulfate > 250 ||
               conductivity < 0 || conductivity > 2000 ||
               organicCarbon < 0 || organicCarbon > 5 ||
               trihalomethanes < 0 || trihalomethanes > 0.1 ||
               turbidity < 0 || turbidity > 5;
    }

    private String getExtremeValuesExplanation(String ph, String hardness, String solids, String chloramines, String sulfate,
                                               String conductivity, String organicCarbon, String trihalomethanes, String turbidity) {
        StringBuilder explanation = new StringBuilder("The following values are outside WHO and Other Studies recommended ranges:\n");
        if (Float.parseFloat(ph) < 6.5 || Float.parseFloat(ph) > 8.5) explanation.append("pH: ").append(ph).append("\n");
        if (Float.parseFloat(hardness) < 50 || Float.parseFloat(hardness) > 300) explanation.append("Hardness: ").append(hardness).append("\n");
        if (Float.parseFloat(solids) < 0 || Float.parseFloat(solids) > 500) explanation.append("Solids: ").append(solids).append("\n");
        if (Float.parseFloat(chloramines) < 0 || Float.parseFloat(chloramines) > 4) explanation.append("Chloramines: ").append(chloramines).append("\n");
        if (Float.parseFloat(sulfate) < 0 || Float.parseFloat(sulfate) > 250) explanation.append("Sulfate: ").append(sulfate).append("\n");
        if (Float.parseFloat(conductivity) < 0 || Float.parseFloat(conductivity) > 2000) explanation.append("Conductivity: ").append(conductivity).append("\n");
        if (Float.parseFloat(organicCarbon) < 0 || Float.parseFloat(organicCarbon) > 5) explanation.append("Organic Carbon: ").append(organicCarbon).append("\n");
        if (Float.parseFloat(trihalomethanes) < 0 || Float.parseFloat(trihalomethanes) > 0.1) explanation.append("Trihalomethanes: ").append(trihalomethanes).append("\n");
        if (Float.parseFloat(turbidity) < 0 || Float.parseFloat(turbidity) > 5) explanation.append("Turbidity: ").append(turbidity).append("\n");
        return explanation.toString();
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WaterQualityGUI gui = new WaterQualityGUI();
            gui.setVisible(true);
        });
    }
}

