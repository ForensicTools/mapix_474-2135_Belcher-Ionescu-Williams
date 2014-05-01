$( document ).ready(function() {
	Map.init();
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
 * Plot the given photo on the map.
 * Generate an InfoBox, which is like a Marker holding an image thumbnail.
 *
 * @param Object	The photo objects should have the following properties: id, path, lat, lng.
 */
Map.plotPhoto = function(photo) {
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
    		<img src="'+photo.path+'" width="64" height="64" />\
    	</div>\
    	<div class="pointer"></div>\
    ';
    
    // setup the InfoBox
    var info = new InfoBox({
    	alignBottom: true,
    	disableAutoPan: false,
    	closeBoxURL: '',
    	enableEventPropagation: true,
    	position: new google.maps.LatLng(photo.lat, photo.lng),
    	content: infocontent
    });
    
    // create the InfoBox
    info.open(Map.map);
}

/**
 * Highlight the photo whose ID corresponds to the one passed in.
 *
 * @param number	The ID of the photo which will be highlighted
 */
Map.highlightPhoto = function(id) {
	// check that we are getting an ID passed in
	if(typeof(id) === undefined || id.length === 0 || parseInt(id, 10) === NaN) return false;
	
	// make all the photos 50% transparent to de-emphasize any highlighted ones
	$('.imgContainer').css("opacity", "0.5");
	
	// make the current photo opaque, to make it evident
	$('#'+parseInt(id, 10).toString()).css("opacity", "1");
}