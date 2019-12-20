import {Component, Input, OnInit} from '@angular/core';
import {CollegeService} from '../../services/college.service';
import {ResponseData} from '../../models/ResponseData';
import {Result} from "../../models/Result";
import {Criteria} from "../../models/Criteria";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
  @Input() criteria: Criteria;
  // results: Result[];
  // summary: MetaData;
  response: any;

  constructor(private collegeService: CollegeService) { }

  ngOnInit() {
    this.response = this.collegeService.getResponseData();
  }

  // search(criteria: Criteria ) {
  //   console.log(ResponseData);
  //   this.collegeService.search(criteria).subscribe(res => {
  //     this.response = res;
  //     console.log(res);
  //   }
  //   );
  // }
  // setResponseData(Response)

}
