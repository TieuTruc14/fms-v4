var Position = {
	loaded: false,
	isModified: false,
	positions: [],
	positionPoints: [],
	locationsDialog: null,
	positionDialog: null,
	positionList: null,
	positionsLayer: null,
	circulFeature: null,
	circle: null,
	currentPosition: null,
	
	init: function() {
		this.positionList = new List('locations_list_wrap', {
			valueNames : ['positionId', 'positionName', 'positionAddress', 'positionDescription']
		});
		
		var scrollbar = $("#locations_scrollbar_list"), viewport = scrollbar.find(".viewport");
		$("#locations_list_wrap").on("click", ".positionName", function(event) {
			
			var id = $(this).parent().find("span").text();
			var position = Position.positions[id];
			
			//Create description
			position.content = "<h4>" + position.name + "</h4><br />Địa chỉ: " + position.address + "<br />Mô tả: " + position.description;
			if(Position.isModified){
				position.content += "<br/><a class='updatePos' posId='" + id + "'>Sửa</a> | " + "<a class='deletePos' posId='" + id + "'>Xóa</a>";  
			}
			
			//Show this point on map
			RealtimeTracking.hasFocus = true;
			RealtimeTracking.marker(position);
			Position.triggerModifyPosition();
			event.preventDefault();
		});
						
		this.locationsDialog = $("#locations").wijdialog({
			autoOpen : false,
			minWidth : 370,
			minHeight : 160,
			height : 350,
			position : [ 564, 86 ],
			close : function(e) {
				RealtimeTracking.clearMapLayers();
			},
			captionButtons : {
				refresh : {
					visible : false
				}
			},
			open : function(e) {
				viewport.css("height", (parseInt($(this).css("height")) - 26) + "px");
			},
			resize : function(e, data) {
				viewport.css("height", (parseInt($(this).css("height")) - 26) + "px");
				scrollbar.tinyscrollbar_update();
			},
			zIndex : 9999
		});
		
		this.positionDialog = $("#dialogPosition").wijdialog({
			autoOpen : false,
			minWidth : 500,
			position : [ 564, 86 ],
			close: function(e){
				if(Position.circle != null && map.hasLayer(Position.circle))
					map.removeLayer(Position.circle);
				
				if(marker != null && map.hasLayer(marker))
					map.removeLayer(marker);
			},
			captionButtons : {
				refresh : {
					visible : false
				}
			}
		});
		
		//if(Position.positionList)
		Position.positionList.on("updated", function() {
			scrollbar.tinyscrollbar();
		});
		
	},
	
	open: function() {
    	if(this.loaded == false) {
    		this.loadData();
    	}
    	
    	this.locationsDialog.wijdialog({position: [564, 86]});
    	this.locationsDialog.wijdialog('open');
	},
	
	loadData: function() {
		if(Position.positionList != null)
			Position.positionList.clear();
		var url = "/tracking/getPositions.action";
		return $.getJSON(url, function(data) {
			Position.positionPoints = [];
			if(data) {
				Position.data = data;
				$.each(data, function(key, value) {
					var obj = {positionId: value["id"], positionName : value["name"], positionAddress: value["address"], positionDescription: value["description"]};
					Position.positionList.add(obj);
					Position.positions[value["id"]] = value;
				});
			}
			Position.loaded = true;
		});
	},
	
	close: function(){
		this.locationsDialog.wijdialog('close');
	},
	
	/**
	 * 
	 * @param positionId
	 * @param lonlatContext
	 */
	editPosition: function(positionId, point) {
    	var url = "/manage/savePosition.action";
    	var isNew = true;
		if(positionId != null) {
			url += "?id="+ positionId;
			isNew = false;
			this.dragPosition = Position.positions[positionId];
		} else {
			this.dragPosition = point;
		}
		
		this.loaded = false;
		this.locationsDialog.wijdialog("close");
		$.get(url, function(data) {
			if(data != "OK") {
				$("#rightMenu").css({display: "none"});
				Position.positionDialog.wijdialog("open");
				$("#userpoint_form").html(data);
				$("#userpoint_form").show();
				Position.updateFormEvent();
				/*Position.drawDragFeature();*/
				Position.updatePosition(isNew);
			}
		});
    },
    
    createDragPositon: function(){
    	this.currentPosition = this.dragPosition;
    	if(marker != null && map.hasLayer(marker))
			map.removeLayer(marker);
    	marker = L.marker([this.currentPosition.y, this.currentPosition.x], {icon: markerIcon, draggable: true}).on("dragend", function(e){
    		Position.dragPosition = {
    				x : this._latlng.lng,
    				y : this._latlng.lat,
    				radius : Position.currentPosition.radius,
    		}; 
    		Position.updatePosition(true);
		})
		.addTo(map);
    },
    
    updatePosition: function(isNew) {
		//var pp1 = mercatorToLatLon(dragFeature.clone().geometry);
    	this.createDragPositon();
		if(isNew) {
    		$("#userpoint_form input[name='x']").val(this.dragPosition.x);
    		$("#userpoint_form input[name='y']").val(this.dragPosition.y); 		
			$("#btn_submit").attr("disabled", "disabled");
    		$.get("/manage/reverseGeocode.action?x="+ this.dragPosition.x + "&y=" + this.dragPosition.y, function(data) {
    			$("#userpoint_form input[name='address']").val(data);
    			Position.currentPosition.address = data;
    			$("#btn_submit").removeAttr("disabled");
    		});
		}
		$("#userpoint_form input[name='radius']").val(this.dragPosition.radius);
		this.changeRadius();
	},
    
	changeRadius: function() {
		var radius = parseInt($("#userpoint_form input[name='radius']").val());
		
		if(this.circle != null && map.hasLayer(this.circle))
			map.removeLayer(this.circle);
		var point = {
    			x: this.dragPosition.x,
    			y: this.dragPosition.y,
    			radius: radius,
    	};
		this.dragPosition.radius = radius;
		
		var circleStyle = {
				color: "#0860A8",
                fillColor: "#0860A8",
                fillOpacity: 0.2,
                weight: 1,
                dashArray: "solid",
        };
		this.circle = L.circle([point.y, point.x], radius, circleStyle).addTo(map);
	},
	
	updateFormEvent: function() {
		$("#userpoint_form form").ajaxForm({
			beforeSubmit: function() { 
				if($("#userpoint_form input[name='name']").val() == "") {
					alert("Bạn hãy nhập tên địa điểm!");
					return false;
				}
				if($("#userpoint_form #poiTypeId").val() == -1){
					alert("Bạn vui lòng chọn loại điểm trước!");
					return false;
				}
				Position.currentPosition.name = $("#userpoint_form input[name='name']").val();
				$("#formmessage").html("Loading ...");
			},
	        success: function(data) {
	        	$("#formmessage").html("");
	        	if(data == "OK") {
	        		Position.positionDialog.wijdialog("close");
	        		if(this.circle != null && map.hasLayer(this.circle))
	        			map.removeLayer(Position.circle);
	        		
	        		Position.currentPosition.content = "<h4>" + Position.currentPosition.name + "</h4><br />Địa chỉ: " + Position.currentPosition.address;
	        		RealtimeTracking.marker(Position.currentPosition);
	        		alert("Cập nhật thành công! Click vào mục địa điểm riêng để xem các địa điểm");
	        		if(marker != null && map.hasLayer(marker))
	    				map.removeLayer(marker);
	        	} else {
	        		$("#formmessage").html(data);
	        	}
	        }
		});
		
		$("#btn_cancle").click(function() {
			Position.positionDialog.wijdialog("close");
			if(Position.circle != null && map.hasLayer(Position.circle))
				map.removeLayer(Position.circle);
			
			if(marker != null && map.hasLayer(marker))
				map.removeLayer(marker);
    	});
		$("#txtRadius").change(function() {
    		Position.changeRadius();
    	});
	},
	
	triggerModifyPosition: function(){
		$(".updatePos").click(function(){
			var id = $(this).attr("posId");
			
			Position.editPosition(id, null);
		});
		
		$(".deletePos").click(function(){
			if(confirm("Bạn có chắc chắn xóa địa điểm này?")) {
				var id = $(this).attr("posId");
       			$.get("/manage/deletePosition.action?id="+ id, function(data) {
    				if(data == "OK") {
    					Position.loadData();
    					if(marker != null && map.hasLayer(marker))
    						map.removeLayer(marker);
    				} else {
    					alert(data);
    				}
    			});
    		}
    		return false;
		});
	},
};
