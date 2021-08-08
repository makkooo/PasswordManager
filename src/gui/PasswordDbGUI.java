package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import passwordmanager.Crypto;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Component;
import javax.swing.SwingConstants;

/*
 * Creates the Password Database GUI
 * where users can view their password records
 * and do operations like add new record, 
 * edit existing record, an delete record.
 */
@SuppressWarnings("serial")
public class PasswordDbGUI extends JFrame {
	
	private static JTable table;
	private JPanel contentPane;
	
	private int posX, posY;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PasswordDbGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PasswordDbGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PasswordDbGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PasswordDbGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordDbGUI frame = new PasswordDbGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PasswordDbGUI() {
		
		/*
		 * Loads the font San Fransisco Pro.
		 */
		InputStream inputStream = LoginGUI.class.getResourceAsStream("/res/SFPro_Semibold.otf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		
		/*
		 * Creates the Frame.
		 */
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt) {
		        posX = evt.getX();
		        posY = evt.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt) {			
				setLocation(getLocation().x + evt.getX() - posX, 
						    getLocation().y + evt.getY() - posY);		
			}
		});
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 530);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * JFrame title.
		 */
		
		JLabel passlock_Icon = new JLabel("");
		passlock_Icon.setIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/passLock_icon.jpg")));
		passlock_Icon.setBounds(5, 1, 24, 24);
		contentPane.add(passlock_Icon);
		JLabel title_Label = new JLabel("Password Manager");
		title_Label.setVerticalAlignment(SwingConstants.BOTTOM);
		title_Label.setBounds(33, 0, 200, 24);
		title_Label.setFont(font.deriveFont(Font.PLAIN, 14));
		title_Label.setText(LoginGUI.passwordVault.getName());
		contentPane.add(title_Label);
		
		/*
		 * Creates a JButton that minimizes the frame.
		 */
		JButton min_Button = new JButton("");
		min_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		min_Button.setBackground(Color.WHITE);
		min_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/min_icon.jpg")));
		min_Button.setFocusable(false);
		min_Button.setBounds(900, 5, 24, 24);
		contentPane.add(min_Button);
		
		/*
		 * Creates a JButton that encrypts the
		 * password vault and closes the frame.
		 */
		JButton close_Button = new JButton("");
		close_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Crypto aes = new Crypto();
				String plainText = "", encryptedText;
				FileWriter writer = null;
				try {
					writer = new FileWriter(LoginGUI.passwordVault);
					for(int i=0; i<table.getRowCount(); i++) {
						for(int j=0; j < table.getColumnCount(); j++) {
							plainText += (String)table.getValueAt(i, j) + "\t";
						}
					plainText += "\r\n";
					}
					encryptedText = aes.encrypt(plainText.getBytes(), LoginGUI.password);
					writer.write(encryptedText);
				} catch(IOException ioe) {
					ioe.printStackTrace();
				} finally {
					try {
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				//Closes the frame.
				System.exit(0);
			}
		});
		close_Button.setBackground(Color.WHITE);
		close_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/close_icon.jpg")));
		close_Button.setFocusable(false);
		close_Button.setBounds(930, 5, 24, 24);
		contentPane.add(close_Button);
		
		/*
		 * Creates ScrollPane for the Table.
		 */
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		scrollPane_1.setBackground(Color.WHITE);
		scrollPane_1.setBounds(new Rectangle(10, 100, 69, 69));
		scrollPane_1.setBounds(215, 40, 715, 460);
		contentPane.add(scrollPane_1);
		
		/*
		 * Creates the Table for the password records.
		 */
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setFont(font.deriveFont(Font.PLAIN, 14));
		table.getTableHeader().setFont(font.deriveFont(Font.PLAIN, 14));
		table.setBounds(new Rectangle(20, 20, 20, 20));
		table.setBorder(null);
		table.setRowHeight(30);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"URL", "Username", "Password"
			}
		));
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		/*
		 * Creates the table header.
		 */
		String[] columnName = {"URL", "Username", "Password"};
		tableModel.setColumnIdentifiers(columnName);
		
		/*
		 * Scans the VaultContents per line 
		 * and store it in a String[]. 
		 */
		String[] tableContents = LoginGUI.vaultContents.split("\\r\\n");
		
		/*
		 * Iterate through the String[] and
		 * split the string on "\t" and set
		 * the strings per row.
		 */
		for(int i=0; i<tableContents.length; i++) {
			String line = tableContents[i].toString().trim();
			String[] dataRow = line.split("\\t");
			tableModel.addRow(dataRow);
		}
		scrollPane_1.setViewportView(table);
		
		/*
		 * Creates a Label where errors within the 
		 * Password Database GUI is displayed.
		 */
		JLabel errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(font.deriveFont(Font.PLAIN, 12));
		errorLbl.setBounds(180, 543, 200, 20);
		contentPane.add(errorLbl);
		
		/*
		 * Creates a "Add" button that allows
		 * the user to add new record/s.
		 */
		JButton addBtn = new JButton("");
		addBtn.setToolTipText("Add new password record");
		addBtn.setFocusable(false);
		addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addBtn.setPressedIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/add_iconPressed.jpg")));
		addBtn.setIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/add_icon.jpg")));
		addBtn.setBorder(null);
		addBtn.setBackground(Color.WHITE);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddRecord addFrame = new AddRecord();
				addFrame.setVisible(true);
				addFrame.setLocationRelativeTo(null);
				addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		addBtn.setBounds(33, 303, 50, 50);
		contentPane.add(addBtn);
		
		/*
		 * Creates a "Delete" button that allows
		 * the user to delete record/s.
		 */
		JButton deleteBtn = new JButton("");
		deleteBtn.setToolTipText("Delete password record");
		deleteBtn.setFocusable(false);
		deleteBtn.setPressedIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/delete_iconPressed.jpg")));
		deleteBtn.setIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/delete_icon.jpg")));
		deleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteBtn.setBorder(null);
		deleteBtn.setBackground(Color.WHITE);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				
				errorLbl.setText(null);
				
				//If number of selected rows is 1
				if(table.getSelectedRowCount() == 1) {
					
					/*
					 * Displays a confirmation dialog
					 * for record deletion.
					 */
					int optDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected record?", "Delete Password Record", JOptionPane.YES_NO_OPTION);
					//If user selects "Yes"
					if(optDelete == 0)
						//Deletes the selected row
						tableModel.removeRow(table.getSelectedRow());
				}
				//If user has no selected rows
				else if(table.getSelectedRowCount() == 0) 
					errorLbl.setText("Please select a row to delete");
				//If table is currently empty
				else if(table.getRowCount() == 0)
					errorLbl.setText("Error! Table is currently empty");
				//If user selects multiple rows
				else
					errorLbl.setText("Error! Please select a single row");
			}
		});
		deleteBtn.setBounds(136, 303, 50, 50);
		contentPane.add(deleteBtn);
		
		JLabel illus_icon = new JLabel("");
		illus_icon.setIcon(new ImageIcon(PasswordDbGUI.class.getResource("/res/illus_icon.jpg")));
		illus_icon.setBounds(1, 319, 215, 200);
		contentPane.add(illus_icon);
	}
	
	/*
	 * Adds a new row to the table.
	 * 
	 * @param recordRow An array of objects 
	 *                  containing row data.
	 */
	public static void AddRowToJTable(Object[] recordRow) {
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.addRow(recordRow);
	}
}
