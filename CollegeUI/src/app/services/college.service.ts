import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {ResponseData} from '../models/ResponseData';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Criteria} from '../models/Criteria';
import {LatLong} from "../models/LatLong";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class CollegeService {
  url: string = 'http://localhost:8080/schools?';
  para: string = 'predominant=2,3&zip=52557&distance=10&year=2017';

  responseData: any ;

  constructor(private http: HttpClient) {
    this.responseData = this.http.get<ResponseData>(`${this.url}${this.para}`, httpOptions);
  }

  search(criteria: Criteria): Observable<ResponseData> {
    // console.log(`${this.url}
    //   &predominant=${criteria.predominant.trim()}
    //   &zip=${criteria.zip}
    //   &distance=${criteria.distance}
    //   &page=${criteria.page}
    //   &perPage=${criteria.perPage}
    //   &year=${criteria.year}`);
    this.responseData = this.http.get<ResponseData>(`${this.url}&predominant=${criteria.predominant.trim()}&zip=${criteria.zip}&distance=${criteria.distance}&page=${criteria.page}&perPage=${criteria.perPage}&year=${criteria.year}`, httpOptions);

    return this.responseData;
  }

  setResponseData(obj: any){
    this.responseData = obj;
  }

  public getResponseData(): ResponseData {
    return this.responseData;
  }

  getLatLong(zip: number): Observable<LatLong> {
    //'https://www.zipcodeapi.com/rest/POYCKK9rDtahKxrvZNCnzyXyTK2B6P4WRBLPKJJVjLAnxoajrVHa4JVxAA2hRRoN/info.json/52557/degrees'
    const zipApi = 'https://www.zipcodeapi.com/rest/POYCKK9rDtahKxrvZNCnzyXyTK2B6P4WRBLPKJJVjLAnxoajrVHa4JVxAA2hRRoN/info.json/'+zip+'/degrees';
    console.log(zipApi);
    return this.http.get<LatLong>(`https://www.zipcodeapi.com/rest/POYCKK9rDtahKxrvZNCnzyXyTK2B6P4WRBLPKJJVjLAnxoajrVHa4JVxAA2hRRoN/info.json/'${zip}'/degrees`, httpOptions);
  }
}
