package gui;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Cursor;

/*
 * A small pop-up frame where
 * the user can input data for 
 * a new password record.
 */
@SuppressWarnings("serial")
public class AddRecord extends JFrame {

	private JPanel contentPane;
	private JTextField url_TextField;
	private JTextField user_TextField;
	private JTextField pass_TextField;
	
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
            java.util.logging.Logger.getLogger(AddRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRecord frame = new AddRecord();
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
	public AddRecord() {
		
		Border whiteBorder = new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY);
		Border redBorder = new MatteBorder(1, 1, 2, 1, (Color) Color.RED);
		Border noBorder = new MatteBorder(0, 0, 0, 0, (Color) Color.WHITE);
		
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
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 300);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * JFrame title.
		 */
		JLabel title_Label = new JLabel("Add Record");
		title_Label.setBounds(5, 0, 180, 24);
		title_Label.setFont(font.deriveFont(Font.PLAIN, 12));
		contentPane.add(title_Label);
		
		/*
		 * Creates a JButton that minimizes the Frame.
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
		min_Button.setBounds(348, 5, 24, 24);
		contentPane.add(min_Button);
		
		/*
		 * Creates a JButton that closes the Frame.
		 */
		JButton close_Button = new JButton("");
		close_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		close_Button.setBackground(Color.WHITE);
		close_Button.setIcon(new ImageIcon(LoginGUI.class.getResource("/res/close_icon.jpg")));
		close_Button.setFocusable(false);
		close_Button.setBounds(378, 5, 24, 24);
		contentPane.add(close_Button);
		
		JPanel url_Panel = new JPanel();
		url_Panel.setLayout(null);
		url_Panel.setBackground(Color.WHITE);
		url_Panel.setBorder(whiteBorder);
		url_Panel.setBounds(55, 50, 289, 40);
		contentPane.add(url_Panel);
		
		JLabel url_Icon = new JLabel("");
		url_Icon.setIcon(new ImageIcon(AddRecord.class.getResource("/res/url_icon.jpg")));
		url_Icon.setBounds(1, 3, 30, 30);
		url_Panel.add(url_Icon);

		/*
		 * Creates a TextField where user
		 * can input the URL/site of the
		 * new password record.
		 */
		url_TextField = new JTextField();
		url_TextField.setFont(font.deriveFont(Font.PLAIN, 14));
		url_TextField.setBorder(noBorder);
		url_TextField.setBounds(40, 3, 245, 35);
		TextPrompt url_TextPrompt = new TextPrompt("Enter URL/Website", url_TextField);
		url_TextPrompt.changeAlpha(69);
		url_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		url_Panel.add(url_TextField);
		
		JPanel user_Panel = new JPanel();
		user_Panel.setLayout(null);
		user_Panel.setBackground(Color.WHITE);
		user_Panel.setBorder(whiteBorder);
		user_Panel.setBounds(55, 110, 289, 40);
		contentPane.add(user_Panel);
		
		JLabel user_Icon = new JLabel("");
		user_Icon.setIcon(new ImageIcon(AddRecord.class.getResource("/res/user_icon.jpg")));
		user_Icon.setBounds(1, 3, 30, 30);
		user_Panel.add(user_Icon);
		
		/*
		 * Creates a TextField where user
		 * can input the username of the
		 * new password record.
		 */
		user_TextField = new JTextField();
		user_TextField.setFont(font.deriveFont(Font.PLAIN, 14));
		user_TextField.setBorder(noBorder);
		user_TextField.setBounds(40, 3, 245, 35);
		TextPrompt user_TextPrompt = new TextPrompt("Enter Username", user_TextField);
		user_TextPrompt.changeAlpha(69);
		user_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		user_Panel.add(user_TextField);
		
		JPanel pass_Panel = new JPanel();
		pass_Panel.setLayout(null);
		pass_Panel.setBackground(Color.WHITE);
		pass_Panel.setBorder(whiteBorder);
		pass_Panel.setBounds(55, 170, 289, 40);
		contentPane.add(pass_Panel);
		
		JLabel pass_Icon = new JLabel("");
		pass_Icon.setIcon(new ImageIcon(AddRecord.class.getResource("/res/pass_icon.jpg")));
		pass_Icon.setBounds(1, 3, 30, 30);
		pass_Panel.add(pass_Icon);
		
		/*
		 * Creates a TextField where user
		 * can input the password of the
		 * new password record.
		 */
		pass_TextField = new JTextField();
		pass_TextField.setFont(font.deriveFont(Font.PLAIN, 14));
		pass_TextField.setBorder(noBorder);
		pass_TextField.setBounds(40, 3, 245, 35);
		TextPrompt pass_TextPrompt = new TextPrompt("Enter Password", pass_TextField);
		pass_TextPrompt.changeAlpha(69);
		pass_TextPrompt.setFont(font.deriveFont(Font.PLAIN, 14));
		pass_Panel.add(pass_TextField);
		
		/*
		 * Creates a "Add" button that 
		 * creates a new row containing
		 * the new password record.
		 */
		JButton addBtn = new JButton("");
		addBtn.setPressedIcon(new ImageIcon(AddRecord.class.getResource("/res/addnew_iconPressed.jpg")));
		addBtn.setFocusable(false);
		addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addBtn.setIcon(new ImageIcon(AddRecord.class.getResource("/res/addnew_icon.jpg")));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				url_Panel.setBorder(whiteBorder);
				pass_Panel.setBorder(whiteBorder);
				user_Panel.setBorder(whiteBorder);
				
				//If URL/Site Text Field is empty or null
				if(url_TextField.getText().isEmpty())
					url_Panel.setBorder(redBorder);
				//If Username Text Field is empty or null
				if(user_TextField.getText().isEmpty())
					user_Panel.setBorder(redBorder);
				//If Password Text Field is empty or null
				if(pass_TextField.getText().isEmpty())
					pass_Panel.setBorder(redBorder);
				//Adds new row containing new password record
				else {
					url_Panel.setBorder(whiteBorder);
					pass_Panel.setBorder(whiteBorder);
					user_Panel.setBorder(whiteBorder);
					PasswordDbGUI.AddRowToJTable(new Object[] {
							url_TextField.getText(), user_TextField.getText(), pass_TextField.getText()
					});
				}
			}
		});
		addBtn.setBounds(160, 240, 75, 30);
		contentPane.add(addBtn);
	}
}