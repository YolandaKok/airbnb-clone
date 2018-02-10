// Instantiate the application and the dependencies 
var app = angular.module('airbnb', ['ui.router', 'ngResource', 'angular-jwt','ngAnimate','ngMaterial', 'ngMessages','ngMap','rzModule','daterangepicker','jkAngularRatingStars', 'ui.bootstrap']);	

////////////////////////////////////////////
//LSH FOR RECOMMENDATION////////////////////
////////////////////////////////////////////
class Map {
/**
 * Construct a new map.
 */
constructor() {
  this.k = [];
  this.v = [];
  this.l = 0;
}

/**
 * @param {{hash: Function, equals: Function}} k
 * @param {*} v
 * @return {*} The old value, if any.
 */
set(k, v) {
  const i = this.index(k);
  const o = this.v[i];

  this.k[i] = k;
  this.v[i] = v;

  return o;
}

/**
 * @param {{hash: Function, equals: Function}} k
 * @param {*} [d]
 * @return {*}
 */
get(k, d) {
  return this.v[this.index(k)] || d;
}

/**
 * @param {{hash: Function, equals: Function}} k
 * @return {boolean}
 */
has(k) {
  return this.get(k) !== undefined;
}

/**
 * @param {{hash: Function, equals: Function}} k
 * @return {number}
 * @private
 */
index(k) {
  let i = k.hash();
  let l;

  while ((l = this.k[i]) !== undefined) {
    if (k.equals(l)) {
      break;
    }

    i++;
  }

  return i;
}
}

class Vector {
/**
 * Construct a vector consisting of binary components where truthy values represent 1 and falsy values represent 0.
 *
 * @param {Array<number>} [cs=[]] The components of the vector.
 *
 * @example
 * const v = Vector([1, 0, 1]);
 */
constructor(cs = []) {
  const l = this.l = cs.length;
  const c = this.c = [];
  const s = this.s = 30;

  let i = 0;

  while (i < l) {
    const n = i + s > l ? l - i : s;

    let e = 0;

    for (let j = 0; j < n; j++) {
      e |= (cs[i + j] ? 1 : 0) << (n - j - 1);
    }

    c[c.length] = e;
    i += n;
  }
}

/**
 * Get the number of components in this vector.
 *
 * @memberof Vector
 * @return {number} The number of components in this vector.
 *
 * @example
 * const v = Vector([1, 0, 0, 1]);
 * v.size();
 * // => 4
 */
size() {
  return this.l;
}

/**
 * Get the component at the specified index of this vector.
 *
 * @param {number} i The index of the component to get.
 * @return {number} The component at the index if found, otherwise `undefined`.
 *
 * @example
 * const v = Vector([1, 0, 1, 1]);
 * v.get(0);
 * // => 1
 */
get(i) {
  const l = this.l;
  const c = this.c;
  const s = this.s;

  if (i < 0 || i >= l) {
    return;
  }

  const d = i / s | 0;
  const j = d * s;
  const p = j + s > l ? l - j : s;

  return (c[d] >> (p - (i % s) - 1)) & 1;
}

/**
 * Compupte the hash of this vector.
 *
 * @return {number} The hash of this vector.
 */
hash() {
  const c = this.c;

  let b = 7;

  for (let i = 0, n = c.length; i < n; i++) {
    b ^= (31 * b) + c[i] + (b << 6) + (b >> 2);
  }

  return b ^ (b >> 32);
}

/**
 * Check if this vector is equal to another.
 *
 * @param {Vector} v The vector to check against.
 * @return {boolean} `true` if the vectors are equal, otherwise `false`.
 */
equals(v) {
  if (this.l !== v.l) {
    return false;
  }

  const c = this.c;

  for (let i = 0, n = c.length; i < n; i++) {
    if (c[i] !== v.c[i]) {
      return false;
    }
  }

  return true;
}

/**
 * Return a string representation of this vector.
 *
 * @return {string} The string representation of the vector.
 */
toString() {
  let s = 'Vector[';

  for (let i = 0, n = this.l; i < n; i++) {
    s += this.get(i);
  }

  return s + ']';
}

/**
 * Compute the distance between two vectors.
 *
 * @see http://graphics.stanford.edu/~seander/bithacks.html#CountBitsSetKernighan
 *
 * @param {Vector} u
 * @param {Vector} v
 * @return {number}
 */
static distance(u, v) {
  let d = 0;

  for (let i = 0, n = u.c.length; i < n; i++) {
    let x = u.c[i] ^ v.c[i];

    for (d; x; d++) {
      x &= x - 1;
    }
  }

  return d;
}

/**
 * Construct a random vector of a given length.
 *
 * @param {number} l
 * @return {Vector}
 */
static random(l) {
  const c = new Array(l);

  for (let i = 0; i < l; i++) {
    c[i] = (Math.random() + 0.5) | 0;
  }

  return new Vector(c);
}
}


class Mask {
/**
 * Construct a new mask.
 *
 * @param {number} d The dimensionality of vectors to mask.
 * @param {number} k The number of dimensions in vector projections.
 */
constructor(d, k) {
  const m = this.m = new Array(k);

  for (let i = 0; i < k; i++) {
    m[i] = Math.random() * d | 0;
  }
}

/**
 * Project a vector, reducing it to a dimensionality specified by this mask.
 *
 * @param {Vector} v The vector to project.
 * @return {Vector} The projected vector.
 */
project(v) {
  const m = this.m;
  const n = m.length;
  const c = new Array(n);

  for (let i = 0; i < n; i++) {
    c[i] = v.get(m[i]);
  }

  return new Vector(c);
}
}

class Table {
/**
 * Construct a lookup table for vectors of dimensionality `d` where vectors are hashed using `k`-width hash values
 * (random vector projections) into `l` sets of hashes.
 *
 * @param {number} d The number of dimensions of vectors in the table.
 * @param {number} k The width of each vector hash.
 * @param {number} l The number of hash sets to use.
 *
 * @example
 * const d = 4;
 * const k = 2;
 * const l = 2;
 * const t = Table(d, k, l);
 */
constructor(d, k, l) {
  this.d = d;
  this.l = 0;

  if (k > d || l > d) {
    throw new Error('Amplification parameters cannot be greater than d');
  }

  const m = this.m = new Array(l);
  const t = this.t = new Array(l);

  for (let i = 0; i < l; i++) {
    m[i] = new Mask(k);
    t[i] = new Map();
  }
}

/**
 * Get the number of vectors in the lookup table.
 *
 * @memberof Table
 * @return {number} The number of vectors in the lookup table.
 *
 * @example
 * t.add(Vector(...));
 * t.size();
 * // => 1
 */
size() {
  return this.l;
}

/**
 * Add a vector `v` to the lookup table.
 *
 * @memberof Table
 * @param {Vector} v The vector to add to the lookup table.
 *
 * @example
 * const v = Vector([1, 0, 1, 0]);
 * t.add(v);
 */
add(v) {
  const d = this.d;
  const t = this.t;
  const m = this.m;

  if (v.size() !== d) {
     console.log(v.size());
    throw new Error('Incorrect vector dimensionality');
  }

  this.l++;

  for (let i = 0, n = t.length; i < n; i++) {
    const k = m[i].project(v);
    const b = t[i].get(k, []);

    b[b.length] = v;

    t[i].set(k, b);
  }
}

/**
 * Query the lookup table for the nearest neighbour of a query vector `q`.
 *
 * @memberof Table
 * @param {Vector} q The query vector to look up the nearest neighbour of.
 * @return {Vector} The nearest neighbouring vector if found.
 *
 * @example
 * const q = Vector([0, 1, 0, 1]);
 * t.query(q);
 * // => Vector(...)
 */
query(q) {
  const d = this.d;
  const t = this.t;
  const m = this.m;

  if (q.size() !== d) {
    throw new Error('Incorrect vector dimensionality');
  }

  let bc = null;
  let bd = Infinity;

  for (let i = 0, n = t.length; i < n; i++) {
    const k = m[i].project(q);
    const b = t[i].get(k, []);

    for (let j = 0, m = b.length; j < m; j++) {
      const c = b[j];
      const d = Vector.distance(q, c);

      if (d < bd) {
        bc = c;
        bd = d;
      }
    }
  }

  return bc;
}

/**
 * Check if the lookup table contains a specific vector.
 *
 * @param {Vector} v The vector to check for.
 * @return {boolean} `true` if the table contains the vector, otherwise `false`.
 */
contains(v) {
  const d = this.d;
  const t = this.t;
  const m = this.m;

  if (v.size() !== d) {
    throw new Error('Incorrect vector dimensionality');
  }

  for (let i = 0, n = t.length; i < n; i++) {
    const k = m[i].project(v);
    const b = t[i].get(k, []);

    for (let j = 0, m = b.length; j < m; j++) {
      if (v.equals(b[j])) {
        return true;
      }
    }
  }

  return false;
}
}
////////////////////////////////////////////
//END LSH FOR RECOMMENDATION////////////////////
////////////////////////////////////////////






//angular.bootstrap(document, ['airbnb']);
// Set the routes that are accessible through the application
app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	// If anything other matches the URL redirect to root
	//$urlRouterProvider.otherwise('/');
	// Create the states for the application
	// first parameter is the state which is the same in the html
	// second parameter is an object which defines its behavior
	$stateProvider
		.state('home', {
			cache: false,
			url: '/',
			views: {
				'recommend': {
					templateUrl: 'recommendations.html',
					controller: 'recommendCtrl'
				},
				'welcomePanel': {
					templateUrl: 'welcomePanel.html',
					controller: 'welcomeCtrl'
				}
			}
		})
		.state('signup', {
			url: '/signup',
			templateUrl: 'signup.html',
			controller: 'signupCtrl'	
		})
		.state('signin', {
			url: '/signin',
			templateUrl: 'signin.html'
		})
		.state('admin', {
			url: '/admin',
			templateUrl: 'admin.html'
		})
		.state('profile', {
			url: '/profile/{userID}',
			templateUrl: 'profile.html',
			controller: 'profileCtrl'	
		})
		.state('edit', {
			url: '/profile/edit/{userID}',
			templateUrl: 'edit.html'
		})
		.state('export',{		
			url: '/export',		
			templateUrl: 'export.html'		
		})
		.state('manageHouses', {
			url:'/manage/houses',
			templateUrl: 'manageHouses.html',
			controller: 'manageCtrl'
		})
		.state('new', {
			url:'/new',
			templateUrl: 'host.html',
			controller: 'houseCtrl'
		})
		        // url will be nested (/form/profile)
        .state('new.intro', {
            url: '/intro',
            templateUrl: 'form-intro.html'
        })
        
        // url will be /form/interests
        .state('new.location', {
            url: '/location',
            templateUrl: 'form-location.html'
        })
        
        // url will be /form/payment
        .state('new.facilities', {
            url: '/facilities',
            templateUrl: 'form-facilities.html'
        })		
        .state('new.price', {		
        	url: '/price',		
        	templateUrl: 'form-price.html'		
        })		
        .state('room', {		
        	url: '/rooms/{houseID}',		
        	templateUrl: 'room.html',		
        	controller: 'roomCtrl'
        })
        .state('inbox',{
			url: '/inbox',
			templateUrl: 'inbox.html',
				
		})
		.state('updateHouse', {
			url: '/updateHouse/{houseid}',
			templateUrl: 'updateHouse.html',
			controller: 'updateHouseCtrl'
		})
        .state('results', {
        	url: '/results?city&adults',
        	templateUrl: 'results.html',
        	controller: 'resultsCtrl'
        });
}]);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Google Maps Service

app.service('Map', function($q) {
    
    this.init = function() {
        var options = {
            center: new google.maps.LatLng(40.7127837, -74.00594130000002),
            zoom: 13,
            disableDefaultUI: true    
        }
        this.map = new google.maps.Map(
            document.getElementById("map"), options
        );
        this.places = new google.maps.places.PlacesService(this.map);
    }
    
    this.search = function(str) {
        var d = $q.defer();
        this.places.textSearch({query: str}, function(results, status) {
            if (status == 'OK') {
                d.resolve(results[0]);
            }
            else d.reject(status);
        });
        return d.promise;
    }
    
    this.addMarker = function(res) {
        if(this.marker) this.marker.setMap(null);
        this.marker = new google.maps.Marker({
            map: this.map,
            position: res.geometry.location,
            animation: google.maps.Animation.DROP
        });
        this.map.setCenter(res.geometry.location);
    }
    
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Map Service Reverse Geolocation

app.service('MapRev', function($q) {
    
    this.init = function(latitude, longitude) {
        var options = {
            center: new google.maps.LatLng(latitude, longitude),
            zoom: 13,
            disableDefaultUI: true,
            fullscreenControl: true,
            zoomControl: true
        }
        
        this.map = new google.maps.Map(
            document.getElementById("map"), options
        );
        
        this.places = new google.maps.places.PlacesService(this.map);
        // Add a circle to see the house's region
    }
    
    this.addCircle = function(latitude, longitude) {
        
        var center = new google.maps.LatLng(latitude, longitude);
        
        var cityCircle = new google.maps.Circle({
            strokeColor: '#0000FF',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#0000FF',
            fillOpacity: 0.35,
            map: this.map,
            center: center,
            radius: 1000
          });
    }
    
    this.transitLayer = function() {
    	  var transitLayer = new google.maps.TransitLayer();
    	  transitLayer.setMap(this.map);
    }
    
});

app.directive("ngFileSelect", function(fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result) {
              $timeout(function() {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function(e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  });

app.factory("fileReader", function($q, $log) {
  var onLoad = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.resolve(reader.result);
      });
    };
  };

  var onError = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.reject(reader.result);
      });
    };
  };

  var onProgress = function(reader, scope) {
    return function(event) {
      scope.$broadcast("fileProgress", {
        total: event.total,
        loaded: event.loaded
      });
    };
  };
  
  var getReader = function(deferred, scope) {
    var reader = new FileReader();
    reader.onload = onLoad(reader, deferred, scope);
    reader.onerror = onError(reader, deferred, scope);
    reader.onprogress = onProgress(reader, scope);
    return reader;
  };

  var readAsDataURL = function(file, scope) {
    var deferred = $q.defer();

    var reader = getReader(deferred, scope);
    reader.readAsDataURL(file);

    return deferred.promise;
  };

  return {
    readAsDataUrl: readAsDataURL
  };
});



//Make a call to the restful services for users

app.factory('UserEnd', ['$resource', '$window',
	  function($resource, window){
		return function(customHeaders){
		    return $resource('http://localhost:8080/airbnb/services/users', {}, {
		          save: { 
			          method: 'POST',
			          params: {},
			          isArray: false,
			          headers: customHeaders || {}
		          },
		          query: {
		        	  method: 'GET',
			          isArray: true
		          }
		      });
		    };
	  }]);

app.factory('MessageEnd', ['$resource', '$window',		
	  function($resource, window){		
		return function(customHeaders){		
		    return $resource('http://localhost:8080/airbnb/services/messages', {}, {		
		          save: { 		
			          method: 'POST',		
			          params: {},		
			          isArray: false,		
			          headers: customHeaders || {}		
		          },		
		          query: {		
		        	  method: 'GET',		
			          isArray: true		
		          }		
		      });		
		    };		
	  }]);

// Make a call to the restful services for houses



app.factory('HouseEnd', ['$resource', '$window',
	  function($resource, window){
		return function(customHeaders){
		    return $resource('http://localhost:8080/airbnb/services/houses', {}, {
		          save: { 
			          method: 'POST',
			          params: {},
			          isArray: false,
			          headers: customHeaders || {}
		          },
		          query: {
		        	  method: 'GET',
			          isArray: true
		          }
		      });
		    };
	  }]);



app.factory('ReviewEnd', ['$resource', '$window',		
	  function($resource, window){		
		return function(customHeaders){		
		    return $resource('http://localhost:8080/airbnb/services/reviews/', {}, {		
		          save: { 		
			          method: 'POST',		
			          params: {},		
			          isArray: false,		
			          headers: customHeaders || {}		
		          },		
		          query: {		
		        	  method: 'GET',		
			          isArray: true		
		          }		
		      });		
		    };		
	  }]);

app.factory('HouseExp', ['$resource', function($resource) {		
	return $resource('http://localhost:8080/airbnb/services/houses/export', null,		
	    {		
	        'query': { method:'GET' }		
	    });		
	}]);

app.factory('ReviewExp', ['$resource', function($resource) {		
	return $resource('http://localhost:8080/airbnb/services/reviews/export', null,		
	    {		
	        'query': { method:'GET'}		
	    });		
	}]);


// Factory to update user Data

app.factory('UserUpdate', ['$resource', function($resource) {
	return $resource('http://localhost:8080/airbnb/services/users/:id', null,
	    {
	        'update': { method:'PUT' }
	    });
	}]);

// Factory to update house Data
app.factory('HouseUpdate', ['$resource', function($resource) {
	return $resource('http://localhost:8080/airbnb/services/houses/update/:id', null,
	    {
	        'update': { method:'PUT' }
	    });
	}]);

// Factory to send startDate and endDate

app.factory('CalendarInfo', ['$resource', function($resource) {
	return $resource('http://localhost:8080/airbnb/services/calendar/:startDate/:endDate', null,
	    {
	        'query': { method:'GET' }
	    });
	}]);


app.factory('ResExp', ['$resource', function($resource) {		
	return $resource('http://localhost:8080/airbnb/services/calendar/export', null,		
	    {		
	        'query': { method:'GET' }		
	    });		
	}]);



// Factory to get the Houses of a Host

/*app.factory('HostHouses', ['$resource', function($resource) {
	return $resource('http://localhost:8080/airbnb/services/houses/host/:hostid', null,
	    {
	        'query': { method:'GET' }
	    });
	}]);*/



app.factory('HostHouses', ['$resource', '$window',
	  function($resource, window){
		return function(customHeaders){
		    return $resource('http://localhost:8080/airbnb/services/houses/host/:hostid', {hostid:'@host'}, {
		          query: {
		        	  method: 'GET',
			          isArray: true
		          }
		      });
		    };
	  }]);





// Factory to Update calendar info

app.factory('CalendarUpdate', ['$resource', function($resource) {
	return $resource('http://localhost:8080/airbnb/services/calendar/:startDate/:endDate/:houseid/:guest', null,
	    {
	        'update': { method:'PUT' }
	    });
	}]);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Definition of the controllers


app.controller('welcomeCtrl', ['$scope', '$stateParams', '$resource', 'HouseUpdate', '$state', function ($scope, $stateParams, $resource, HouseUpdate, $state) {
	
}]);



// Sign Up Controller

app.controller('updateHouseCtrl', ['$scope', '$stateParams', '$resource', 'HouseUpdate', '$state', function ($scope, $stateParams, $resource, HouseUpdate, $state) {
	
	var id = $stateParams.houseid;
	
	$scope.newHouse = new Object();
	
	var House = $resource('http://localhost:8080/airbnb/services/houses/:houseId', {houseId:'@id'});
	House.get( {houseId:id}, function(house){
		$scope.name = house.name;
		$scope.bathrooms = house.bathrooms;
		$scope.bedrooms = house.bedrooms;
		$scope.beds = house.beds;
		$scope.accommodates = house.accommodates;
		$scope.summary = house.summary;
		$scope.city = house.city;
		$scope.country = house.country;
		$scope.propertyType = house.propertyType;
		$scope.bedType = house.bedType;
		$scope.price = house.price;
		$scope.weeklyPrice = house.weeklyPrice;
		$scope.monthlyPrice = house.monthlyPrice;
		$scope.extraPeople = house.extraPeople;
		$scope.cleaningFee = house.cleaningFee;
		$scope.roomType = house.roomType;
		$scope.pictureURL = house.pictureURL;
		$scope.cleaningFee = house.cleaningFee;
		$scope.wifi = house.wifi;
		$scope.tv = house.tv;
		$scope.parking = house.parking;
		$scope.kitchen = house.kitchen;
		$scope.elevator = house.elevator;
		$scope.heating = house.heating;
		$scope.ac = house.ac;
		$scope.squareFeet = house.squareFeet;
		
		// Pass the current data to the new object
		$scope.newHouse.name = house.name;
		$scope.newHouse.bathrooms = house.bathrooms;
		$scope.newHouse.bedrooms = house.bedrooms;
		$scope.newHouse.beds = house.beds;
		$scope.newHouse.accommodates = house.accommodates;
		$scope.newHouse.summary = house.summary;
		$scope.newHouse.city = house.city;
		$scope.newHouse.country = house.country;
		$scope.newHouse.propertyType = house.propertyType;
		$scope.newHouse.bedType = house.bedType;
		$scope.newHouse.price = house.price;
		$scope.newHouse.weeklyPrice = house.weeklyPrice;
		$scope.newHouse.monthlyPrice = house.monthlyPrice;
		$scope.newHouse.extraPeople = house.extraPeople;
		$scope.newHouse.cleaningFee = house.cleaningFee;
		$scope.newHouse.roomType = house.roomType;
		$scope.newHouse.pictureURL = house.pictureURL;
		$scope.newHouse.cleaningFee = house.cleaningFee;
		$scope.newHouse.wifi = house.wifi;
		$scope.newHouse.tv = house.tv;
		$scope.newHouse.parking = house.parking;
		$scope.newHouse.kitchen = house.kitchen;
		$scope.newHouse.elevator = house.elevator;
		$scope.newHouse.heating = house.heating;
		$scope.newHouse.ac = house.ac;
		$scope.newHouse.squareFeet = house.squareFeet;
	});
	
	// Now put the new information to the object
	
	$scope.newHouse.name = $scope.name;
	$scope.newHouse.bathrooms = $scope.bathrooms;
	$scope.newHouse.bedrooms = $scope.bedrooms;
	$scope.newHouse.beds = $scope.beds;
	$scope.newHouse.accommodates = $scope.accommodates;
	$scope.newHouse.summary = $scope.summary;
	$scope.newHouse.city = $scope.city;
	$scope.newHouse.country = $scope.country;
	$scope.newHouse.propertyType = $scope.propertyType;
	$scope.newHouse.bedType = $scope.bedType;
	$scope.newHouse.price = $scope.price;
	$scope.newHouse.weeklyPrice = $scope.weeklyPrice;
	$scope.newHouse.monthlyPrice = $scope.monthlyPrice;
	$scope.newHouse.extraPeople = $scope.extraPeople;
	$scope.newHouse.cleaningFee = $scope.cleaningFee;
	$scope.newHouse.roomType = $scope.roomType;
	$scope.newHouse.pictureURL = $scope.pictureURL;
	$scope.newHouse.cleaningFee = $scope.cleaningFee;
	$scope.newHouse.wifi = $scope.wifi;
	$scope.newHouse.tv = $scope.tv;
	$scope.newHouse.parking = $scope.parking;
	$scope.newHouse.kitchen = $scope.kitchen;
	$scope.newHouse.elevator = $scope.elevator;
	$scope.newHouse.heating = $scope.heating;
	$scope.newHouse.ac = $scope.ac;
	$scope.newHouse.squareFeet = $scope.squareFeet;
	
	// Submit updated data
	
	$scope.updateHouse = function() {
		HouseUpdate.update({ id:id }, $scope.newHouse);
		$state.go('manageHouses');

	}
	
	
	
}]);


app.controller('signupCtrl', ['$scope','UserEnd','$window','$state', function ($scope,UserEnd,window,$state) {
	$scope.dis = true;
	
	var data = UserEnd().query();
	
	// UserEnd array of json objects
	$scope.checkName = function (key) {
		

		data.$promise.then(function () { 
			// Returns data in json format	
		    var i, len = data.length;
			$scope.isSameName=false;
		    for (i = 0; i < len; i++) {
		    		//console.log(key);
		    		if (data[i].username == key) {
		    			$scope.isSameName=true;
		    			return;
		    		}
			    }
			 return;
		});
	};
	$scope.checkEmail = function (key) {
		
		data.$promise.then(function () { 
			// Returns data in json format	
		    var i, len = data.length;
			$scope.isSameEmail=false;
		    for (i = 0; i < len; i++) {
		    		//console.log(key);
		    		if (data[i].email == key) {
		    			$scope.isSameEmail=true;
		    			return;
		    		}
			    }
			 return;
		});
	};
	
	$scope.newuser = new Object();
	$scope.newuser.birthday = new Date();	
	$scope.insertUser = function(isValid) {
		// insert user in json format to the RESTful API
        //var saveresult = UserEnd({Authorization: 'Bearer ' + window.sessionStorage.token}).save($scope.newuser);
        var saveresult = UserEnd().save($scope.newuser);
        // $promise executes when there are results: equals to if
		saveresult.$promise.then(function () { 
		// Returns data in json format	
        $scope.users = UserEnd().query();
        $state.go('home');
		});

    }
}]);

// Authentication Controller

app.controller('AuthCtrl', ['$scope', '$window', '$state', 'UserEnd', '$http', 'jwtHelper','$resource', '$filter', 'NgMap','HouseEnd', '$rootScope', function($scope, window, $state, UserEnd, $http, jwtHelper,$resource, $filter, NgMap, HouseEnd, $rootScope) {
	
	
	//$scope.host = sessionStorage.getItem('host');
	
	if(sessionStorage.getItem('host') === 'true')
		
	{
		$scope.host = true;
	}
	else if(sessionStorage.getItem('host') === 'false')
	{
		$scope.host = false;
	}
	
	
	//destroy rootScope
	$rootScope.$resetScope = function() {
		for (var prop in $rootScope) {
		    if (prop.substring(0,1) !== '$') {
		        delete $rootScope[prop];
		    }
		}
	}
	
	  // Search Autocomplete //////////////////////////////////////////////////
	  $scope.vm = this;
	  $scope.vm.placeChanged = function() {
		$scope.vm.place = this.getPlace();
		for(var i = 0; i < $scope.vm.place.address_components.length; i++ ) {
			if ($scope.vm.place.address_components[i].types["0"] === 'locality') {
				console.log($scope.vm.place.address_components[i].types["0"]);
				$scope.city = $scope.vm.place.address_components[i].long_name;
				console.log($scope.city);
			}
		}
		
	  }
	  ////////////////////////////////////////////////////////////////////////

	  
	  // Search Houses
	  
	  $scope.Search = function () {
		  $state.go('results', {city:$scope.city, adults:$scope.adults});
	  }
	  
	
	  $scope.date = {
	            startDate: moment().subtract(1, "days"),
	            endDate: moment()
	  }; 
	
	$scope.name='undefined';
    $scope.password="";
    function urlBase64Decode(str) {
        var output = str.replace('-', '+').replace('_', '/');
        switch (output.length % 4) {
            case 0:
                break;
            case 2:
                output += '==';
                break;
            case 3:
                output += '=';
                break;
            default:
                throw 'Illegal base64url string!';
        }
        return window.atob(output);
    }

    function getUserFromToken() {
        var token = window.sessionStorage.token;
        var user = {};
        if (typeof token !== 'undefined') {
            var encoded = token.split('.')[1];
            user = JSON.parse(urlBase64Decode(encoded));
        }
        //console.log(user);
        return user;
    }
        
    
	$scope.isConnected = function () {
			if(typeof window.sessionStorage.token !== 'undefined')
			{
				var isExpired = jwtHelper.isTokenExpired(window.sessionStorage.token);
				if(isExpired)
				{
					//console.log("end");
					$scope.logout();
					//$state.go('signin');
					window.location.replace("http://localhost:8080/airbnb/#!/signin");
				}
			}
			var user = getUserFromToken();
			$scope.name = user.sub;
	}
	
	// sign in
	
	$scope.users = UserEnd().query();
	
	$scope.users.$promise.then(function () { 
		var result = $filter('filter')($scope.users, {username:$scope.name})[0];
		$scope.ID = result.userID;
	});
	
	$scope.newuser = new Object();
	$scope.logininfo = new Object();
	
	$scope.login = function() {
		$http
        .post('http://localhost:8080/airbnb/services/users/login', $scope.logininfo)
        .then(function(response){
        	var data = response.data;
            var status = response.status;
            var statusText = response.statusText;
            var headers = response.headers;
            var config = response.config;
        	window.sessionStorage.token = data;
        	$scope.authentication = false;  //data is the data returned from server
    		$scope.users = UserEnd().query();
    		
    		$scope.users.$promise.then(function () { 
    			
    			var result = [];
    			result = $filter('filter')($scope.users, {username:$scope.name});  //find user by his username
    		    
    			for(var i = 0; i < result.length; i++) {
    				console.log(result[i].username);
    				if(result[i].username === $scope.name) 
    				{
    	    			$rootScope.connected_user = result;
    	    			$scope.ID = result[i].userID;
    	    			$scope.password = result[i].password;
    	    			$scope.host = result[i].host;
    	    			// Save in sessionStorage the host value
    	    			sessionStorage.setItem('host', result[i].host);
    				}
    			}
    			
    			
    			
    			if($scope.name=='admin')
    			{
					window.location.replace("http://localhost:8080/airbnb/#!/admin");  //go to admin's pa
    			}
    		});
        	$state.go('home');        	
        }, function() {
        	$scope.authentication=true;
    		return;
		});
	};

	// Logout
	
	$scope.logout = function() {
		window.sessionStorage.clear();
		$rootScope.$resetScope();
		$scope.ID = undefined;
		$state.go('home');
	};
	
	$scope.showHouseForm = function(key) {
		// We have a list of results and we can search into them to find the profile that we want
		// console.log(userID);
		// make a call to the restful API
		var users = UserEnd().query();
		users.$promise.then(function () { 
			var result = $filter('filter')(users, {username:key})[0];
			var userId = result.userID;
			
			var User = $resource('http://localhost:8080/airbnb/services/users/:userId', {userId:'@userId'});
			User.get( {userId:userId}, function(user){
			$scope.profile = user;
			window.location.href = 'http://localhost:8080/airbnb/#!/new_house/' + userId;
		})	
			
		});
	
	};
	
	$scope.goToExport = function() {			
		window.location.replace("http://localhost:8080/airbnb/#!/admin/export");  //go to admin's pa
	};	
	
	
	///////////////////////////// DATERANGEPICKER //////////////////////////////////
  		    
	/////////////////////////////////////////////////////////////////////////////////////
	
	
	
}]);




//Results Controller

app.controller('resultsCtrl', ['$scope', 'HouseEnd', '$stateParams', '$filter' , 'CalendarInfo', '$resource', function ($scope, HouseEnd, $stateParams, $filter, CalendarInfo, $resource) {
    
			$scope.slider = {
			  min: 0,
			  max: 400,
			  options: {
			    floor: 0,
			    ceil: 450
			  }
			};
			$scope.byRange = function (fieldName, minValue, maxValue) {
				  if (minValue === undefined) minValue = Number.MIN_VALUE;
				  if (maxValue === undefined) maxValue = Number.MAX_VALUE;

				  return function predicateFunc(item) {
				    return minValue <= item[fieldName] && item[fieldName] <= maxValue;
				  };
				};
	
			var adults = $stateParams.adults;
			$scope.searchCity = $stateParams.city;
	
		
	  		$scope.datas = HouseEnd().query();
	  		//var calendar = CalendarInfo().query();
	  		sd = $scope.date.startDate.format("YYYY-MM-DD");
	  		ed = $scope.date.endDate.format("YYYY-MM-DD");
	  		var calendar = $resource('http://localhost:8080/airbnb/services/calendar/:startDate/:endDate', {startDate:'@sd', endDate:'@ed'}); 
	  		
	  		calendar.get( {startDate:sd, endDate:ed}, function(calendar){
	  			console.log(calendar.results);
	  		});	

	  		$scope.datas.$promise.then(function() {
		 
		  	$scope.retrieve_houses = [];  

			$scope.retrieve_houses = $filter('filter')($scope.datas, {city:$scope.searchCity, accommodates:adults});
			for(var i = 0; i < $scope.retrieve_houses.length; i++) {
				console.log($scope.retrieve_houses[i].houseID);
				// Call Calendar with parameters the two Dates
			}
			
		    $scope.retrieve_houses = $filter('orderBy')($scope.retrieve_houses, 'price');
			
		    
		    
			// Filter the results
			
			$scope.filter = function () {
				$scope.datas = HouseEnd().query();
				$scope.datas.$promise.then(function() {

					var filters = {};
					
					if($scope.value1 == true) {
						filters.tv = true;
					}
					
					if($scope.value2 == true) {
						filters.elevator = true;
					}
					
					if($scope.value3 == true) {
						filters.ac = true;
					}
					
					if($scope.value4 == true) {
						filters.heating = true;
					}
					
					if($scope.value5 == true) {
						filters.kitchen = true;
					}
					
					if($scope.value6 == true) {
						filters.parking = true;
					}
					
					if($scope.value7 == true) {
						filters.wifi = true;
					}
					
					if($scope.value8 == true) {
						filters.propertyType = "Apartment";
					}
					
					if($scope.value9 == true) {
						filters.propertyType = "House";
					}
					
					if($scope.value10 == true) {
						filters.propertyType = "Loft";
					}
					
					if($scope.value11 == true) {
						filters.roomType = "Entire home/apt";
					}
					
					if($scope.value12 == true) {
						filters.roomType = "Private room";
					}
					
					if($scope.value13 == true) {
						filters.roomType = "Shared room";
					}
					
					$scope.retrieve_houses = $filter('filter')($scope.datas, {city:$scope.searchCity, accommodates:adults});
					$scope.retrieve_houses = $filter('filter')($scope.retrieve_houses, filters);
					$scope.retrieve_houses = $filter('orderBy')($scope.retrieve_houses, 'price');
				    
					
					// Number of the results
					$scope.num_results = $scope.retrieve_houses.length;
					//////////
					$scope.viewby = 3;
					$scope.totalItems = $scope.retrieve_houses.length;
					$scope.currentPage = 1;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; //Number of pager buttons to show

					$scope.setPage = function (pageNo) {
						$scope.currentPage = pageNo;
					};

					$scope.pageChanged = function() {
						console.log('Page changed to: ' + $scope.currentPage);
					};

					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; //reset to first page
					}
					

					
					
				});

			}
			
			
			// Number of the results
			$scope.num_results = $scope.retrieve_houses.length;
			
			//////////
			$scope.viewby = 9;
			$scope.totalItems = $scope.retrieve_houses.length;
			$scope.currentPage = 1;
			$scope.itemsPerPage = $scope.viewby;
			$scope.maxSize = 5; //Number of pager buttons to show

			$scope.setPage = function (pageNo) {
				$scope.currentPage = pageNo;
			};

			$scope.pageChanged = function() {
				console.log('Page changed to: ' + $scope.currentPage);
			};

			$scope.setItemsPerPage = function(num) {
				$scope.itemsPerPage = num;
				$scope.currentPage = 1; //reset to first page
			}
				  
	  });
	
	

	
}]);

//////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('manageCtrl', ['$scope','HostHouses', '$resource', function ($scope, HostHouses, $resource) {
	
		// Retrieve all houses of a host
		console.log($scope.ID);
		var id = $scope.ID;
  		$scope.hou = HostHouses().query({hostid:id});
  		
}]);



/////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('houseCtrl', ['$scope', 'HouseEnd', 'NgMap', '$state', function ($scope, HouseEnd, NgMap, $state) {
	
	///////////////////////////////////// Upload the Photo ////////////////////////////////////////////////
	
	
    
	$scope.$on("fileProgress", function(e, progress) {
	      $scope.progress = progress.loaded / progress.total;
	});
	
	$scope.bin2String= function(array) {
		  var result = "";
		  for (var i = 0; i < array.length; i++) {
		    result += String.fromCharCode(parseInt(array[i], 2));
		  }
		  return result;
	};
	
	console.log($scope.imageSrc1);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// We have to make some changes
			  console.log(moment().format("YYYY-MM-DD"));

			  // Search Autocomplete //////////////////////////////////////////////////
			  $scope.vm = this;
			  $scope.vm.placeChanged = function() {
				 	$scope.newhouse = {};
					$scope.newhouse.hostID = $scope.ID;	  
					$scope.vm.place = this.getPlace();
					$scope.newhouse.latitude = $scope.vm.place.geometry.location.lat();
					$scope.newhouse.longitude = $scope.vm.place.geometry.location.lng();
					$scope.newhouse.country = $scope.vm.place.address_components[4].long_name;
					$scope.newhouse.countryCode = $scope.vm.place.address_components[4].short_name;
					$scope.newhouse.city = $scope.vm.place.address_components[2].short_name;
					$scope.newhouse.zipcode = $scope.vm.place.address_components[5].short_name.replace(/\s+/g, '');
					$scope.newhouse.state = $scope.vm.place.address_components[3].short_name;
					$scope.newhouse.street = $scope.vm.place.address_components[1].short_name + " " + $scope.vm.place.address_components[0].short_name;
			  }
			  ////////////////////////////////////////////////////////////////////////
	    
	    
		

 
		$scope.processForm = function () {
			
			$scope.newhouse.image_URL = window.btoa($scope.newhouse.image_URL);
			$scope.newhouse.calendarUpdated = moment().format("YYYY-MM-DD");
			if($scope.newhouse.city === 'Athina') 
				$scope.newhouse.city = 'Athens';
	        var saveresult = HouseEnd().save($scope.newhouse);
	        // $promise executes when there are results: equals to if
	        $state.go('profile', {userID:$scope.ID}, {reload: true});
	        
	        saveresult.$promise.then(function () { 
	        	
			});
		};
	 
		 
}]);


/////////////////////////////////////////////////////////////////////////////////////////////////////////

//Room Ctrl

app.controller('roomCtrl', ['$scope', 'HouseEnd', '$stateParams', '$resource', 'MapRev' , 'CalendarInfo', 'CalendarUpdate','ReviewEnd',function($scope, HouseEnd, $stateParams, $resource, MapRev, CalendarInfo, CalendarUpdate,ReviewEnd) {
	$scope.need_login_book=function()
	{
		alert("Login to book");
	}
	var id = $stateParams.houseID;
	$scope.reviews=ReviewEnd().query();
	  $scope.reviews.$promise.then(function() {
      	var total_stars = 0;
      	var reviews_for_this=0;
      	for(var i = 0; i < $scope.reviews.length; i++) {
      		if($scope.reviews[i].houseID==id){
      			total_stars += $scope.reviews[i].stars;
      			reviews_for_this++;
      		}
      	}
      	if(total_stars !== 0){
      		$scope.average = Math.round(total_stars / reviews_for_this);
      	}
      	else 
      	{
      		$scope.average = 0;
      	}
      	
      	console.log(total_stars);
      });
      
	
	var House = $resource('http://localhost:8080/airbnb/services/houses/:houseId', {houseId:'@id'});
	House.get( {houseId:id}, function(house){
		$scope.name = house.name;
		$scope.bathrooms = house.bathrooms;
		$scope.bedrooms = house.bedrooms;
		$scope.beds = house.beds;
		$scope.accommodates = house.accommodates;
		$scope.summary = house.summary;
		$scope.city = house.city;
		$scope.country = house.country;
		$scope.propertyType = house.propertyType;
		$scope.bedType = house.bedType;
		$scope.price = house.price;
		$scope.weeklyPrice = house.weeklyPrice;
		$scope.monthlyPrice = house.monthlyPrice;
		$scope.extraPeople = house.extraPeople;
		$scope.cleaningFee = house.cleaningFee;
		$scope.roomType = house.roomType;
		$scope.pictureURL = house.pictureURL;
		$scope.cleaningFee = house.cleaningFee;
		$scope.wifi = house.wifi;
		$scope.tv = house.tv;
		$scope.parking = house.parking;
		$scope.kitchen = house.kitchen;
		$scope.elevator = house.elevator;
		$scope.heating = house.heating;
		// Send latitude and longitude for reverse geocoding
		
		MapRev.init(house.latitude, house.longitude);
		MapRev.addCircle(house.latitude, house.longitude);
		MapRev.transitLayer();
		var id1 = house.hostID;
		
		// Call to the user resource
		var User = $resource('http://localhost:8080/airbnb/services/users/:userId', {userId:'@id1'});
		User.get ( {userId:id1}, function(user) {
			$scope.imgSrc = user.final_image;
			$scope.usID = user.userID;
			$scope.hostName = user.username;
		});
		
	})
	
	// Calendar and Booking info
	$scope.c = 0;
	
	// Check for availability
	$scope.check = function() {
		$scope.c = 1;
		
		sd = $scope.date.startDate.format("YYYY-MM-DD");
		ed = $scope.date.endDate.format("YYYY-MM-DD");
		var calendar = $resource('http://localhost:8080/airbnb/services/calendar/:startDate/:endDate', {startDate:'@sd', endDate:'@ed'});
		// Check if house is in the array of strings
  		
		$scope.warning = "";
		$scope.success = "";
		
  		calendar.get( {startDate:sd, endDate:ed}, function(calendar){
  			$scope.avail = false;
  			for(var i = 0; i < calendar.results.length; i++) {
  				if(id == calendar.results[i]) {
  					$scope.avail = true;
  				}
  			}
  			
  			if($scope.avail == false) {
  				$scope.warning = "Room is not available between " + sd + " and " + ed;
  				$scope.success = "";
  			}
  			else {
  				$scope.success = "Room is available from " + sd + " and " + ed;
  				$scope.warning = "";
  				$scope.numDates = calendar.numDates;
  				$scope.priceResult = calendar.numDates * $scope.price;
  				$scope.finalSum = $scope.priceResult + $scope.cleaningFee;
  			}
  			
  			
  		});
  		

	}	
		var guestID = $scope.ID;
		// Book the house for the given dates
		$scope.book = function() {
			$scope.warning = " ";
			$scope.success = " ";
			if($scope.avail == true) {
				CalendarUpdate.update({ startDate:sd, endDate:ed, houseid:id, guest:guestID }, null);
				$scope.success = "Room has been booked from " + sd + " and " + ed;
				$scope.warning = " ";
			}
			else {
				$scope.warning = "Room is not available between" + sd + " and " + ed; 
			}
		}
	
}]);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// Profile Controller

app.controller('profileCtrl', ['$scope', 'UserEnd', '$stateParams', '$resource' ,function ($scope, UserEnd, $stateParams, $resource) {
	var id = $stateParams.userID;
	// Make a call to the restful to retrieve the data of the user
	
	var User = $resource('http://localhost:8080/airbnb/services/users/:userId', {userId:'@id'}); 
	User.get( {userId:id}, function(user){
		$scope.username=user.username;
		$scope.userID = user.userID;
		$scope.firstName=user.firstName;
		$scope.lastName=user.lastName;
		$scope.email = user.email;
		$scope.host = user.host;
		$scope.verifiedFromAdmin = user.verifiedFromAdmin;
		//$scope.image_URL="/9j/"+user.image_URL;
		$scope.final_image = user.final_image;
	})		
	
	$scope.goToEdit = function()
	{
		window.location.href = 'http://localhost:8080/airbnb/#!/profile/edit/' + id;

	};
	
}]);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// New House Entry Controller

app.controller('newHouseCtrl', ['$scope', 'UserEnd', '$stateParams', '$resource', 'HouseEnd',function ($scope, UserEnd, $stateParams, $resource, HouseEnd) {
	var id = $stateParams.userID;
	// Make a call to the restful to retrieve the data of the user
	var houses = HouseEnd().query();
	$scope.newhouse = new Object();
	

	$scope.newhouse.name = $scope.housename;
	$scope.newhouse.hostID = parseInt(id);
	
	
	$scope.processForm = function () {
        var saveresult = HouseEnd().save($scope.newhouse);
        // $promise executes when there are results: equals to if
		saveresult.$promise.then(function () { 
		// Returns data in json format	
        $scope.houses = HouseEnd().query();
		});
	};
	
	
}]);


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


app.controller('editCtrl', ['$scope', 'UserEnd', '$stateParams', '$resource' , '$http', 'UserUpdate', '$window','$state',function ($scope, UserEnd, $stateParams, $resource, $http, UserUpdate, window,$state) {
	
	$scope.imageSrc = "";
    
	$scope.$on("fileProgress", function(e, progress) {
	      $scope.progress = progress.loaded / progress.total;
	});
	
	$scope.bin2String= function(array) {
		  var result = "";
		  for (var i = 0; i < array.length; i++) {
		    result += String.fromCharCode(parseInt(array[i], 2));
		  }
		  return result;
	  };
	
	
	var id = $stateParams.userID;

	
	// Make a call to the restful to retrieve the data of the user
	
	$scope.newuser = new Object();
	
	var User = $resource('http://localhost:8080/airbnb/services/users/:userId', {userId:'@id'}); 
	User.get( {userId:id}, function(user){
		$scope.username = user.username;
		$scope.userID = user.userID;
		$scope.firstName = user.firstName;
		$scope.lastName = user.lastName;
		$scope.email = user.email;
		$scope.password = user.password;
		$scope.birthday = new Date(user.birthday);
		$scope.host = user.host;
		
		// Pass the current data to the new user object
		$scope.newuser.username = user.username;
		$scope.newuser.firstName = user.firstName;
		$scope.newuser.lastName = user.lastName;
		$scope.newuser.email = user.email;
		$scope.newuser.birthday = new Date(user.birthday);
		$scope.newuser.verifiedFromAdmin = user.verifiedFromAdmin;
		$scope.newuser.host = user.host;
		//$scope.newuser.image_URL= user.image_URL;
		
	})		
	
	

	// Updated Data
	
	
	$scope.newuser.username = $scope.username;
	$scope.newuser.firstName = $scope.firstName;
	$scope.newuser.lastName = $scope.lastName;
	$scope.newuser.email = $scope.email;
	$scope.newuser.birthday = new Date($scope.birthday);	
	$scope.newuser.verifiedFromAdmin = $scope.verifiedFromAdmin;
	$scope.newuser.host = $scope.host;
	//$scope.newuser.image_URL= $scope.image_URL;
	
	$scope.UpdateProfile = function() {
		// put call
		if($scope.newuser.verifiedFromAdmin==true)		
			alert("Wait for verification");
		var userId = $stateParams.userID;
		if($scope.imageSrc !== "") {
			$scope.imageSrc = window.btoa($scope.imageSrc);
			$scope.newuser.image_URL= $scope.imageSrc;
		}
		// Now call update passing in the ID first then the object you are updating
		UserUpdate.update({ id:id }, $scope.newuser);
		//$window.location.reload();
		$state.go('profile', {userID:id});	

	};
	
	var data = UserEnd().query();
	
	$scope.checkName = function (key) {
		

		data.$promise.then(function () { 
			// Returns data in json format	
		    var i, len = data.length;
			$scope.isSameName=false;
		    for (i = 0; i < len; i++) {
		    		if (data[i].username == key) {
		    			$scope.isSameName=true;
		    			return;
		    		}
			    }
			 return;
		});
	};
	$scope.checkEmail = function (key) {
		
		data.$promise.then(function () { 
			// Returns data in json format	
		    var i, len = data.length;
			$scope.isSameEmail=false;
		    for (i = 0; i < len; i++) {
		    		if (data[i].email == key) {
		    			$scope.isSameEmail=true;
		    			return;
		    		}
			    }
			 return;
		});
	};
}]);


app.controller('adminCtrl', ['$scope','UserEnd','$window','$state','$filter','UserUpdate','$resource', '$state', function ($scope,UserEnd,window,$state,$filter,UserUpdate,$resource, $state) {
	var datas=UserEnd().query();
	$scope.needApproval=[];
	datas.$promise.then(function () { 
		$scope.needApproval = $filter('filter')(datas, {verifiedFromAdmin:true});
	});
	
	$scope.Accept=function(){
		 $scope.hostsArray = [];
		 $scope.hostsArray = $filter('filter')($scope.needApproval, { selected: true }, true);
		 $scope.hostsArray.forEach( function (host)
		 {
			$scope.newuser = new Object();
			$scope.newuser.userID = host.userID;
			$scope.newuser.username = host.username;
			$scope.newuser.firstName = host.firstName;
			$scope.newuser.lastName = host.lastName;
			$scope.newuser.email = host.email;
			$scope.newuser.birthday = host.birthday;
			var la = new Date(host.birthday);
			$scope.newuser.verifiedFromAdmin = false;
			$scope.newuser.host=true;
			$scope.newuser.password=host.password;
			$scope.newuser.image_URL= host.image_URL;
			UserUpdate.update({ id:$scope.newuser.userID }, $scope.newuser);

		  });
			$state.reload();

	};
}]);

app.controller('exportCtrl', ['$scope','HouseExp','$window','$state','$filter','ReviewExp','$resource', 'ResExp', function ($scope,HouseExp,window,$state,$filter,ReviewExp,$resource, ResExp) {		
	$scope.expHouses= function(){		
		HouseExp.query();		
	};		
	$scope.expRes= function(){		
		ResExp.query();
	};		
	$scope.expReviews= function(){		
		ReviewExp.query();		
				
	};		
}]);

app.controller('reviewCtrl',['$scope','$stateParams','ReviewEnd','$filter','$rootScope', '$resource','UserEnd', 'MessageEnd','$window', function($scope,$stateParams,ReviewEnd,$filter,$rootScope,$resource,UserEnd, MessageEnd,$window)		
	{			
		// Make a call to the restful to retrieve the data of the userstate
		
	    $scope.firstRate = 0;	
	    $scope.boxShow = false;
	    $scope.connected=typeof window.sessionStorage.token;
		$scope.id2 = $stateParams.houseID;		
		$scope.reviews = ReviewEnd().query();		
		$scope.newreview = new Object();		
		$scope.newreview.date = new Date();		
		$scope.need_login_msg = function()
		{
		        alert("Login to send a message");
		}
		$scope.btn_post = function() {
			if ($scope.newreview.comments !== undefined) {
			if( $scope.connected== 'undefined')
			{
				alert("Login to write a review");
				return;
			}
			if($scope.firstRate==0)
			{
				alert("Ranking must be >0");
				return;
			}
			if ($scope.newreview.comments != '')		
			{		
				//$scope.comments.push($scope.newreview.comments);		
				$scope.newreview.houseID = parseInt($scope.id2);		
				$scope.newreview.reviewerID= $scope.ID;
				
				$scope.newreview.stars=$scope.firstRate;		
				// insert review in json format to the RESTful API		
			    var saveresult = ReviewEnd().save($scope.newreview);		
			    // $promise executes when there are results: equals to if		
				saveresult.$promise.then(function () { 		
					// Returns data in json format			
			        $scope.reviews = ReviewEnd().query();		
			        delete $scope.firstRate;
			        delete $scope.newreview.comments;
			        
					});		
			}
			}
		};		
		
		$scope.users = UserEnd().query();		
		$scope.newmessage = new Object();

	    $scope.sendMessage = function(msg) {
	    	$scope.boxShow=false;  //hide textarea
	    	$scope.newmessage = new Object();
	    	$scope.newmessage.receiverID=$scope.usID;
	    	$scope.newmessage.senderID=$scope.ID;
	    	$scope.newmessage.message=msg;
	    	  var saveresult = MessageEnd().save($scope.newmessage);		
			    // $promise executes when there are results: equals to if		
				saveresult.$promise.then(function () { 		
					// Returns data in json format			
			        $scope.messages = MessageEnd().query();
			        
			        		
					});		
	    	
	    };
		
	}]);



app.controller('recommendCtrl', ['$scope','ReviewEnd','UserEnd','HouseEnd','$resource',function ($scope,ReviewEnd,UserEnd,HouseEnd,$resource) {
	var reviews=ReviewEnd().query();
	var users=UserEnd().query();
	var houses=HouseEnd().query();
	var arr = [[]];
	var i=0;
	var j=0;
	var x=0;
	var current_user=[];
	var is_reviewed=0;
	users.$promise.then(function(){
		houses.$promise.then(function()  //make 2-dimensional array of every user and every house.Each cell contains a virtual number(1) 
											//if review in the specific house exists and positive or 0 in any different case
		{
			reviews.$promise.then(function()
			{
				angular.forEach(users,function(user)
				{
					
					if(user.username!=='admin')
					{
					arr[i]=[];
					j=0;
					angular.forEach(houses,function(house)
					{
						angular.forEach(reviews,function(review)
						{
							if(review.reviewerID==user.userID && house.houseID==review.houseID)
							{
								 is_reviewed=1;
								 var sentimood = new Sentimood();
								 var analysis = sentimood.analyze(review.comments);   //transform the words into a number depending the produced emotion
								 var analysis=analysis.score * review.stars;
								 if(analysis<0)
									 arr[i][j]=0;
								 else arr[i][j]=1;
									 console.log(arr[i][j]);
								if($scope.ID===user.userID)
								{
									 x=i; //line with data of current_user
								}
																	 
							}
						 })
						 if(is_reviewed == 0)
						 {	 
							 if($scope.ID===user.userID)
						 	 {
						 		 x=i;
						 	 }
							 arr[i][j]=0;
						 
						 }
						 else 
						 {
							 is_reviewed=0;
						 }
						 j++;
					})
					i++;
					}
				})
				current_user=arr[x];
				arr=arr.slice(0);   
				arr.splice(x,1);    //remove the current user vector from the array
				console.log(arr);
				console.log(current_user);
				console.log(arr.length);
				const t=new Table(arr[0].length, 2,arr.length);	  //start of lsh algorithm
				for(var n=0;n<arr.length;n++)
				{
					t.add(new Vector(arr[n]));
				}
				recommendation=t.query(new Vector(current_user));
				var minHash_vector=[];  
				for(z=0;z<recommendation.size();z++)
				{
					minHash_vector[z]=recommendation.get(z);  //construct the vector depending from the closest neighbors
				}
				console.log('minHash_vector'+minHash_vector);
				var final_vector=[];
				for(z=0;z<recommendation.size();z++)
				{
					if(minHash_vector[z]==1)     //if is recommended
					{	if(current_user[z]==0)     //and has not been positively reviewed by the user
							final_vector[z]=1;    //add to the final vector
						else
							final_vector[z]=0;

					}
					else
					{
						final_vector[z]=0;
					}
				}
				z=0;
				has_been_there=0;
				$scope.recommendation_list=[];
				houses.$promise.then(function(){
				angular.forEach(houses,function(house)
				{
					if(final_vector[z]==1)   //if is recommended
					{
						angular.forEach(reviews,function(review)
						{
							if($scope.ID==review.reviewerID && house.houseID==review.houseID  || house.hostID==$scope.ID)
							{
								has_been_there=1;    //and is not been negatively reviewed or owned by the user
							}
						})

						if(has_been_there==0)
						{
							$scope.recommendation_list.push(house.houseID);
						}
						else
							has_been_there=0;
							
					}
					z++;
					
				})	
				console.log($scope.recommendation_list);

				$scope.house_list=[];
				for(var i = 0; i < $scope.recommendation_list.length; i++) {
					var id = $scope.recommendation_list[i];
					var House = $resource('http://localhost:8080/airbnb/services/houses/:houseId', {houseId:'@id'});
					House.get( {houseId:id}, function(house){
						$scope.house_list.push(house);
					});
		
				}
				})
			})
		})
	})
	
	
	
}]);






app.controller('inboxCtrl', ['$scope', 'MessageEnd','UserEnd', '$stateParams', '$resource' ,'$state',function ($scope, MessageEnd, UserEnd,$stateParams, $resource,$state) {
	$scope.messages = MessageEnd().query();
	$scope.users = UserEnd().query();
	$scope.current_messages = [];
	$scope.length = 0;
	$scope.messages.$promise.then(function () { 
	$scope.users.$promise.then(function () { 	
		$scope.messages.forEach(function(message)
		{
			$scope.users.forEach(function(user)
			{
				if(message.receiverID == $scope.ID && user.userID == message.senderID)
				{
					$scope.current_messages.push(message);

				}
			});

		});
		$scope.viewby = 2;
	 	$scope.totalItems=$scope.current_messages.length;
	 	$scope.currentPage = 1;
	 	$scope.itemsPerPage = $scope.viewby;
	 	$scope.maxSize = 5; //Number of pager buttons to show

	 	$scope.setPage = function (pageNo) {
	 		$scope.currentPage = pageNo;
	 	};

	 	$scope.pageChanged = function() {
	 		console.log('Page changed to: ' + $scope.currentPage);
	 	};

	 	$scope.setItemsPerPage = function(num) {
	 		$scope.itemsPerPage = num;
	 		$scope.currentPage = 1; //reset to first paghe
	 	};
	});
 	});
	
	$scope.receiver=$scope.ID;
	$scope.del_mess="";
	$scope.openMessage=function(inbox){
		$scope.msg=inbox;
	};
	
	$scope.replyTo=function(user){
		$scope.To=user;
	};
	$scope.replyMessage=function(){
		$scope.newmessage=new Object();
    	$scope.newmessage.receiverID=$scope.To;
    	$scope.newmessage.senderID=$scope.ID;
    	$scope.newmessage.message=$scope.reply;
    	 var saveresult = MessageEnd().save($scope.newmessage);		
		    // $promise executes when there are results: equals to if		
			saveresult.$promise.then(function () { 		
				// Returns data in json format			
		        $scope.messages = MessageEnd().query();	
		       
		    		
		        		
		});		
		
	};
	$scope.deleteMessage = function(messageID){
		var Message = $resource('http://localhost:8080/airbnb/services/messages/:messageId', {messageId:'@messageID'});
		Message.delete( {messageId:messageID}, function(){

	})	
	$state.reload();

	
	};
}]);


