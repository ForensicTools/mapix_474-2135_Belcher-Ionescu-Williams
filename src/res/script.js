$( document ).ready(function() {
	Map.init();
	Map.makeInfo();
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
 * Generate a map Marker.
 *
 * Pretty boring, just for demonstration. Will most likely be removed at release.
 */
Map.makeMarker = function() {
	// setup the Marker
	var marker = new google.maps.Marker({
        position: new google.maps.LatLng(43.084710, -77.679780),
        title: "Hello world"
    });
    
    // create the Marker
    marker.setMap(Map.map);
}

/**
 * Generate an InfoBox, which is like a Marker holding an image thumbnail.
 */
Map.makeInfo = function() {
	// the content that goes in the InfoBox
	var infocontent = '\
    	<div style="width:64px;height:64px;background:#e3e3e3;padding:4px;">\
    		<img src="http://placekitten.com/64/64" />\
    	</div>\
    ';
    
    // setup the InfoBox
    var info = new InfoBox({
    	disableAutoPan: true,
    	hasCloseButton: false,
    	position: new google.maps.LatLng(43.084710, -77.679780),
    	content: infocontent
    });
    
    // create the InfoBox
    info.open(Map.map);
}