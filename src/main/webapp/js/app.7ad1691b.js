(function(t){function e(e){for(var r,s,n=e[0],l=e[1],c=e[2],m=0,p=[];m<n.length;m++)s=n[m],Object.prototype.hasOwnProperty.call(o,s)&&o[s]&&p.push(o[s][0]),o[s]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(t[r]=l[r]);u&&u(e);while(p.length)p.shift()();return i.push.apply(i,c||[]),a()}function a(){for(var t,e=0;e<i.length;e++){for(var a=i[e],r=!0,n=1;n<a.length;n++){var l=a[n];0!==o[l]&&(r=!1)}r&&(i.splice(e--,1),t=s(s.s=a[0]))}return t}var r={},o={app:0},i=[];function s(e){if(r[e])return r[e].exports;var a=r[e]={i:e,l:!1,exports:{}};return t[e].call(a.exports,a,a.exports,s),a.l=!0,a.exports}s.m=t,s.c=r,s.d=function(t,e,a){s.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:a})},s.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},s.t=function(t,e){if(1&e&&(t=s(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var a=Object.create(null);if(s.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)s.d(a,r,function(e){return t[e]}.bind(null,r));return a},s.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return s.d(e,"a",e),e},s.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},s.p="/ProgrammazioneWebMattiaBressanEsame/";var n=window["webpackJsonp"]=window["webpackJsonp"]||[],l=n.push.bind(n);n.push=e,n=n.slice();for(var c=0;c<n.length;c++)e(n[c]);var u=l;i.push([0,"chunk-vendors"]),a()})({0:function(t,e,a){t.exports=a("56d7")},"2cd7":function(t,e,a){"use strict";a("4451")},"3cfd":function(t,e,a){"use strict";a("cb99")},4451:function(t,e,a){},"53cc":function(t,e,a){"use strict";a("be58")},5478:function(t,e,a){},"56d7":function(t,e,a){"use strict";a.r(e);var r=a("2b0e"),o=function(){var t=this,e=t._self._c;return e("div",{attrs:{id:"app"}},["login"!=this.$route.name&&"signup"!=this.$route.name?e("Navbar"):t._e(),e("router-view",{scopedSlots:t._u([{key:"default",fn:function({Component:t}){return[e("transition",{attrs:{name:"fade"}},[e(t,{tag:"component"})],1)]}}])})],1)},i=[],s=a("bc3a"),n=a.n(s),l=a("db49"),c=a.n(l),u=()=>n.a.create({baseURL:c.a.backend_url,headers:{Authorization:"Bearer "+localStorage.token,"Access-Control-Allow-Origin":"*"}});const m="/user";var p={login(t){return u().post(m+"/login",t)},register(t){return u().post(m+"/signup",t)},getAllUsers(){return u().get(m)},getUser(t){return u().get(m+"/"+t)},deleteUser(t){return u().delete(m+"/"+t)},logout(){return u().delete(m+"/logout")}},d=function(){var t=this,e=t._self._c;return e("div",{staticClass:"mynavbar"},[e("b-navbar",{attrs:{toggleable:"lg",type:"dark",variant:"success"}},[e("b-navbar-brand",{attrs:{to:{path:"/"}}},[e("img",{staticClass:"d-inline-block align-center img-fluid logo-img",attrs:{src:a("cf05"),alt:"airplane_trip"}}),e("em",{staticClass:"ml-3"},[t._v("Viaggio diario")])]),e("b-navbar-toggle",{attrs:{target:"nav-collapse"}}),e("b-collapse",{attrs:{id:"nav-collapse","is-nav":""}},[e("b-navbar-nav",{staticClass:"ml-auto d-flex align-items-center",attrs:{center:""}},[t.isAuthenticated?e("b-nav-item-dropdown",{staticClass:"align-center",attrs:{right:""},scopedSlots:t._u([{key:"button-content",fn:function(){return[e("b-icon-person-circle",{staticClass:"mr-3",attrs:{"font-scale":"1.5"}}),e("em",[t._v(t._s(t.userinfo.firstname))]),e("b-icon",{attrs:{icon:"airplane","aria-hidden":"true"}})]},proxy:!0}],null,!1,1971256512)},[e("b-dropdown-item",{attrs:{to:{path:"/profile"}}},[e("b-button",{staticClass:"mb-2",attrs:{variant:"outline-info"}},[e("b-icon",{attrs:{icon:"person-fill","aria-hidden":"true"}}),t._v(" Profile ")],1)],1),e("b-dropdown-item",{on:{click:function(e){return e.preventDefault(),t.logout()}}},[e("b-button",{staticClass:"mb-2",attrs:{variant:"outline-info"}},[e("b-icon",{attrs:{icon:"power","aria-hidden":"true"}}),t._v(" Logout ")],1)],1)],1):t._e()],1)],1)],1)],1)},f=[],b={name:"Navbar",props:{msg:String},data:function(){return{isAuthenticated:!1,userinfo:{name:"Mattia",lastname:"Bressan"}}},methods:{logout:async function(){try{const t=await p.logout();200==t.status&&(localStorage.setItem("token",null),localStorage.setItem("isAuthenticated",!1),this.isAuthenticated=!1,this.$router.push({path:"/login"}))}catch(t){const e=t.toJSON();401==e.status?alert("Non autorizzato"):404==e.status?alert("Utente non trovato"):409==e.status?alert("Username già presente"):alert(e.message)}}},mounted:function(){this.isAuthenticated="true"===localStorage.getItem("isAuthenticated"),this.userinfo=JSON.parse(JSON.parse(localStorage.getItem("userinfo")))}},g=b,h=(a("3cfd"),a("2877")),v=Object(h["a"])(g,d,f,!1,null,"0c562353",null),y=v.exports,w={name:"App",components:{Navbar:y},data:function(){return{isAuthenticated:!1}},methods:{logout:async function(){const t=await p.logout();console.log(t),200!=t.status?alert("Non sono riuscito a fare logout"):(localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"}))}},mounted:function(){this.isAuthenticated="true"===localStorage.getItem("isAuthenticated")}},S=w,_=(a("c64b"),Object(h["a"])(S,o,i,!1,null,null,null)),k=_.exports,I=a("8c4f"),C=a("130e"),x=function(){var t=this,e=t._self._c;return e("div",{staticClass:"login"},[e("b-container",{staticClass:"background-image-trip min-vh-100",attrs:{fluid:""}},[e("b-container",[e("b-row"),e("b-row",{staticClass:"mt-5 mb-5"},[e("b-col",{attrs:{cols:"12",md:"6"}},[e("h1",{staticStyle:{color:"#0963c4","font-size":"2.9rem"}},[e("b",[t._v("Il tuo diario dei viaggi ")]),e("br"),e("span",{staticStyle:{color:"rgb(3 153 117)","font-size":"2.4rem"}},[t._v("Ti accompagna in tutti i viaggi")])]),e("p",{staticClass:"mb-4",staticStyle:{color:"rgb(1 86 65)","font-size":"1.1rem"}},[t._v(" “Un viaggio non inizia nel momento in cui partiamo né finisce nel momento in cui raggiungiamo la meta. In realtà comincia molto prima e non finisce mai, dato che il nastro dei ricordi continua a scorrerci dentro anche dopo che ci siamo fermati. È il virus del viaggio, malattia sostanzialmente incurabile”"),e("br"),t._v(" Ryszard Kapuscinski ")])]),e("b-col",[e("div",{staticClass:"card bg-glass"},[e("div",{staticClass:"card-body px-4 py-5 px-md-5"},[e("b-form",[e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.firstname,expression:"firstname"}],staticClass:"form-control",attrs:{type:"text",id:"firstname"},domProps:{value:t.firstname},on:{input:function(e){e.target.composing||(t.firstname=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"firstname"}},[t._v("First name")])]),e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.lastname,expression:"lastname"}],staticClass:"form-control",attrs:{type:"text",id:"lastname"},domProps:{value:t.lastname},on:{input:function(e){e.target.composing||(t.lastname=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"lastname"}},[t._v("Last name")])])],1),e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.email,expression:"email"}],staticClass:"form-control",attrs:{type:"email",id:"email"},domProps:{value:t.email},on:{input:function(e){e.target.composing||(t.email=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"email"}},[t._v("Email address")])])],1),e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.username,expression:"username"}],staticClass:"form-control",attrs:{type:"text",id:"username"},domProps:{value:t.username},on:{input:function(e){e.target.composing||(t.username=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"username"}},[t._v("Username")])])],1),e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.password,expression:"password"}],staticClass:"form-control",attrs:{type:"password",id:"form3Example4"},domProps:{value:t.password},on:{input:function(e){e.target.composing||(t.password=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"form3Example4"}},[t._v("Password")])])],1),e("b-form-row",[e("b-col",[e("button",{staticClass:"btn btn-primary btn-block mb-4",attrs:{type:"submit"},on:{click:function(e){return e.preventDefault(),t.signup()}}},[t._v(" Sign up ")])])],1),e("b-form-row",[e("b-col",[t._v(" Go to "),e("router-link",{attrs:{to:"/login"}},[t._v("Login")])],1)],1)],1)],1)])])],1),e("b-row")],1)],1)],1)},D=[],T={name:"Signup",components:{},data:function(){return{firstname:null,lastname:null,email:null,username:null,password:null}},methods:{isValidEmail(t){let e=/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/;return!!e.test(t)},checkInputs:function(){return void 0===this.firstname||null===this.firstname||""===this.firstname?(alert("Inserisci firstname"),!1):void 0===this.lastname||null===this.lastname||""===this.lastname?(alert("Inserisci lastname"),!1):void 0!==this.email&&null!==this.email&&""!==this.email&&this.isValidEmail(this.email)?void 0===this.username||null===this.username||""===this.username?(alert("Inserisci username"),!1):void 0!==this.password&&null!==this.password&&""!==this.password||(alert("Inserisci password"),!1):(alert("Controlla l' email inserita"),!1)},signup:async function(){const t={firstname:this.firstname,lastname:this.lastname,email:this.email,username:this.username,password:this.password};if(this.checkInputs())try{const e=await p.register(t);console.log(e),200==e.status&&(localStorage.setItem("userinfo",JSON.stringify(e.data.userinfo)),localStorage.setItem("token",e.data.Authorization),localStorage.setItem("isAuthenticated",!0),this.$router.push({path:"/trips"}))}catch(e){const t=e.toJSON();401==t.status?alert("credenziali sbagliate"):404==t.status?alert("Utente non trovato"):409==t.status?alert("Username già presente"):alert("Problema nel signup")}}},mounted:function(){}},N=T,O=(a("53cc"),Object(h["a"])(N,x,D,!1,null,"3feae7ae",null)),A=O.exports,P=function(){var t=this,e=t._self._c;return e("div",{staticClass:"login"},[e("b-container",{staticClass:"background-image-trip min-vh-100",attrs:{fluid:""}},[e("b-container",[e("b-row"),e("b-row",{staticClass:"mt-5 mb-5"},[e("b-col",{attrs:{cols:"12",md:"6"}},[e("h1",{staticStyle:{color:"#0963c4","font-size":"2.9rem"}},[e("b",[t._v("Il tuo diario dei viaggi ")]),e("br"),e("span",{staticStyle:{color:"rgb(3 153 117)","font-size":"2.4rem"}},[t._v("Ti accompagna in tutti i viaggi")])]),e("p",{staticClass:"mb-4",staticStyle:{color:"rgb(1 86 65)","font-size":"1.1rem"}},[t._v(" “Un viaggio non inizia nel momento in cui partiamo né finisce nel momento in cui raggiungiamo la meta. In realtà comincia molto prima e non finisce mai, dato che il nastro dei ricordi continua a scorrerci dentro anche dopo che ci siamo fermati. È il virus del viaggio, malattia sostanzialmente incurabile”"),e("br"),t._v(" Ryszard Kapuscinski ")])]),e("b-col",[e("div",{staticClass:"card bg-glass"},[e("div",{staticClass:"card-body px-4 py-5 px-md-5"},[e("b-form",[e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.username,expression:"username"}],staticClass:"form-control",attrs:{type:"text",id:"username"},domProps:{value:t.username},on:{input:function(e){e.target.composing||(t.username=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"username"}},[t._v("Username")])])],1),e("b-form-row",[e("b-col",[e("input",{directives:[{name:"model",rawName:"v-model",value:t.password,expression:"password"}],staticClass:"form-control",attrs:{type:"password",id:"form3Example4"},domProps:{value:t.password},on:{input:function(e){e.target.composing||(t.password=e.target.value)}}}),e("label",{staticClass:"form-label",attrs:{for:"form3Example4"}},[t._v("Password")])])],1),e("b-form-row",[e("b-col",[e("button",{staticClass:"btn btn-primary btn-block mb-4",attrs:{type:"submit"},on:{click:function(e){return e.preventDefault(),t.login()}}},[t._v(" login ")])])],1),e("b-form-row",[e("b-col",[t._v(" Go to "),e("router-link",{attrs:{to:"/signup"}},[t._v("Signup")])],1)],1)],1)],1)])])],1),e("b-row")],1)],1)],1)},z=[],E={name:"Login",components:{},data:function(){return{username:null,password:null}},methods:{checkInputs:function(){return void 0===this.username||null===this.username||""===this.username?(alert("Inserisci username"),!1):void 0!==this.password&&null!==this.password&&""!==this.password||(alert("Inserisci password"),!1)},login:async function(){const t={username:this.username,password:this.password};if(this.checkInputs())try{const e=await p.login(t);console.log(e),200==e.status&&(localStorage.setItem("userinfo",JSON.stringify(e.data.userinfo)),localStorage.setItem("token",e.data.Authorization),localStorage.setItem("isAuthenticated",!0),this.$router.push({path:"/trips"}))}catch(e){const t=e.toJSON();401==t.status?alert("credenziali sbagliate"):404==t.status?alert("Utente non trovato"):alert("Problema nel login")}}},mounted:function(){}},$=E,J=(a("8da9"),Object(h["a"])($,P,z,!1,null,"7f279f68",null)),L=J.exports,j=function(){var t=this,e=t._self._c;return e("div",{staticClass:"trip"},[e("div",{staticClass:"container"},[e("form",[e("b-row",{staticClass:"mt-5"},[e("b-col",[e("router-link",{attrs:{to:{name:"trips"}}},[e("b-button",{attrs:{variant:"success"}},[e("b-icon-arrow-left"),t._v(" Indietro ")],1)],1)],1),e("b-col",[e("h1",[t._v("Viaggio a: "+t._s(t.trip.name))])]),e("b-col",[t.editmode?e("b-button",{attrs:{variant:"success"},on:{click:function(e){return e.preventDefault(),t.editTrip()}}},[t._v("Modifica")]):t._e(),t.editmode?t._e():e("b-button",{attrs:{variant:"success"},on:{click:function(e){return e.preventDefault(),t.addTrip()}}},[t._v("Aggiungi")])],1)],1),e("b-row",{staticClass:"mt-2"},[e("b-col",[e("label",{attrs:{for:"name"}},[t._v("Nome:")])]),e("b-col",[e("label",{attrs:{for:"tripDate"}},[t._v("Data del viaggio:")])]),e("b-col",[e("label",{attrs:{for:"vehicle"}},[t._v("Veicolo:")])])],1),e("b-row",{staticClass:"mt-2 mb-4"},[e("b-col",[e("b-form-input",{attrs:{id:"name",placeholder:"Nome",type:"text"},model:{value:t.trip.name,callback:function(e){t.$set(t.trip,"name",e)},expression:"trip.name"}})],1),e("b-col",[e("b-form-datepicker",{staticClass:"mb-2",attrs:{id:"tripDate","date-format-options":{year:"numeric",month:"numeric",day:"numeric"}},model:{value:t.trip.tripDate,callback:function(e){t.$set(t.trip,"tripDate",e)},expression:"trip.tripDate"}})],1),e("b-col",[e("b-form-input",{attrs:{id:"vehicle",placeholder:"Veicolo",type:"text"},model:{value:t.trip.vehicle,callback:function(e){t.$set(t.trip,"vehicle",e)},expression:"trip.vehicle"}})],1)],1)],1),e("b-row",[t.mapReady?e("l-map",{ref:"myMap",staticStyle:{height:"500px"},attrs:{zoom:t.zoom,center:t.center,drawControl:t.drawControl},on:{ready:function(e){return t.setUpTheMap(t.trip.path)}}},[e("l-draw"),e("l-tile-layer",{attrs:{url:t.url,attribution:t.attribution}}),e("l-feature-group",{ref:"features"}),e("l-geo-json",{attrs:{geojson:t.geojson}})],1):t._e()],1),e("div",[e("b-row",{staticClass:"mt-2"},[e("b-col",[e("h3",[t._v("Tappe")])])],1),e("b-table",{staticClass:"mt-3 mb-3",attrs:{hover:"",items:t.tappe,fields:t.fields,"label-sort-asc":"","label-sort-desc":"","label-sort-clear":""},scopedSlots:t._u([{key:"cell(index)",fn:function(e){return[t._v(" "+t._s(e.index+1)+" ")]}},{key:"cell(lat)",fn:function(e){return[t._v(" "+t._s(e.item.geometry.coordinates[1])+" ")]}},{key:"cell(long)",fn:function(e){return[t._v(" "+t._s(e.item.geometry.coordinates[0])+" ")]}}])})],1)],1)])},M=[],U=a("2699"),B=a("a40a"),G=a("c5ca"),V=a("044a");const R="/trip";var q={getAllTrips(){return u().get(R)},getTripByID(t){return u().get(R+"/"+t)},getAllTripsByDate(t,e){let a=null===t||void 0===t||""===t,r=null===e||void 0===e||""===e;return a||r?a?u().get(R+"?enddate="+e):r?u().get(R+"?startdate="+t):void 0:u().get(R+"?startdate="+t+"&enddate="+e)},createTrip(t){return u().post(R,t)},updateTrip(t,e){return u().put(R+"/"+t,e)},deleteTrip(t){return u().delete(R+"/"+t)}},F=a("e11e"),Y=a.n(F),W=a("20d6"),K=a.n(W),H={name:"Trip",components:{LMap:U["a"],LTileLayer:B["a"],LFeatureGroup:G["a"],LDraw:K.a,LGeoJson:V["a"]},data(){return{url:"https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",attribution:'&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',zoom:15,center:[45.6495,13.7768],editmode:!1,drawControl:!0,geojson:{},drawnItems:null,trip:{id:null,name:null,tripDate:null,vehicle:null,path:null},tappe:[],mapReady:!1,fields:[{key:"index",label:"index"},{key:"lat",label:"Latitudine",sortable:!0},{key:"long",label:"Longitudine",sortable:!0}]}},methods:{checkInputs:function(){return void 0===this.trip||null===this.trip?(alert("Error"),!1):void 0===this.trip.name||null===this.trip.name||""===this.trip.name?(alert("inserisci il Nome del viaggio"),!1):void 0===this.trip.tripDate||null===this.trip.tripDate||""===this.trip.tripDate?(alert("inserisci la data del viaggio"),!1):void 0===this.trip.vehicle||null===this.trip.vehicle||""===this.trip.vehicle?(alert("inserisci il veicolo usato"),!1):void 0!==this.trip.path&&null!==this.trip.path||(alert("inserisci il percorso"),!1)},loadTrip:async function(t){try{const e=await q.getTripByID(t);200==e.status?this.trip=e.data:alert("Problema caricamento viaggio")}catch(e){const t=e.toJSON();401==t.status?(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})):404==t.status?alert("Viaggio non trovato"):alert(t.message)}},addTrip:async function(){if(this.trip.path=this.drawnItems.toGeoJSON(),this.checkInputs())try{const t=await q.createTrip(this.trip).catch(this.handleAxiosError);201==t.status?(this.trip=t.data,alert("Viaggio inserito correttamente"),this.$router.push({path:"/trips"})):alert("Problema nell'inserimento del viaggio")}catch(t){const e=t.toJSON();401==e.status&&(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})),409==e.status?alert("Problema nell'inserimento"):alert(e.message)}},editTrip:async function(){this.trip.path=this.drawnItems.toGeoJSON();try{const t=await q.updateTrip(this.trip.id,this.trip);this.checkInputs()&&200==t.status&&(this.trip=t.data,alert("Viaggio aggiornato correttamente"),this.$router.push({path:"/trips"}))}catch(t){const e=t.toJSON();401==e.status?(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})):409==e.status?alert("Non riuscito ad aggiornare"):404==e.status?alert("Non trovato"):alert(e.message)}},setUpTheMap(t){let e=this.$refs.myMap.mapObject;var a=new Y.a.FeatureGroup;e.addLayer(a),this.drawnItems=a;var r=new Y.a.Control.Draw({draw:{polygon:!1,circle:!1,rectangle:!1,circlemarker:!1},edit:{featureGroup:a}});function o(t,e){a.addLayer(e)}e.addControl(r),Y.a.control.layers({},{drawlayer:a},{position:"topleft",collapsed:!1}).addTo(e),e.on(Y.a.Draw.Event.CREATED,(function(t){var e=t.layer;a.addLayer(e)}));var i=null;null!=t&&(i=this.getPoints(t),Y.a.geoJson(t,{onEachFeature:o}));const s=this.getPoints;e.on("draw:editstop",(function(){i=s(a.toGeoJSON())})),e.on("draw:drawstop",(function(){i=s(a.toGeoJSON())})),e.on("draw:deletestop",(function(){i=s(a.toGeoJSON())})),this.tappe=i},filterTable(t){return console.log(t),"Point"===t.geometry.type},getPoints:function(t){if(null===t)return null;let e=t.features,a=e.filter(t=>"Point"===t.geometry.type);return this.tappe=a,a}},async beforeMount(){"tripID"in this.$route.params&&null!=this.$route.params.tripID?(this.editmode=!0,await this.loadTrip(Number(this.$route.params.tripID)),this.trip.id=Number(this.$route.params.tripID),this.mapReady=!0):(this.editmode=!1,this.mapReady=!0)}},Q=H,X=Object(h["a"])(Q,j,M,!1,null,null,null),Z=X.exports,tt=function(){var t=this,e=t._self._c;return e("div",{staticClass:"trips"},[e("div",{staticClass:"container"},[e("b-row",{staticClass:"mt-5"},[e("b-col",[e("h1",[e("strong",[t._v("I miei viaggi")])])])],1),e("b-row",{attrs:{"align-v":"top"}},[e("b-col",{attrs:{cols:"12",md:"2"}},[e("strong",[t._v("Filtra Per data:")])]),e("b-col",{attrs:{cols:"6",md:"3"}},[e("b-form-datepicker",{staticClass:"mb-2",attrs:{"date-format-options":{year:"numeric",month:"numeric",day:"numeric"}},on:{input:function(e){return t.getAllTripsByDate()}},model:{value:t.searchStartDate,callback:function(e){t.searchStartDate=e},expression:"searchStartDate"}})],1),e("b-col",{attrs:{cols:"6",md:"3"}},[e("b-form-datepicker",{staticClass:"mb-2",attrs:{"date-format-options":{year:"numeric",month:"numeric",day:"numeric"}},on:{input:function(e){return t.getAllTripsByDate()}},model:{value:t.searchEndDate,callback:function(e){t.searchEndDate=e},expression:"searchEndDate"}})],1),e("b-col",{attrs:{cols:"6",md:"2"}},[e("button",{staticClass:"btn btn-success",attrs:{type:"button"},on:{click:function(e){return t.loadTrip()}}},[e("b-icon-arrow-repeat")],1)]),e("b-col",{attrs:{cols:"6",md:"1"}},[e("router-link",{attrs:{to:{name:"trip"}}},[e("button",{staticClass:"btn btn-success",attrs:{type:"button"}},[e("b-icon-plus-lg")],1)])],1)],1),e("b-table",{staticClass:"mt-3",attrs:{hover:"",items:t.trips,fields:t.fields,busy:t.isBusy,"label-sort-asc":"","label-sort-desc":"","label-sort-clear":""},scopedSlots:t._u([{key:"table-busy",fn:function(){return[e("div",{staticClass:"text-center text-danger my-2"},[e("b-spinner",{staticClass:"align-middle"}),e("strong",[t._v("Loading...")])],1)]},proxy:!0},{key:"cell(id)",fn:function(t){return[e("router-link",{attrs:{to:{name:"trip",params:{tripID:t.value}}}},[e("button",{staticClass:"btn btn-success",attrs:{type:"button"}},[e("b-icon-pencil-square")],1)])]}},{key:"cell(tripDate)",fn:function(e){return[t._v(" "+t._s(t._f("moment")(e.value,"DD/MM/YYYY"))+" ")]}},{key:"cell(elimina)",fn:function(a){return[e("button",{staticClass:"btn btn-danger",attrs:{type:"button"},on:{click:function(e){return t.deleteTrip(a.item.id)}}},[e("b-icon-trash")],1)]}}])})],1)])},et=[],at={name:"Trips",components:{},data(){return{isLoaded:!1,fields:[{key:"id",label:"Modifica"},{key:"name",label:"Nome",sortable:!0},{key:"tripDate",label:"Data",sortable:!0},{key:"vehicle",label:"Mezzo",sortable:!0},{key:"elimina",label:"Elimina"}],trips:null,searchStartDate:null,searchEndDate:null}},methods:{loadTrip:async function(){this.isLoaded=!1;try{const t=await q.getAllTrips();200==t.status&&(this.trips=t.data,this.isLoaded=!0)}catch(t){const e=t.toJSON();401==e.status?(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})):alert(e.message)}},deleteTrip:async function(t){if(confirm("Vuoi veramente cancellare il viaggio?"))try{const e=await q.deleteTrip(t);200==e.status&&(alert("Viaggio cancellato"),await this.loadTrip())}catch(e){const t=e.toJSON();401==t.status?(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})):409==t.status?alert("Non riuscito a cancellare"):404==t.status?alert("Non trovato"):alert(t.message)}},getAllTripsByDate:async function(){if(this.searchEndDate<this.searchStartDate)return alert("Data di fine non può essere maggiore di inzio");this.isLoaded=!1;try{const t=await q.getAllTripsByDate(this.searchStartDate,this.searchEndDate);200==t.status&&(this.trips=t.data,this.isLoaded=!0)}catch(t){const e=t.toJSON();401==e.status?(alert("Token scaduto"),localStorage.removeItem("token"),localStorage.removeItem("isAuthenticated"),localStorage.removeItem("userinfo"),this.$router.push({path:"/login"})):alert(e.message)}}},mounted:async function(){await this.loadTrip()}},rt=at,ot=(a("2cd7"),Object(h["a"])(rt,tt,et,!1,null,"083f4da3",null)),it=ot.exports,st=function(){var t=this,e=t._self._c;return e("div",{staticClass:"profile"},[e("div",{staticClass:"container"},[e("b-form",[e("b-row",{staticClass:"mt-5"},[e("b-col",[e("router-link",{attrs:{to:{name:"trips"}}},[e("b-button",{attrs:{variant:"success"}},[e("b-icon-arrow-left"),t._v(" Indietro ")],1)],1)],1),e("b-col",[e("h2",[t._v("Profilo")])]),e("b-col")],1),e("b-row",{staticClass:"mt-2"},[e("b-col",[e("b-form-group",{attrs:{id:"input-group-2",label:"Nome:","label-for":"input-2"}},[e("b-form-input",{attrs:{id:"input-2",placeholder:"Inserisci nome",required:"",readonly:""},model:{value:t.form.firstname,callback:function(e){t.$set(t.form,"firstname",e)},expression:"form.firstname"}})],1)],1),e("b-col",[e("b-form-group",{attrs:{id:"input-group-2",label:"Cognome:","label-for":"input-2"}},[e("b-form-input",{attrs:{id:"input-2",placeholder:"Inserisci cognome",required:"",readonly:""},model:{value:t.form.lastname,callback:function(e){t.$set(t.form,"lastname",e)},expression:"form.lastname"}})],1)],1)],1),e("b-row",{staticClass:"mt-2"},[e("b-col",[e("b-form-group",{attrs:{id:"input-group-1",label:"Username","label-for":"input-1"}},[e("b-form-input",{attrs:{id:"input-1",type:"email",placeholder:"username",required:"",readonly:""},model:{value:t.form.username,callback:function(e){t.$set(t.form,"username",e)},expression:"form.username"}})],1)],1),e("b-col",[e("b-form-group",{attrs:{id:"input-group-1",label:"Email address:","label-for":"input-1"}},[e("b-form-input",{attrs:{id:"input-1",type:"email",placeholder:"Inserisci email",required:"",readonly:""},model:{value:t.form.email,callback:function(e){t.$set(t.form,"email",e)},expression:"form.email"}})],1)],1)],1)],1)],1)])},nt=[],lt={name:"Profile",components:{},data(){return{form:{firstname:"firstname",lastname:"lastname",email:"email",username:"username",oldpassword:null,password:null,role:"admin"}}},methods:{},mounted:function(){const t=JSON.parse(JSON.parse(localStorage.getItem("userinfo")));this.form.firstname=t.firstname,this.form.lastname=t.lastname,this.form.email=t.email,this.form.username=t.username,this.form.role=t.role,console.log(t.username)}},ct=lt,ut=Object(h["a"])(ct,st,nt,!1,null,null,null),mt=ut.exports;const pt=(t,e,a)=>{if(null===localStorage.getItem("isAuthenticated"))a("/login");else{const t=localStorage.getItem("isAuthenticated");if("true"==t)return a()}};r["default"].use(I["a"]),r["default"].use(C["a"],n.a);const dt=[{path:"/",name:"Home",component:it,beforeEnter:pt,meta:{transition:"fade"}},{path:"/profile",name:"Profile",component:mt,beforeEnter:pt},{path:"/signup",name:"signup",component:A},{path:"/login",name:"login",component:L},{path:"/trip",name:"trip",component:Z,beforeEnter:pt,meta:{transition:"slide-right"}},{path:"/trip/:tripID",name:"trip",component:Z,beforeEnter:pt,meta:{transition:"slide-right"}},{path:"/trips",name:"trips",component:it,beforeEnter:pt,meta:{transition:"slide-right"}}],ft=new I["a"]({mode:"history",base:"/ProgrammazioneWebMattiaBressanEsame/",routes:dt});var bt=ft,gt=a("5f5b"),ht=a("b1e0"),vt=a("f9bc"),yt=(a("2dd8"),a("f9e3"),a("4e2b"));a("6cc5"),a("5fbf");r["default"].use(a("2ead")),r["default"].use(gt["a"]),r["default"].use(ht["a"]),r["default"].use(vt["a"]),r["default"].component("l-map",U["a"]),r["default"].component("l-tile-layer",B["a"]),r["default"].component("l-marker",yt["a"]),delete F["Icon"].Default.prototype._getIconUrl,F["Icon"].Default.mergeOptions({iconRetinaUrl:a("584d"),iconUrl:a("6397"),shadowUrl:a("e2b9")}),r["default"].config.productionTip=!1,new r["default"]({router:bt,render:t=>t(k)}).$mount("#app")},"64f9":function(t,e,a){},"8da9":function(t,e,a){"use strict";a("64f9")},be58:function(t,e,a){},c64b:function(t,e,a){"use strict";a("5478")},cb99:function(t,e,a){},cf05:function(t,e,a){t.exports=a.p+"img/logo.339b276a.png"},db49:function(t,e){const a={local:"http://localhost:8080/ProgrammazioneWebMattiaBressanEsame/api"};t.exports={backend_url:a.local}}});
//# sourceMappingURL=app.7ad1691b.js.map