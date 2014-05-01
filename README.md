mapix
=====

Visualization tool which scans images for time and GPS metadata. Images are plotted on an interactive map (Google Maps based) as thumbnails above the coordinates they were reportedly created at. Scrubbing the timeline below the map will highlight which photos were taken at the current time. This should help investigators find inconsistencies with image metadata by visually walking through where the sequence of photos was taken.

Usage
-----
Use the "Import" button to bring up a file browser and select the folder containing the photos you wish to map - at this time, you can only select an entire folder (directory) and the folder must only contain image files. 

Once selected, the list under the import button will populate with the imported filenames ordered by timestamp. The names can be clicked to view a scaled version of the photo. Click the name again to exit the scaled photo preview.

Once the photos are imported, the ticks on the timeline slider at the bottom will adjust to represent one tick per photo. Drag the slider in either direction to highlight photos on the map in relation to the date/time they were taken.

Build Environment
-----------------
* Java Version to Build - JDK 1.8.0

External Libraries:
* [Metadata Extractor](https://code.google.com/p/metadata-extractor/)
* [MigLayout](http://www.miglayout.com/)
* [JSON-Simple](http://code.google.com/p/json-simple/)

Authors
-------
* [Vlad Ionescu](github.com/vladionescu)
* [Alex Belcher](github.com/alex-r-belcher)
* Jon Williams
