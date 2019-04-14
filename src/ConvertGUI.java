import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ConvertGUI extends JFrame{

	private JPanel contentPane;
	private JFileChooser chooser;
	private JButton selectInFileBtn;
	private JButton selectOutFileBtn;
	private JButton convertBtn;
	private JTextField input;
	private JTextField output;
	private JPanel panel;
	private JPanel panelUp;
	private JPanel panelDown;

	/**
	 * Create the frame.
	 */
	public ConvertGUI(){
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 251, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
		
		panel = new JPanel();
		panelUp = new JPanel();
		panelDown = new JPanel();
	    panelUp.setLayout(new GridLayout(2,2,10,0));
		selectInFileBtn = new JButton("Select");
		selectOutFileBtn = new JButton("Select");
		chooser = new JFileChooser();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		input = new JTextField("Input File Path...");
		output = new JTextField("Output Directory Path...");
		
		panelUp.add(input);
		panelUp.add(selectInFileBtn);
		panelUp.add(output);
		panelUp.add(selectOutFileBtn);
		panel.add(panelUp);
		panel.add(panelDown);
		panelDown.setLayout(new CardLayout(0, 0));
		convertBtn = new JButton("Convert");
		panelDown.add(convertBtn, "name_50649954272336");
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		setTitle("Primal to Dual Problem Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 364, 98);
		setResizable(false);
		
		convertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e){	
				PrimalProblem pp = new PrimalProblem(readFile());
				DualProblem dp = new DualProblem(pp);
				Convert(pp, dp);
			}
			
		});
		
		selectInFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
				chooser.setDialogTitle("Choose Input File");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) 
		        	input.setText(chooser.getSelectedFile().getAbsolutePath());
			}

		});
		
		selectOutFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
				chooser.setDialogTitle("Choose Input File");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//chooser.showOpenDialog(null);
				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) 
		        	output.setText(chooser.getSelectedFile().getAbsolutePath());
			}

		});
	
}
	
String readFile() {
		
		/* This method reads the problem from txt file and returns it to lowercase string */

		String lp="";
		File inFile = new File(input.getText());
        if (inFile.exists()){
            FileReader freader = null;
			try {
				freader = new FileReader(inFile);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error in reading file! Check path & try again.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
            BufferedReader reader = new BufferedReader(freader);
            String line="";
            try {
				line = reader.readLine();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error in reading file! Check path & try again.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
            while (line != null){
            if (!line.trim().isEmpty()) {
                lp += line.trim();
                lp += "\n";
            }
                try {
					line = reader.readLine();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error in reading file! Check path & try again.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
            }
            try {
				reader.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error in reading file! Check path & try again.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
        }

		return lp.toLowerCase();
      
	}
	
	boolean inFileCheck(String inFile) {
		if (inFile == "") return false;
		return true;
	}

void Convert(PrimalProblem pp, DualProblem dp) {
	
	/* This method does the parsing & conversion */

	if(inFileCheck(input.getText()))
		dp.writeFile(output.getText());
}
	
}