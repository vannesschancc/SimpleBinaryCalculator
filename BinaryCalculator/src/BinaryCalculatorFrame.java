import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BinaryCalculatorFrame extends JFrame {
    private JTextField display;
    private JRadioButton binary;
    private JRadioButton decimal;
    private ButtonGroup buttonGroup;
    private JButton zeroButton;
    private JButton oneButton;
    private JComboBox < String > operator;
    private JButton compute;
    private JButton clear;
    private int currentOp;
    private int displayedOperand;
    private int operand1;
    private int operand2;

    // Method Name		: BinaryCalculatorFrame
    // Parameters		: None 
    // Return value(s)	: None 
    // Partners     	: None
    // Description		: This is constructor, which will construct the JButtons, JRadiobuttons, JButtongroup and Jcombobox 

    public BinaryCalculatorFrame() {
        //calling JFrame's constructor
        super("Binary Calculator");
        //set the layout
        setLayout(new FlowLayout());

        // create components needed 
        display = new JTextField("0", 20);
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display);

        // set up RadioButtons, add them into the JFrame and put them into ButtonGroup
        binary = new JRadioButton("Binary", true);
        decimal = new JRadioButton("Decimal", false);
        add(binary);
        add(decimal);

        // No need for handlers as the states will only be used by compute when it changes the 
        //value of the display text field.
        buttonGroup = new ButtonGroup();
        buttonGroup.add(binary);
        buttonGroup.add(decimal);


        // set up the buttons and button handlers
        zeroButton = new JButton("0");
        oneButton = new JButton("1");
        add(zeroButton);
        add(oneButton);

        // adding zeros and ones handler
        zeroButton.addActionListener(new ButtonHandler());
        oneButton.addActionListener(new ButtonHandler());

        // set up the Operator 
        String[] choice = {
            "OP", "+", "-", "x", "/"
        };
        operator = new JComboBox < String > (choice);
        add(operator);
        //set up the operator handler;
        operator.addItemListener(new OperatorHandler());

        //set up the compute 
        compute = new JButton("Compute");
        add(compute);
        compute.addActionListener(new ButtonHandler());

        // set up clear 
        clear = new JButton("Clear");
        add(clear);
        clear.addActionListener(new ButtonHandler());
    }

    //nested class
    private class ButtonHandler implements ActionListener {

        // Method Name		: actionPerformed 
        // Parameters		: e, an object of ActionEvent
        // Return value(s)	: None
        // Partners     	: None
        // Description		: It handles the events triggered by buttons 

        public void actionPerformed(ActionEvent e) {
                //convert the current operand into decimal 
                String currentOperand = Integer.toString(displayedOperand);
                String answerString = "";
                int answer = 0;
                int answerInt;
                // adding 1s or 0s 
                if (e.getSource() == zeroButton) {
                    if (currentOperand.charAt(0) == '0') {
                        currentOperand = "";
                    }
                    //add a  0 digits at the end of displayedOperand
                    currentOperand = currentOperand + "0";
                    displayedOperand = Integer.parseInt(currentOperand);
                    //update display
                    display.setText(currentOperand);
                }
                if (e.getSource() == oneButton) {
                    if (currentOperand.charAt(0) == '0') {
                        currentOperand = "";
                    }
                    //add a 1 digits at the end of displayedOperand
                    currentOperand += "1";
                    displayedOperand = Integer.parseInt(currentOperand);
                    //update display
                    display.setText(currentOperand);
                }

                if (e.getSource() == compute) {
                    operand2 = displayedOperand;
                    String Boperand1 = Integer.toString(operand1);
                    String Boperand2 = Integer.toString(operand2);
                    int Doperand1 = Integer.parseInt(Boperand1, 2);
                    int Doperand2 = Integer.parseInt(Boperand2, 2);
                    //perform binary calculation
                    if (currentOp == 1) {
                        //Perform addition
                        answer = Doperand1 + Doperand2;
                        // update operand
                        answerString = Integer.toBinaryString(answer);
                        display.setText("answer is" + answerString);
                        // update the operator to op
                        operator.setSelectedIndex(0);
                    } else if (currentOp == 2) {
                        //Perform subtraction
                        answer = Doperand1 - Doperand2;
                        // update operand
                        answerString = Integer.toBinaryString(answer);
                        // update the operator to op
                        operator.setSelectedIndex(0);
                    } else if (currentOp == 3) {
                        //Perform multiplication
                        answer = Doperand1 * Doperand2;
                        // update operand
                        answerString = Integer.toBinaryString(answer);

                        // update the operator.
                        operator.setSelectedIndex(0);
                    } else if (currentOp == 4) {
                        //Perform division
                        try {
                            answer = Doperand1 / Doperand2;
                            // update operand
                            answerString = Integer.toBinaryString(answer);
                            // update the operator to op
                            operator.setSelectedIndex(0);
                        } catch (ArithmeticException AE) {
                            display.setText("Error");
                        }
                    } else if (currentOp == 0) {
                        //not doing anything 
                    } // end of calculation

                    //update the displayedOperand
                    displayedOperand = Integer.parseInt(answerString);

                    //determine which mode to display`
                    if (binary.isSelected()) {
                        display.setText(answerString);
                    } else if (decimal.isSelected()) {
                        answerInt = Integer.parseInt(answerString, 2);
                        display.setText(Integer.toString(answerInt));
                    } // end of calculation	
                } // end of compute 

                // clearing  value
                if (e.getSource() == clear) {
                    //reset all values
                    displayedOperand = 0; //displayed text should be 0 
                    operand1 = 0;
                    operand2 = 0;
                    currentOp = 0; //selected Operator should be changed to 0
                    display.setText("0");
                }
            } //end of actionPerformed
    } //end of ButtonHandler

    //nested class 
    private class OperatorHandler implements ItemListener {

        // Method Name		: OperatorHandler
        // Parameters		: None 
        // Return value(s)	: None
        // Partners     	: None
        // Description		: The constructor did not perform any task 

        public OperatorHandler() {

        }

        // Method Name		: itemStateChanged 	
        // Parameters		: e, an object of ItemeEvent
        // Return value(s)	: None
        // Partners     	: None
        // Description		: It handled the changes in operator. 

        public void itemStateChanged(ItemEvent e) {
            // Determine whether item is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //set the output to corresponding operator
                currentOp = operator.getSelectedIndex();
                //update the operand 
                operand1 = displayedOperand;
                display.setText("0");
                displayedOperand = 0;
            } else {
                currentOp = 0;
            }
        }
    }
}
