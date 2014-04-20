package mapix;

/*import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;*/

import org.w3c.dom.*;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.*; 
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

public class Photo {
	
	private String path, dateTime, name;
	private double xGPS = 200, yGPS = 200; //initialized to values that are out of range
	private Date date = null;
	private boolean isMappable = true;
	//private int timeValue; //seconds since epoch? Use for sorting
	
	/**
	 * Constructor
	 * @param path String containing path to photo
	 */
	public Photo(String path, String name)
	{
		this.path = path; 
		this.name = name;
		extractMetadata();
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
			String path = getPath();
			
			File jpgInput = new File (path);
			
			//Extract date and time
			Metadata dateAndTime = ImageMetadataReader.readMetadata(jpgInput);
			if(dateAndTime != null)
			{
				ExifSubIFDDirectory dateAndTimeDirectory = dateAndTime.getDirectory(ExifSubIFDDirectory.class);
				//System.out.println(name);
				if(dateAndTimeDirectory != null)
				{
				
					date = dateAndTimeDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
				
					System.out.println("Photo Date and Time: "+ date);
					dateTime=date.toString();	
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
				return;
			}
			GpsDirectory gpsDirectory = geoLocation.getDirectory(GpsDirectory.class);
			if(gpsDirectory == null)
			{
				isMappable = false;
				return;
			}
			GeoLocation coordinates = gpsDirectory.getGeoLocation();
			if(coordinates == null)
			{
				isMappable = false;
				return;
			}
				
			//Getting the Latitude
			System.out.println("GPS lat"+coordinates.getLatitude());
			yGPS = coordinates.getLatitude();
			
			//Getting the Longitude
			xGPS=coordinates.getLongitude();
			
			//System.out.println("GPS Coordinates: "+ coordinates);
		}
		
		catch(Exception a){
			a.printStackTrace();
			
		}
		
	}
}
