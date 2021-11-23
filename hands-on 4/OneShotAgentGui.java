package examples.behaviours;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OneShotAgentGui extends JFrame {

    private OneShotAgent myAgent;
    private JTextField xField;
    public double equis;

    public OneShotAgentGui(OneShotAgent a) {
        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.add(new JLabel("X:"));
        xField = new JTextField(15);
        p.add(xField);
        getContentPane().add(p, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    equis = Double.parseDouble(xField.getText());
                    myAgent.inicializar();
                    xField.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(OneShotAgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );	
		setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

}