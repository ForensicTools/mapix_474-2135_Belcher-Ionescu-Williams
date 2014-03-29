/**
 * <Insert Mapix Description Here>
 * @author Alex Belcher
 * @author Insert your name
 * @author ""
 * @version 0.1
 */

package mapix;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSlider;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;



public class MapixInterface extends ComponentAdapter{

	private JFrame frmMapix;
	private JTextField txtPathtophotos;
	private JButton btnInportPhotos;
	private JButton btnMap;
	private JSlider slider;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapixInterface window = new MapixInterface();
					window.frmMapix.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapixInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMapix = new JFrame();
		frmMapix.setTitle("Mapix");
		frmMapix.setBounds(100, 100, 525, 325);
		frmMapix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMapix.getContentPane().setLayout(new MigLayout("", "[401px,grow][:114px:100px,grow]", "[][][][224px,grow][]"));
		frmMapix.addComponentListener(this);
		
		txtPathtophotos = new JTextField();
		txtPathtophotos.setText("Path/to/photos");
		frmMapix.getContentPane().add(txtPathtophotos, "cell 1 1,growx,aligny bottom");
		txtPathtophotos.setColumns(10);
		
		JPanel panel = new JPanel();
		frmMapix.getContentPane().add(panel, "cell 0 0,grow");
		
		btnInportPhotos = new JButton("Import");
		btnInportPhotos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		frmMapix.getContentPane().add(btnInportPhotos, "cell 1 2,growx,aligny top");
		
		list = new JList();
		frmMapix.getContentPane().add(list, "cell 1 3,grow");
		
		slider = new JSlider();
		frmMapix.getContentPane().add(slider, "cell 0 4,growx,aligny center");
		
		//We probably will not have this button
		//btnMap = new JButton("Map!");
		//frmMapix.getContentPane().add(btnMap, "cell 1 5,growx,aligny top");
	}
	
	/**
	 * Imports (recursively) all photos at the specified path
	 * @param path		Full path to folder containing photos
	 * @return void 	May change to array/arraylist of photo objects or potentially stay void and
	 * implement the array of photo objects as a global variable
	 */
	public void importPhotos(String path)
	{
		//This should grab each photo in the folder and:
		//1. Create a photo object, inserting the picture/path into the object
		//2. Call extractMetadata to pull the GPS and Date/Time -OR- call the photo Object's extractMetadata
		//		method. 
		//3. Insert into array(list) utilizing some sorting algorithm to sort by date/time
		
	}
	
	
	/** 
	 * This function pulls in a map covering the area included in the obtained GPS coordinates
	 * return and params may change as needed
	 */
	public void buildMap()
	{
		
	}
	
	/**
	 * This function plots a photo(s) on the map based on where the timeline slider is.
	 * return and params may change as needed 
	 */
	public void plotPhoto()
	{
		
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		Dimension newDim = frmMapix.getSize();
		if(newDim.height < 220)
			newDim.height = 220;
		
		if(newDim.width < 250)
			newDim.width = 250;
		
		frmMapix.setSize(newDim);
		
	}

}
