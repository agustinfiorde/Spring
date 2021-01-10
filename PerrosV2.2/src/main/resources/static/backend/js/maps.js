      function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 17,
          center: {lat: -32.8813368, lng: -68.8478019}
        });

        var geocoder = new google.maps.Geocoder();

        document.getElementById('geocode').addEventListener('click', function() {
          geocodeAddress(geocoder, map);
        });
        
        geocodeAddress(geocoder, map);
      }

      function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('calle').value + ' ' + document.getElementById('numeracion').value + ',' + document.getElementById('localidad').value + ','+document.getElementById('provincia').value + ',' + document.getElementById('pais').value;
        geocoder.geocode({'address': address}, function(results, status) {
          if (status === 'OK') {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: resultsMap,
              icon: {
            	  url: '../img/map2.png',
                  scaledSize: new google.maps.Size(50,50) 
              },
              position: results[0].geometry.location
            });
          } else {
            //alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      }

      
      