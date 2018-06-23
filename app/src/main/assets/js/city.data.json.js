             /**
			 * 获取城市数据
			 * @returns {} 
			 */
			function getCityData(){
			    var localStorageAddrKey = "my_areas";
	            if(localStorage){
		              var my_areas = localStorage.getItem(localStorageAddrKey);
		              if(!!my_areas){
		              	     return JSON.parse(my_areas);
		               }
	             }
	             var city_data = [];
	              window.jQuery.ajaxSetup({async: false});
                  window.jQuery.get("http://139.196.255.175:8088/api/dictionaries/queryAreaList.action", function (res) {
                        if (res.status == 200) {
        	                city_data =initCityData(city_data,res.data,0);
                        }
                  });
                 window.jQuery.ajaxSetup({async: true});
                 if(localStorage){
    	              localStorage.setItem(localStorageAddrKey,JSON.stringify(city_data));
                 }
                 return city_data;
			}
			
			/**
			 * 初始化符合要求的数据
			 * @param {} data
			 * @returns {} 
			 */
			function initCityData(cityDatas,unDoDatas,level){
			     if(!unDoDatas){
			        return cityDatas;
			     }
			     if(!cityDatas){
			        cityDatas = [];
			     }
			     
			     var fCode = "";
			     var fName = "";
			     if(level == 0){
			    	 fCode = 1;
			    	 fName = "全国";
			     }else if(level == 1){
			    	 fCode = 2;
			    	 fName = "全省/市";
			     }else if(level == 2){
			    	 fCode = 3;
			    	 fName = "全市/区";
			     }
			     var cd = {};
			     cd.value = fCode;
			     cd.text = fName;
			     cd.children = [];
			     cityDatas.push(cd);
			     for(var index=0,len=unDoDatas.length;index<len;index++){
			          var unDoData = unDoDatas[index];
			          var cd = {};
			          cd.value = unDoData.id;
			          cd.text = unDoData.name;
			          cd.children = [];
			          cityDatas.push(cd);
			          if(!!unDoData.child){
			             cd.children = initCityData(cityDatas.children,unDoData.child,level+1);
			          }
			     }
			     return cityDatas;
			}