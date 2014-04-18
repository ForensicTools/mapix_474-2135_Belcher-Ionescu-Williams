$( document ).ready(function() {
	Map.init();
});

/**
 * Orienting my object.
 */
var Map = new Object();

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
	new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	
	// get ahold of the map element
	Map.dom = document.getElementById('map-canvas');
}