package mapix;

public class Photo {
	
	private String path, dateTime;
	private double xGPS, yGPS;
	private int timeValue; //seconds since epoch? Use for sorting
	
	/**
	 * Constructor
	 * @param path String containing path to photo
	 */
	public Photo(String path)
	{
		this.path = path; 
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
	 * Accessor to return the time value calculated from date/time metadata
	 * @return timeValue
	 */
	public int getTimeValue()
	{
		return timeValue;
	}
	
	/** 
	 * Extracts metadata (GPS and Time/Date) from photo
	 * 
	 */
	private void extractMetadata()
	{
		
	}
}
