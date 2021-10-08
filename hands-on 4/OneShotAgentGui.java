package examples.behaviours;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OneShotAgentGui extends JFrame {

    private JTextField xField;
    public String equis;
    public OneShotAgent myAgent;

    public OneShotAgentGui(OneShotAgent a) {
        super(a.getLocalName());
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
                    equis = xField.getText().trim();
                    myAgent.calcular(Integer.parseInt(equis));
                    xField.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(OneShotAgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                }
                
                /*
                equis = xField.getText().trim();
                myAgent.calcular(Integer.parseInt(equis));
                xField.setText("");
                */
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