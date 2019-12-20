import {Component, OnInit} from '@angular/core';
import {CollegeService} from '../../services/college.service';
import { ViewChild } from '@angular/core';
import { } from 'googlemaps';
import {LatLong} from "../../models/LatLong";

declare var ol: any;
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {
  map: any;
  // google.maps.Map;
  // @ViewChild('map', { static: false }) gmapElement: any;

  zip: number;
  distance: number;
  predominant: string;
  year: number;
  responseData: any;
  perPage: number = 20;
  pages: number = 0;
  page: number = 0;
  total: number = 0;

  latitude: any = 41.022174;
  longitude: any = -91.964884;



  // iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';

  constructor(private collegerService: CollegeService) { }


  selectedMarkerType: string = 'parking_lot_maps.png';

  ngOnInit() {
    const mousePositionControl = new ol.control.MousePosition({
      coordinateFormat: ol.coordinate.createStringXY(4),
      projection: 'EPSG:4326',
      // comment the following two lines to have the mouse position
      // be placed within the map.
      className: 'custom-mouse-position',
      target: document.getElementById('mouse-position'),
      undefinedHTML: '&nbsp;'
    });


    this.map = new ol.Map({
      target: 'map',
      controls: ol.control.defaults({
        attributionOptions: {
          collapsible: false
        }
      }).extend([mousePositionControl]),
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        })
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([this.longitude, this.latitude]),
        zoom: 7
      })
    });

    this.map.on('click', function (args) {
      console.log(args.coordinate);
      var lonlat = ol.proj.transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
      console.log(lonlat);

      var lon = lonlat[0];
      var lat = lonlat[1];
      alert(`lat: ${lat} long: ${lon}`);
    });
  }

  onSubmit() {
    this.onPage(0);
    this.collegerService.getLatLong(this.zip).subscribe(x => {
      this.latitude = x.lat;
      this.longitude = x.lng;
    });
    this.setCenter();
  }

  onNext(){
    this.page++;
    this.onPage(this.page);
  }

  onPrev() {
    this.page--;
    this.onPage(this.page);
  }

  onPage(pg: number) {
    console.log('search...' + pg);
    const criteria = {
      zip: this.zip,
      distance: this.distance,
      predominant: this.predominant,
      year: this.year,
      page: pg,
      perPage: this.perPage
    }

    this.collegerService.search(criteria).subscribe((data) => {
      console.log(data.metaData.total);
      console.log(data.metaData.perPage);
      this.responseData = data;
      this.pages = Math.ceil(data.metaData.total / data.metaData.perPage);
      this.perPage = data.metaData.perPage;
      this.total = data.metaData.total;
    });

  }

  // showMap(){
  //   this.map = new ol.Map({
  //     target: 'map',
  //     controls: ol.control.defaults({
  //       attributionOptions: {
  //         collapsible: false
  //       }
  //     }).extend(),
  //     layers: [
  //       new ol.layer.Tile({
  //         source: new ol.source.OSM()
  //       })
  //     ],
  //     view: new ol.View({
  //       center: ol.proj.fromLonLat([this.longitude, this.latitude]),
  //       zoom: 8
  //     })
  //   });
  // }

  // googleMap() {
  //   const mapProp = {
  //     center: new google.maps.LatLng(this.latitude, this.longitude),
  //     zoom: 15,
  //     mapTypeId: google.maps.MapTypeId.ROADMAP
  //   };
  //   this.map = new google.maps.Map(this.gmapElement.nativeElement, mapProp);
  // }

  setCenter() {
    var vectorLayer = new ol.layer.Vector({
      source:new ol.source.Vector({
        features: [new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.fromLonLat([this.longitude, this.latitude])),//, 'EPSG:3857',  'EPSG:4326')),
        })]
      }),
      style: new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 0.5],
          anchorXUnits: "fraction",
          anchorYUnits: "fraction",
          src: "https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg"
        })
      })
    });
    this.map.addLayer(vectorLayer);


    var view = this.map.getView();
    view.setCenter(ol.proj.fromLonLat([this.longitude, this.latitude]));
    // var lonlat = ol.proj.fromLonLat([this.longitude, this.latitude]);
    // var markers = new ol.Layer.Markers( "Markers" );
    // this.map.addLayer(markers);
    // markers.addMarker(new ol.Marker(lonlat));
    view.setZoom(7);
  }

}
