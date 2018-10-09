/*
 * @author:	cuonght at eposi dot vn
 * @version: 1.0.1	2013-07-04
 */

var Map = {
	userDefineLayers : [],
	lastRotate : [],	//Trạng thái quay cuối cùng của xe
	arrows: null,
	showHistory : false,
	dragListenTime: null,
	
	//imageMapping
	icons : {
		'iconOverspeed': {
			icon : L.AwesomeMarkers.icon({
				icon : 'spinner',
				color : 'orange',
				spin : true
			}),
			opacity: 1.0
			},
		'iconOverspeed100': {
			icon : L.AwesomeMarkers.icon({
				icon : 'spinner',
				color : 'red',
				spin : true
			}),
			opacity: 1.0
			},
		'iconRunning': {
			icon : L.AwesomeMarkers.icon({
				icon : 'spinner',
				color : 'green',
				spin : true
			}),
			opacity: 1.0
			},
		'iconStop': {
			icon : L.AwesomeMarkers.icon({
				icon : 'spinner',
				color : 'cadetblue',
				spin : true
			}),
			opacity: 1.0
			},
	},
	
	init : function() {
		this.initMap();		//initiation map
	},

	/**
	 * This function initiation a map
	 * 
	 * @author cuonght at eposi dot vn
	 */
	initMap: function(){
		arrowIcon = L.icon({
	        iconUrl: '/assets/images/icons/tracking-history/track_point.png',
	        iconSize: [16, 16],
	    });
		var lastView = $.cookie("lastview");
		Map.arrows = new L.layerGroup();
    	if( lastView == null)
    		map = L.map('map').setView( [21, 106], 9 );	// HaNoi, VietNam
    		
    	else{
    		var parameters = lastView.split("|");
    		if(parameters.length >2){
    			map = L.map('map').setView( [parameters[0], parameters[1]], parameters[2] );	// last view of user
    		}
    		else
        		map = L.map('map').setView( [21, 106], 9 );	// HaNoi, VietNam
    	}
		
	    marker = new L.layerGroup();						// marker
	    
	    // EPOSI MAP 2
	    var eposiMap = L.tileLayer('http://210.211.102.122:8081/eps-tms-v21/{z}/{x}/{y}.png', {
	    	minZoom: 5,
	    	maxZoom: 17,
	    	attribution: 'Map data &copy; <a href="http://eposi.vn">EPOSI</a> 2013.'
	    }).addTo(map);
	    
	    // Google map
	    var ggMap = L.tileLayer('http://mt1.googleapis.com/vt?lyrs=m@216024762&src=apiv3&hl=vi&x={x}&y={y}&z={z}&s=Gali&style=37%7Csmartmaps,59', {
	    	minZoom: 0,
	    	maxZoom: 20,
	    	attribution: 'Map data &copy; <a href="http://maps.google.com">maps.google.com</a>.'
	    }); //.addTo(map);
	    var ggMapSATELLITE = L.tileLayer('http://khm0.googleapis.com/kh?v=129&hl=vi&x={x}&y={y}&z={z}&token=109441', {
	    	minZoom: 0,
	    	maxZoom: 20,
	    	attribution: 'Map data &copy; <a href="http://maps.google.com">maps.google.com</a>.'
	    });
	    
	    var baseLayers = {
    	    "Eposi Map": eposiMap,
	    	"Google Map": ggMap,
    	    "Google Satellite" : ggMapSATELLITE,
    	};
	    
	    var overLayers = {};
	    if(typeof Map.userDefineLayers != "undefined" && Map.userDefineLayers.length >0 ){
	    	for( var i=0; i< Map.userDefineLayers.length; i++ ){
	    		var userLayer = Map.userDefineLayers[i];
	    		overLayers[userLayer.name] = userLayer.layer;
	    		map.addLayer(userLayer.layer);
	    	}
	    	L.control.layers(baseLayers, overLayers).addTo(map);
	    }
	    else
	    	L.control.layers(baseLayers).addTo(map);
    	
		map.on("load viewreset", function(e){
			Map.showArrow();
			
			//Saved last view of user
	    	var newCenter = map.getCenter();
	    	var lastview = [newCenter.lat, newCenter.lng, map._zoom];
	    	$.cookie("lastview", lastview.join("|"), {path:"/"});
		});
		map.on("dragend", function(e){
			//Saved last view of user
	    	var newCenter = map.getCenter();
	    	var lastview = [newCenter.lat, newCenter.lng, map._zoom];
	    	$.cookie("lastview", lastview.join("|"), {path:"/"});
	    	
			var timeNow = new Date().getTime();
			Map.dragListenTime = timeNow;
			
			setTimeout(function(){
				timeNow = new Date().getTime();
				if(timeNow - Map.dragListenTime < 1000)
					return;
				
				if(Map.showHistory == true)
					Map.showArrow();
				}, 1000);
	    });
	    
		var popup = L.popup();
		//map.on('click', function(e){ popup.setLatLng(e.latlng).setContent(e.latlng.toString()).openOn(map); });
	},//END initMap()
	
	
	showArrow : function(){
		//get bound of map
		var mapBounds = map.getBounds();
		
		//clear old arrow layer in map
		if(map.hasLayer(Map.arrows))
			map.removeLayer(Map.arrows);
		
		//clear old arrow
		Map.arrows.clearLayers();
		
		//caculus new arrow layer
		var mapLayers = map._layers;
		var layer;
		var mapfocus;
		//console.log("show Arrow");
		//console.log(mapLayers);
		for(var layer_id in mapLayers){
			//console.log("layer_id" + layer_id);
			iLayer = mapLayers[layer_id];
			if(iLayer.hasOwnProperty('feature') && iLayer.feature.hasOwnProperty('geometry') && iLayer.feature.geometry.type == "LineString"){
				var sum = 0;
				var startPoint;
				var startVector;
				var endPoint;
				var lstLatlng = iLayer._latlngs;
				startPoint = map.latLngToLayerPoint(lstLatlng[0]);
				//console.log(lstLatlng);
				for(var i = 0; i < lstLatlng.length; i++){
					if(!mapBounds.contains(lstLatlng[i]))
						continue;
					endPoint = map.latLngToLayerPoint(lstLatlng[i]);
					sum += startPoint.distanceTo(endPoint);
					if (sum <= 50){
						startVector = endPoint;
					}
					if (sum >= 120) {
						rotate = Map.getRotate(startPoint, endPoint);
						if(rotate == 0)
							//ham lay rotate
							rotate = Map.getRotate(startVector, endPoint);
							//console.log(rotate);
							degrees = rotate * (180/Math.PI)
							mapfocus = lstLatlng[i];
							var newicon = L.Icon.Label.extend({
				    			options: {
				    				iconUrl: "/assets/images/icons/tracking-history/track_point.png",
				    				iconSize: [16, 16],
				    				iconAnchor: [8, 8],
				    				labelAnchor: new L.Point(0, 0),
				    				wrapperAnchor: new L.Point(0, 0),
				    				rotate: degrees,
				    			}
				    		});
							var marker = new L.marker(lstLatlng[i], {icon: new newicon({ labelText: ""})});
							Map.arrows.addLayer(marker);
						sum = 0;
					}
					startPoint = endPoint;
				}
			}
		}
	    map.addLayer(Map.arrows);
	},
	
	//ham return rotate
	getRotate: function(startPoint, endPoint){
		var degrees = 0;
		if(startPoint.x == endPoint.x || startPoint.y == endPoint.y)
			return 0;
		
		degrees = Math.asin( (endPoint.x - startPoint.x) / startPoint.distanceTo(endPoint) );
		if(endPoint.y > startPoint.y)
			degrees = Math.PI - degrees;
		
		return degrees;
	},//END getRotate()
	
	/**
	 * This function marker a point in map
	 * 
	 * @author cuonght at eposi dot vn
	 */
	marker : function(point, iconType, popUpContent){
		if( typeof popUpContent != "undefined" ){
			marker = L.marker([point.y, point.x], Map.icons[iconType]) .bindPopup(popUpContent);
			return marker;
		}
	},
	
	/**
	 * This function set map focus one point (latlng)
	 * 
	 * @author cuonght at eposi dot vn
	 */
	mapFocus : function(point){
		//check if latlng
		if(typeof point.lat == "undefined" || typeof point.lng == "undefined")
			latlng = new L.LatLng(point.y, point.x);
		else
			latlng = point;
		
		if(map.getBounds().contains(latlng) != true)
			map.setView( latlng, map._zoom );
	},
	
	/**
	 * This function add a layer into map
	 * 
	 * @author cuonght at eposi dot vn
	 */
	addLayerToMap: function(layer) {
		if(typeof layer != "undefined" && !map.hasLayer(layer)){
			map.addLayer(layer);
		}
	},
	
	/**
	 * This function clear a layer on map,
	 * if value of {layer} is not set, clear all old layer on map
	 * 
	 * @author cuonght at eposi dot vn
	 */
	clearMapLayers: function(layer) {
		if(typeof layer == "undefined"){
			TrackingHistory.vehicleId = null;
			
			var mapLayers = map._layers;
			var layer;
			//console.log(mapLayers);
			for(var layer_id in mapLayers){
				//console.log("layer_id" + layer_id);
				layer = mapLayers[layer_id];
				if( layer.hasOwnProperty('_popupContent') || layer.hasOwnProperty('_popup') || (layer.hasOwnProperty('options') && (layer.options.hasOwnProperty('icon') || layer.options.hasOwnProperty('style'))) ) {
					if(map.hasLayer(layer))
						map.removeLayer(layer);
				}
			}
		}else{
			if(map.hasLayer(layer))
				map.removeLayer(layer);
		}
	},
}