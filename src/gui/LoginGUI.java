package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.AEADBadTagException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;

import passwordmanager.*;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

/*
 * Creates the Login GUI of the
 * Password Manager
 */
public class LoginGUI {

	private JFrame frmJapmPasswordManager;
	private JLabel passlocklogo_icon;
	private JButton min_Button;
	private JButton close_Button;
	private JTabbedPane tabbedPane;
	
	private JPanel openDB_Panel;
	private JPanel open_Panel;
	private JLabel db_Icon;
	private JTextField open_TextField;
	private JButton open_Button;
	private JPanel pass_Panel;
	private JLabel pass_Icon;
	private JPasswordField pass_PassField;
	private JCheckBox showpass_Button;
	private JLabel error_Label;
	private JButton login_Button;
	
	private JPanel createDB_Panel;
	private JPanel create_Panel;
	private JLabel create_db_Icon;
	private JTextField create_TextField;
	private JPanel create_pass_Panel;
	private JLabel create_pass_Icon;
	private JPasswordField create_pass_PassField;
	private JCheckBox create_showpass_Button;
	private JLabel create_error_Label;
	private JButton create_Button;
	
	public static File passwordVault;
	public static String password;
	public static String vaultContents;
	private int posX = 0, posY = 0;
	private JLabel tagline_label;
	
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
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frmJapmPasswordManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/*
		 * Creates 
		 */
		Crypto aes = new Crypto();
		
		Border whiteBorder = new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY);
		Border redBorder = new MatteBorder(1, 1, 2, 1, (Color) Color.RED);
		Border noBorder = new MatteBorder(0, 0, 0, 0, (Color) Color.WHITE);
		
		String defaultDir = System.getProperty("user.home") + "/Desktop";
		
		/*
		 * Creates a FileChooser with a .pldb file extension filter.
		 */
		JFileChooser dbFileChooser = new JFileChooser();
		dbFileChooser.setCurrentDirectory(new File(defaultDir));
		dbFileChooser.setFileFilter(new FileNameExtensionFilter("PassLock Password Database", "pldb"));
		
		
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
		 * Creates the JFrame for the Login.
		 */
		frmJapmPasswordManager = new JFrame();
		frmJapmPasswordManager.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt) {
		        posX = evt.getX();
		        posY = evt.getY();
			}
		});
		frmJapmPasswordManager.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt) {			
				frmJapmPasswordManager.setLocation(
						frmJapmPasswordManager.getLocation().x + evt.getX() - posX, 
						frmJapmPasswordManager.getLocation().y + evt.getY() - posY);		
			}
		});
		frmJapmPasswordManager.setBackground(Color.WHITE);
		frmJapmPasswordManager.getContentPane().setBackground(Color.WHITE);
		frmJapmPasswordManager.setResizable(false);
		frmJapmPasswordManager.setBounds(100, 100, 800, 500);
		frmJapmPasswordManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJapmPasswordManager.setUndecorated(true);
		frmJapmPasswordManager.setLocationRelativeTo(null);
		frmJapmPasswordManager.getContentPane().setLayout(null);
		
		/*
		 * JFrame title
		 */
		passlocklogo_icon = new JLabel("");
		passlocklogo_icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/logo_icon.jpg")));
		passlocklogo_icon.setBounds(25, 60, 150, 50);
		passlocklogo_icon.setFont(font.deriveFont(Font.PLAIN, 20));
		frmJapmPasswordManager.getContentPane().add(passlocklogo_icon);
		
		/*
		 * Creates a JButton that minimizes the frame.
		 */
		min_Button = new JButton("");
		min_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJapmPasswordManager.setState(JFrame.ICONIFIED);
			}
		});
		min_Button.setBackground(Color.WHITE);
		min_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/min_icon.jpg")));
		min_Button.setFocusable(false);
		min_Button.setBounds(740, 5, 24, 24);
		frmJapmPasswordManager.getContentPane().add(min_Button);
		
		/*
		 * Creates a JButton that closes the frame.
		 */
		close_Button = new JButton("");
		close_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		close_Button.setBackground(Color.WHITE);
		close_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/close_icon.jpg")));
		close_Button.setFocusable(false);
		close_Button.setBounds(770, 5, 24, 24);
		frmJapmPasswordManager.getContentPane().add(close_Button);
		
		/*
		 * Creates tabbed pane in the Login frame.
		 */
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setFocusable(false);
		tabbedPane.setBounds(400, 5, 401, 495);
		tabbedPane.setFont(font.deriveFont(Font.PLAIN, 14));
		frmJapmPasswordManager.getContentPane().add(tabbedPane);
		
		/*
		 * Creates the "Open Vault" tab.
		 * Allows the user to choose a password vault 
		 * and enter the master password.
		 */
		openDB_Panel = new JPanel();
		openDB_Panel.setBorder(null);
		openDB_Panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Open Vault", null, openDB_Panel, null);
		tabbedPane.setBackgroundAt(0, Color.WHITE);
		openDB_Panel.setLayout(null);
		
		open_Panel = new JPanel();
		open_Panel.setBackground(Color.WHITE);
		open_Panel.setBorder(whiteBorder);
		open_Panel.setBounds(50, 100, 290, 40);
		openDB_Panel.add(open_Panel);
		open_Panel.setLayout(null);
		
		db_Icon = new JLabel("");
		db_Icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/database_icon.jpg")));
		db_Icon.setBounds(1, 3, 30, 30);
		open_Panel.add(db_Icon);
		
		open_TextField = new JTextField();
		open_TextField.setEditable(false);
		open_TextField.setFont(font.deriveFont(Font.PLAIN, 14));
		open_TextField.setBorder(noBorder);
		open_TextField.setBackground(Color.WHITE);
		open_TextField.setBounds(40, 3, 210, 35);
		TextPrompt open_TextPrompt = new TextPrompt("Open Password Database", open_TextField);
		open_TextPrompt.changeAlpha(69);
		open_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		open_Panel.add(open_TextField);
		
		/*
		 * Opens a FileChooser and allows the user
		 * to open password vault with .pldb file extension.
		 */
		open_Button = new JButton("");
		open_Button.setToolTipText("Open File");
		open_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = dbFileChooser.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					passwordVault = dbFileChooser.getSelectedFile();
					open_TextField.setText(passwordVault.getName());
				}
			}
		});
		open_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		open_Button.setBackground(Color.WHITE);
		open_Button.setBorder(null);
		open_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/open_icon.jpg")));
		open_Button.setFocusable(false);
		open_Button.setBounds(250, 3, 35, 35);
		open_Panel.add(open_Button);
		
		pass_Panel = new JPanel();
		pass_Panel.setBackground(Color.WHITE);
		pass_Panel.setBorder(whiteBorder);
		pass_Panel.setBounds(50, 200, 290, 40);
		openDB_Panel.add(pass_Panel);
		pass_Panel.setLayout(null);
		
		pass_Icon = new JLabel("");
		pass_Icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_icon.jpg")));
		pass_Icon.setBounds(1, 3, 30, 30);
		pass_Panel.add(pass_Icon);
		
		/*
		 * Creates a PasswordField where users can 
		 * enter the master password to a password vault.
		 */
		pass_PassField = new JPasswordField();
		pass_PassField.setFont(font.deriveFont(Font.PLAIN, 14));
		pass_PassField.setEchoChar('\u2981');
		pass_PassField.setBorder(noBorder);
		pass_PassField.setBackground(Color.WHITE);
		pass_PassField.setBounds(40, 3, 210, 35);
		TextPrompt pass_TextPrompt = new TextPrompt("Master Password", pass_PassField);
		pass_TextPrompt.changeAlpha(69);
		pass_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		pass_Panel.add(pass_PassField);
		
		/*
		 * Creates a CheckBox that allows user
		 * to toggle password visibility.
		 */
		showpass_Button = new JCheckBox("");
		showpass_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(showpass_Button.isSelected()) {
					//shows password
					pass_PassField.setEchoChar((char)0);
				}
				else
					//hides password
					pass_PassField.setEchoChar('\u2981');
			}
		});
		showpass_Button.setSelectedIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_iconHide.jpg")));
		showpass_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showpass_Button.setBackground(Color.WHITE);
		showpass_Button.setBorder(null);
		showpass_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_iconShow.jpg")));
		showpass_Button.setBounds(255, 9, 25, 25);
		pass_Panel.add(showpass_Button);
		
		/*
		 * Creates a Label where errors within the 
		 * "Open Vault" tab is displayed.
		 */
		error_Label = new JLabel("");
		error_Label.setHorizontalAlignment(SwingConstants.CENTER);
		error_Label.setBounds(45, 269, 289, 20);
		error_Label.setFont(font.deriveFont(Font.PLAIN, 12));
		error_Label.setForeground(Color.RED);
		openDB_Panel.add(error_Label);
		
		/*
		 * Creates the "Login" button.
		 */
		login_Button = new JButton("");
		login_Button.setPressedIcon(new ImageIcon(LoginGUI.class.getResource("/res/login_iconPressed.jpg")));
		login_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/login_icon.jpg")));
		login_Button.setAlignmentX(Component.CENTER_ALIGNMENT);
		login_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				open_Panel.setBorder(whiteBorder);
				pass_Panel.setBorder(whiteBorder);
				password = String.valueOf(pass_PassField.getPassword());
				
				/*
				 * Decrypts the password vault contents using 
				 * deryptFile() method of Crypto class.
				 * 
				 * @exception NullPointerException if no password vault is selected.
				 * @exception AEADBadTagException if password mismatched.
				 */
				try {
					vaultContents = aes.decryptFile(passwordVault, password);
					
					/*
					 * Initializes PassDbGUI to display the
					 * password vault contents.
					 */
					PasswordDbGUI passTable = new PasswordDbGUI();
					passTable.setVisible(true);
					passTable.setLocationRelativeTo(null);
					
					//Closes the Login frame.
					frmJapmPasswordManager.dispose();
					error_Label.setText(null);
				} catch (NullPointerException npe) {
					open_Panel.setBorder(redBorder);
					error_Label.setText("No Password Database Selected!");
				} catch (AEADBadTagException bte) {
					pass_Panel.setBorder(redBorder);
					error_Label.setText("Incorrect Password!");
				} 
			}
		});
		login_Button.setFocusable(false);
		login_Button.setBounds(140, 320, 100, 30);
		openDB_Panel.add(login_Button);
		
		/*
		 * Creates the "Create Vault" tab.
		 * Allows the user to create a new password vault 
		 * and create the master password.
		 */
		createDB_Panel = new JPanel();
		createDB_Panel.setBorder(null);
		createDB_Panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Create Vault", null, createDB_Panel, null);
		tabbedPane.setBackgroundAt(1, Color.WHITE);
		createDB_Panel.setLayout(null);
		
		create_Panel = new JPanel();
		create_Panel.setBackground(Color.WHITE);
		create_Panel.setBorder(whiteBorder);
		create_Panel.setBounds(50, 100, 290, 40);
		createDB_Panel.add(create_Panel);
		create_Panel.setLayout(null);
		
		create_db_Icon = new JLabel("");
		create_db_Icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/database_icon.jpg")));
		create_db_Icon.setBounds(1, 3, 30, 30);
		create_Panel.add(create_db_Icon);
		
		/*
		 * Creates the TextField for the 
		 * password vault name.
		 */
		create_TextField = new JTextField();
		create_TextField.setText(null);
		create_TextField.setFont(font.deriveFont(Font.PLAIN, 14));
		create_TextField.setBorder(noBorder);
		create_TextField.setBackground(Color.WHITE);
		create_TextField.setBounds(40, 3, 245, 35);
		TextPrompt create_TextPrompt = new TextPrompt("New Password Database", create_TextField);
		create_TextPrompt.changeAlpha(69);
		create_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		create_Panel.add(create_TextField);
		
		create_pass_Panel = new JPanel();
		create_pass_Panel.setBackground(Color.WHITE);
		create_pass_Panel.setBorder(whiteBorder);
		create_pass_Panel.setBounds(50, 200, 290, 40);
		createDB_Panel.add(create_pass_Panel);
		create_pass_Panel.setLayout(null);
		
		create_pass_Icon = new JLabel("");
		create_pass_Icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_icon.jpg")));
		create_pass_Icon.setBounds(1, 3, 30, 30);
		create_pass_Panel.add(create_pass_Icon);
		
		/*
		 * Creates the PasswordField for the 
		 * password vault's master password.
		 */
		create_pass_PassField = new JPasswordField();
		create_pass_PassField.setFont(font.deriveFont(Font.PLAIN, 14));
		create_pass_PassField.setEchoChar('\u2981');
		create_pass_PassField.setBorder(noBorder);
		create_pass_PassField.setBackground(Color.WHITE);
		create_pass_PassField.setBounds(40, 3, 210, 35);
		TextPrompt create_pass_TextPrompt = new TextPrompt("New Master Password", create_pass_PassField);
		create_pass_TextPrompt.changeAlpha(69);
		create_pass_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		create_pass_Panel.add(create_pass_PassField);
		
		/*
		 * Creates a CheckBox that allows user
		 * to toggle password visibility.
		 */
		create_showpass_Button = new JCheckBox("");
		create_showpass_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(create_showpass_Button.isSelected()) {
					create_pass_PassField.setEchoChar((char)0);
				}
				else
					create_pass_PassField.setEchoChar('\u2981');
			}
		});
		create_showpass_Button.setSelectedIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_iconHide.jpg")));
		create_showpass_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		create_showpass_Button.setBackground(Color.WHITE);
		create_showpass_Button.setBorder(null);
		create_showpass_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/pass_iconShow.jpg")));
		create_showpass_Button.setBounds(255, 9, 25, 25);
		create_pass_Panel.add(create_showpass_Button);
		
		/*
		 * Creates a Label that prompts the
		 * user to create a strong password
		 * for the password vault.
		 */
		create_error_Label = new JLabel("<html>Password must be atleast 8 characters and a mix of <br/>\r\n "
				                        + "uppercase and lowercase letters, numbers, and  symbols</html>");
		create_error_Label.setForeground(Color.DARK_GRAY);
		create_error_Label.setHorizontalTextPosition(SwingConstants.LEFT);
		create_error_Label.setBounds(50, 250, 290, 35);
		create_error_Label.setFont(font.deriveFont(Font.PLAIN, 10));
		createDB_Panel.add(create_error_Label);
		
		
		/*
		 * Creates the "Create" button.
		 */
		create_Button = new JButton();
		create_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				create_pass_Panel.setBorder(whiteBorder);
				create_Panel.setBorder(whiteBorder);
				create_error_Label.setForeground(Color.DARK_GRAY);
				FileWriter writer = null;
				
				//A sample password record when a new password vault is created.
				String plainText = "www.foo.com\tuser@foo\tpass@foo";
				
				/*
				 * Creates a file for the password vault.
				 * 
				 *  @throws IOException if file is a directory
				 */
				try {
					String newPassword = String.valueOf(create_pass_PassField.getPassword());
					
					//If users don't input password vault name
					if(create_TextField.getText().length() == 0)
						create_Panel.setBorder(redBorder);
					
					//If the password meets the criteria of a strong password. 
					if(strongPasswordChecker(newPassword)) {
							File file = new File(defaultDir, create_TextField.getText() + ".pldb");
							writer = new FileWriter(file);
							writer.write(aes.encrypt(plainText.getBytes(StandardCharsets.UTF_8), newPassword));
							JOptionPane.showMessageDialog(null, "Password vault created successfully");
						}
					
					else {
						create_pass_Panel.setBorder(redBorder);
						create_error_Label.setForeground(Color.RED);
					}
				 } catch(IOException ioe) {
					 ioe.printStackTrace();
		         } finally {
					try {
						if(writer != null)
							writer.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
		         }
			}
		});
		create_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		create_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/create_icon.jpg")));
		create_Button.setFocusable(false);
		create_Button.setBounds(140, 320, 100, 30);
		createDB_Panel.add(create_Button);
		
		tagline_label = new JLabel("Never forget a password again.");
		tagline_label.setBounds(50, 380, 290, 40);
		tagline_label.setFont(new Font("Corbel Light", tagline_label.getFont().getStyle(), 23));
		frmJapmPasswordManager.getContentPane().add(tagline_label);
		
		JLabel illus_icon = new JLabel("");
		illus_icon.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/login_bg.jpg")));
		illus_icon.setBounds(25, 60, 350, 350);
		frmJapmPasswordManager.getContentPane().add(illus_icon);
	}
	
	/*
	 * Checks if the password meets the 
	 * criteria of a strong password.
	 * 
	 * @param password The password to be checked
	 * @return the result of RegEx pattern matching
	 */
	private boolean strongPasswordChecker(String password) {
		
		 String PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
		 Pattern pattern = Pattern.compile(PASS_PATTERN);
		 Matcher matcher = pattern.matcher(password);
		 return matcher.matches();
		
	}
}