/**
 * Mapix
 * -----
 * Visualization tool to scan image files for time, GPS, camera data and plot on interactive map as thumbnails with timeline scrubbing.
 * 
 * https://github.com/vladionescu/mapix
 * 
 * @author Alex Belcher
 * @author Vlad Ionescu
 * @author Jon Williams
 * @version 0.1
 */

package mapix;

import mapix.Photo;
import net.miginfocom.swing.MigLayout;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.imageio.*;

import java.util.ArrayList; 
import java.util.Collections;



public class MapixInterface extends ComponentAdapter implements ActionListener{

	private JFrame frmMapix; //Main frame
	private JPanel mapPanel = new JPanel(new BorderLayout()); // plain JPanel containing JavaFX Panel
	private JFXPanel jfx = new JFXPanel(); // JavaFX Panel containing map
	private JButton importButton; 
	private JSlider slider;
	private JList<?> list;
	private JFileChooser fc;
	private ArrayList<Photo> photoList = new ArrayList<Photo>();
	private JScrollPane listScroller;
	private Popup popup;
	private boolean popupExists=false;
	private String popupImg = "";
	private int numMappable = 0; //keep track of the number of mappable photos in the list. 
	
	private WebEngine webkit; // WebKit engine, for rendering map

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
		initializeMap();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMapix = new JFrame();
		frmMapix.setTitle("Mapix");
		
        // Set the size at a minimum of 525 x 325
        frmMapix.setPreferredSize(new Dimension(525,325));
        frmMapix.setMinimumSize(frmMapix.getPreferredSize());
        frmMapix.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frmMapix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMapix.getContentPane().setLayout(new MigLayout("", "[401px,grow][:114px:100px,grow]", "[][][][224px,grow][]"));
		frmMapix.addComponentListener(this); //This listens for resize
		listScroller = new JScrollPane();
		
		//Create new File Chooser that allows selection of both files and directories
		//TODO: Implement multiple selection support and file type filtering
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		importButton = new JButton("Import");
		importButton.addActionListener(this);
		frmMapix.getContentPane().add(importButton, "cell 1 1,growx,aligny bottom");
		
		//Will list files
		frmMapix.getContentPane().add(listScroller, "cell 1 3,grow");
		
		//Could probably use setLabelTable to certain ticks (dates)
		//Could also probably reset the number number of ticks/spacing after determining number of files.
		slider = new JSlider();
		slider.setValue(0);
		slider.setMajorTickSpacing(2);
		slider.setToolTipText("");
		slider.setSnapToTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		frmMapix.getContentPane().add(slider, "cell 0 4,growx,aligny center");
		
	}
	
	private void initializeMap() {
		Platform.runLater(new Runnable() {
			public void run() {
				// Attach the JavaFX panel, which contains the WebEngine, to our JPanel
				mapPanel.add(jfx, BorderLayout.CENTER);
				
				// Add the map JPanel to the interface
				frmMapix.add(mapPanel, "cell 0 0,grow,span 1 4");
				
				// Start the WebEngine, vroom vroom
				WebView view = new WebView();
				webkit = view.getEngine();
				
				// Put the WebView inside our JavaFX panel
				jfx.setScene(new Scene(view));
				
				// load static HTML file, initialized with empty map and JS scripts
				try {
					webkit.load(localURL("/res/mapinit.html"));
				} catch (FileNotFoundException e) {
					// if the file doesn't exist, our program can't function.
					// print the error and die - EXIT CODE 1
					System.err.println(e.getMessage());
					System.exit(1);
				}
				
				// WebView#getEngine().executeScript(...);
				// do this ^ to execute JS against the view
			}
		});
	}
	
	/**
	 * Imports (recursively) all photos at the specified path
	 * TODO: Sort Array by time
	 * TODO: Parameter will need to be an array if we wish to implement multiple selection.
	 * @param path		File Object returned by the File Chooser
	 * @return void 	May change to array/arraylist of photo objects or potentially stay void and
	 * implement the array of photo objects as a global variable
	 * @throws IOException 
	 */
	private void importPhotos(File f) 
	{
		if(f.isDirectory())
		{
			//Extract individual files from directory. This might need to be recursive if we wish to support
			//	Nested directories. 
			File[] files = f.listFiles();
			
		    	for(File imageFile : files)
		 	    {
				    try {
				    	// Create new photo object and add to list
						Photo p = new Photo(imageFile.getCanonicalPath(), imageFile.getName());
						
						insert(p);
					} catch (IOException e) {
						System.out.println("General IO Exception: " + e.getMessage());
					} 
			    }
		}
		
		slider.setMaximum(photoList.size());
		
	}
	
	
	/** 
	 * This function pulls in a map covering the area included in the obtained GPS coordinates
	 * return and params may change as needed
	 */
	private void buildMap()
	{
		
	}
	
	/**
	 * This function plots a photo(s) on the map based on where the timeline slider is.
	 * return and params may change as needed 
	 */
	private void plotPhoto()
	{
		
	}
	
	/**
	 * This function builds the list of files imported. It also includes the actionlistener to display
	 * clicked images from the list. 
	 */
	private void buildList(Photo[] photos)
	{
		
		list = new JList<Photo>(photos);
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setCellRenderer(new MyCellRenderer());
		
		//The following code listens for a click on an item in the JList. When clicked, the referenced
		//image is displayed. If the image is already displayed, it is hidden. 
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				
				String path = ((Photo)list.getSelectedValue()).getPath();
				//Is this image already displayed?
				if(popupImg.equals(path) && popupExists)
				{
					popup.hide();
					popupExists = false;
					return;
				}
				//Is another image already displayed?
				if(popupExists) 
					popup.hide();
				
				//set current popup values
				popupExists=true;
				popupImg = ((Photo)list.getSelectedValue()).getPath();
				
				BufferedImage resizeImage = null, img = null;
				
				//try to read the image and create a resized image (Images were displaying much to largely) 
				try {
					img = ImageIO.read(new File(((Photo)list.getSelectedValue()).getPath()));
					int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
					resizeImage = new BufferedImage(500, 500, type);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//Paint the resized image
				Graphics2D g = resizeImage.createGraphics();
				g.drawImage(img, 0, 0, 500, 500, null);
				g.dispose();
				
				//Create a JLabel to display in a popup
				ImageIcon icon = new ImageIcon(resizeImage);
				JLabel jl = new JLabel(icon);
				PopupFactory factory = PopupFactory.getSharedInstance();
				popup = factory.getPopup(null, jl, 0,0);
				popup.show();
			}
		};
		
		list.addMouseListener(mouseListener);
		listScroller.getViewport().setView(list);
	}
    
	/**
	 * This function inserts a new photo object into the Photo arraylist in sorted order based on time
	 * @param p Photo obect to be inserted
	 */
	private void insert(Photo p)
	{
		
		//if a photo is missing date or GPS info, put it at the end of the list
		if(!p.isMappable())
		{
			photoList.add(p);
			return;
		}
		
		numMappable++;
		for(int i = 0; i < photoList.size(); i++)
		{
			if(photoList.get(i).getDate().before(p.getDate()))
				continue;
			photoList.add(i, p);
			return;
		}
		photoList.add(p);
	}
	
	/**
	 * Action performed on import button
	 * @param e Action Event (mouse click)
	 * @return void
	 */
	public void actionPerformed(ActionEvent e) {
		
		//Source of event was the import button
		if(e.getSource() == importButton)
		{
			int returnVal = fc.showOpenDialog(frmMapix); //open File Chooser window
			
			//A file was chosen
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
	            File file = fc.getSelectedFile(); 
				importPhotos(file);
				Photo[] photosArr = new Photo[photoList.size()];
				photosArr = photoList.toArray(photosArr);
				buildList(photosArr);
				slider.setMaximum(numMappable);
				
			}
		}
		
	}
	
	private String localURL(String str) throws FileNotFoundException {
		// find the resource in the local path, delimited by /
		URL local = this.getClass().getResource(str);
		
		// does the file not exist? throw an exception
		if(null == local) {
			throw new FileNotFoundException("The local resource "+str+" was not found.");
		}
		
		// return a valid URL to load by the browser
		return local.toExternalForm();
	}
}