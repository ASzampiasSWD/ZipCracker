import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.FileHeader;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ZipCrackerGUI {

	private JFrame frame;
	private JTextField txtPathZip;
	private JTextField txtPathWordlist;
	String strZipPath;
	String strWordlistPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ZipCrackerGUI window = new ZipCrackerGUI();
					window.frame.setVisible(true);
					window.frame.setTitle("Zip Cracker");
					window.frame.setIconImage(ImageIO.read(getClass().getResource("com/image/icon.png")));
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ZipCrackerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 665, 279);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtPathZip = new JTextField();
		txtPathZip.setBounds(122, 43, 491, 20);
		frame.getContentPane().add(txtPathZip);
		txtPathZip.setColumns(10);
		txtPathZip.setEnabled(false);
		
		JButton btnZip = new JButton("Browse");
		btnZip.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			   JFileChooser fileChooser = new JFileChooser();
			    int intReturnValue = fileChooser.showOpenDialog(null);
			        if (intReturnValue == JFileChooser.APPROVE_OPTION)
			        {
			          File strFileZip = fileChooser.getSelectedFile();
			          System.out.println(strFileZip.getName());
			           txtPathZip.setText(strFileZip.getAbsolutePath());
			           strZipPath = strFileZip.getAbsolutePath();
			        }
			}
		});
		btnZip.setBounds(23, 42, 89, 23);
		frame.getContentPane().add(btnZip);
		
		JLabel lblBrowseForZip = new JLabel("Browse for Zip File:");
		lblBrowseForZip.setBounds(23, 11, 144, 14);
		frame.getContentPane().add(lblBrowseForZip);
		
		JLabel lblBrowseForWordlist = new JLabel("Browse for Wordlist:");
		lblBrowseForWordlist.setBounds(23, 89, 144, 14);
		frame.getContentPane().add(lblBrowseForWordlist);
		
		JButton btnWordlist = new JButton("Browse");
		btnWordlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fileChooser = new JFileChooser();
			    int intReturnValue = fileChooser.showOpenDialog(null);
			        if (intReturnValue == JFileChooser.APPROVE_OPTION)
			        {
			          File strFileWordlist = fileChooser.getSelectedFile();
			          System.out.println(strFileWordlist.getName());
			          txtPathWordlist.setText(strFileWordlist.getAbsolutePath());
			          strWordlistPath = strFileWordlist.getAbsolutePath();
			      
			        }	
			}
		});
		btnWordlist.setBounds(23, 120, 89, 23);
		frame.getContentPane().add(btnWordlist);
		
		txtPathWordlist = new JTextField();
		txtPathWordlist.setColumns(10);
		txtPathWordlist.setBounds(122, 121, 491, 20);
		txtPathWordlist.setEnabled(false);
		frame.getContentPane().add(txtPathWordlist);
		
		JButton btnCrack = new JButton("Crack");
		btnCrack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				btnCrack.setBackground(Color.GREEN);
				ZipFile zipFile;
				boolean bKeepGoing = true;
				JFrame frame = new JFrame();
                frame.setBounds(200, 200, 200, 200);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		frame.getContentPane().setLayout(null);
				
				try 
				{
					Scanner scScan = new Scanner(new File(strWordlistPath));
				
				while (scScan.hasNextLine() && bKeepGoing)
				{
					String strPassword = scScan.nextLine();		
				
				try 
				{
					zipFile = new ZipFile(strZipPath);
		     
					if (zipFile.isEncrypted())
					{
					 	zipFile.setPassword(strPassword);	
					}
					
					 List<FileHeader> fileHeaders = zipFile.getFileHeaders();

			            for(FileHeader flHeader : fileHeaders) 
			            {
			                try 
			                {
			                    InputStream is = zipFile.getInputStream(flHeader);
			                    byte[] b = new byte[4 * 4096];
			                    while (is.read(b) != -1) 
			                    {
			                        // Verify password.
			                    }
			                    is.close();
			                    System.out.println("FOUND PASSWORD");
			                    btnCrack.setBackground(Color.GREEN);
			                    frame.setVisible(true);
			            		JLabel lblAnswer = new JLabel("Password is: " + strPassword);
			            		lblAnswer.setBounds(50, 50, 500, 20);
			            		frame.getContentPane().add(lblAnswer);
			            		// extract for future.       		
			                    bKeepGoing = false;
			                } 
			                catch (ZipException qe) 
			                {
			                    if (qe.getCode() == ZipExceptionConstants.WRONG_PASSWORD) 
			                    {
			                    	// Wrong password.
			                       // System.out.println("Wrong password");
			                    }
			                }
			                catch (IOException fe) 
			                {
			                     // Wrong Password
			                	 // System.out.println("WRONG PASSWORD");
			                }
			            }
			            // extract here if you want.
				}
				catch(Exception de)
				{
					
				}
			} 
				
				if (bKeepGoing == true)
				{
				  btnCrack.setBackground(Color.RED);
				  frame.setVisible(true);
          		  JLabel lblAnswer = new JLabel("Password is NOT FOUND");
          		  lblAnswer.setBounds(50, 50, 500, 20);
          		  frame.getContentPane().add(lblAnswer);
				}
				
				
				}
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}		
			}
		});
		btnCrack.setBounds(524, 180, 89, 23);
		frame.getContentPane().add(btnCrack);		
	}
}
