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

import net.miginfocom.swing.MigLayout;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList; 

public class MapixInterface extends ComponentAdapter implements ActionListener{

	private JFrame frmMapix; //Main frame
	private JButton importButton; 
	private JSlider slider;
	private JList<?> list;
	private JFileChooser fc;
	private ArrayList<Photo> photoList = new ArrayList<Photo>();
	private JScrollPane listScroller;

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
		frmMapix.addComponentListener(this); //This listens for resize
		listScroller = new JScrollPane();
		
		//Create new File Chooser that allows selection of both files and directories
		//TODO: Implement multiple selection support and file type filtering
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		importButton = new JButton("Import");
		importButton.addActionListener(this);
		frmMapix.getContentPane().add(importButton, "cell 1 1,growx,aligny bottom");
		
		//Will hold Map
		JPanel panel = new JPanel();
		frmMapix.getContentPane().add(panel, "cell 0 0,grow");
		
		//Will list files
		frmMapix.getContentPane().add(listScroller, "cell 1 3,grow");
		
		//Could probably use setLabelTable to certain ticks (dates)
		//Could also probably reset the number number of ticks/spacing after determining number of files.
		slider = new JSlider();
		slider.setValue(0);
		slider.setMajorTickSpacing(10);
		slider.setToolTipText("");
		slider.setSnapToTicks(true);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		frmMapix.getContentPane().add(slider, "cell 0 4,growx,aligny center");
		
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
						photoList.add(new Photo(imageFile.getCanonicalPath(), imageFile.getName()));
					} catch (IOException e) {
						System.out.println("General IO Exception: " + e.getMessage());
					} 
			    }	
		}
		
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
	 * This function builds the list of files imported. 
	 */
	private void buildList(Photo[] photos)
	{
		
		list = new JList<Photo>(photos);
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setCellRenderer(new MyCellRenderer());
		listScroller.getViewport().setView(list);
		
		
		
	}
	
	/**
	 * This method overrides the componentResized method in the componentAdpter class.
	 * This is being done in order to ensure the window is not resized below a minimum threshold size
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		Dimension newDim = frmMapix.getSize();
		
		if(newDim.height < 220)
			newDim.height = 220;
		
		if(newDim.width < 250)
			newDim.width = 250;
		
		frmMapix.setSize(newDim);
		
	}

	@Override
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
			}
		}
		
	}
}
