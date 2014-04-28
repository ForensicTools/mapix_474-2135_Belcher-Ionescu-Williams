package mapix;

/*import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;*/



import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import java.io.File;
import java.util.Date;


public class Photo {
	
	private static Integer maxid = 0; // the highest numbered ID of any Photo. Incremented each time a new ID is assigned.
	
	private Integer id; // uniquely identifying number for this Photo
	private String path, dateTime, name;
	private double xGPS = 200, yGPS = 200; //initialized to values that are out of range
	private Date date = null;
	private boolean isMappable = true;
	
	/**
	 * Constructor
	 * @param path String containing path to photo
	 */
	public Photo(String path, String name)
	{
		// assign the ID of this Photo to be 1 greater than the previous highest ID
		this.id = this.maxid + 1;
		// then increment the max ID so the next Photo doesn't conflict
		Photo.maxid += 1;
		
		this.path = path; 
		this.name = name;
		extractMetadata();
	}
	
	/**
	 * Accessor to return the unique ID
	 * @return id
	 */
	public Integer getID() {
		return id;
	}
	
	/**
	 * Accessor to return the path to photo
	 * @return path
	 */
	public String getPath()
	{
		return path;
	}
	
	/**
	 * Accessor to return the Date/Time string
	 * @return dateTime
	 */
	public String getDateTime()
	{
		return dateTime;
	}
	
	/**
	 * Accessor for x-axis gps coordinate
	 * @return xGPS
	 */
	public double getxGPS()
	{
		return xGPS;
	}
	
	/**
	 * Accessor for y-axis gps coordinate
	 * @return yGPS
	 */
	public double getyGPS()
	{
		return yGPS;
	}
	
	
	/**
	 * Accessor to return the filename
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Accessor to return the date object
	 * @return date
	 */
	public Date getDate(){
		
		return date;
	}
	
	/**
	 * Accessor to return whether or not a photo is mapable (contains both data and GPS data)
	 * @return isMappable
	 */
	public boolean isMappable()
	{
		return isMappable;
	}
	
	/** 
	 * Extracts metadata (GPS and Time/Date) from photo
	 * 
	 */
	private void extractMetadata()
	{
		try{
			File jpgInput = new File (path);
			
			//Extract date and time
			Metadata dateAndTime = ImageMetadataReader.readMetadata(jpgInput);
			if(dateAndTime != null)
			{
				ExifSubIFDDirectory dateAndTimeDirectory = dateAndTime.getDirectory(ExifSubIFDDirectory.class);
				if(dateAndTimeDirectory != null)
				{
				
					date = dateAndTimeDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					dateTime=date.toString();
					dateAndTimeDirectory = null; //cleanup
				}
				else 
					isMappable = false;
			}
			else 
				isMappable = false;
			
			//Extract GeoLocation
			Metadata geoLocation = ImageMetadataReader.readMetadata(jpgInput);
			if(geoLocation == null)
			{
				isMappable = false;
				jpgInput = null;
				dateAndTime = null;
				return;
			}
			GpsDirectory gpsDirectory = geoLocation.getDirectory(GpsDirectory.class);
			if(gpsDirectory == null)
			{
				isMappable = false;
				jpgInput = null;
				dateAndTime = null;
				geoLocation = null;
				return;
			}
			GeoLocation coordinates = gpsDirectory.getGeoLocation();
			if(coordinates == null)
			{
				isMappable = false;
				jpgInput = null;
				dateAndTime = null;
				geoLocation = null;
				gpsDirectory = null;
				return;
			}
				
			//Getting the Latitude
			yGPS = coordinates.getLatitude();
			
			//Getting the Longitude
			xGPS=coordinates.getLongitude();
			
			
			//cleanup
			jpgInput = null;
			dateAndTime = null;
			geoLocation = null;
			gpsDirectory = null;
			coordinates = null;
			
		}
		
		catch(Exception a){
			a.printStackTrace();
			
		}
		
	}
	
}
