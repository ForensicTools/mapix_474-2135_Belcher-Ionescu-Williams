$( document ).ready(function() {
	Map.init();
	
	var photo = new Object();
	photo.id = 'a';
	photo.path = 'http://placekitten.com/64/64';
	photo.lat = 43.084710;
	photo.lng = -77.67978;
	
	Map.makeInfo(photo);
});

/**
 * Orienting my object.
 */
var Map = new Object();

Map.map = null; // Google Maps map object, which can be acted on
Map.dom = null; // handle to the element containing the generated map

/**
 * Generate a map centered on GCCIS at RIT with minimal UI elements.
 */
Map.init = function() {
	var mapOptions = {
		center: new google.maps.LatLng(43.084710, -77.679780),
		zoom: 12,
		disableDefaultUI: true,
		mapTypeControl: true,
		streetViewControl: true
	};
	
	// generate the map and insert it in #map-canvas
	Map.map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	
	// get ahold of the map element
	Map.dom = document.getElementById('map-canvas');
}

/**
 * Generate an InfoBox, which is like a Marker holding an image thumbnail.
 *
 * @param Object	The photo objects should have the following properties: id, path, lat, lng.
 */
Map.makeInfo = function(photo) {
	// check that we are getting all the expected arguments/properties
	if(
		typeof(photo) === undefined ||
		typeof(photo.id) === undefined ||
		typeof(photo.path) === undefined ||
		typeof(photo.lat) === undefined ||
		typeof(photo.lng) === undefined
	)
		return;

	// the content that goes in the InfoBox
	var infocontent = '\
    	<div class="imgContainer" id="'+photo.id+'">\
    		<img src="'+photo.path+'" />\
    	</div>\
    	<div class="pointer"></div>\
    ';
    
    // setup the InfoBox
    var info = new InfoBox({
    	alignBottom: true,
    	disableAutoPan: true,
    	closeBoxURL: '',
    	enableEventPropagation: true,
    	position: new google.maps.LatLng(photo.lat, photo.lng),
    	content: infocontent
    });
    
    // create the InfoBox
    info.open(Map.map);
}