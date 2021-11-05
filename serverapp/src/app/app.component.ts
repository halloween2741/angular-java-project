import { Component, OnInit } from '@angular/core';
import { ServerService } from './service/server.service';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { DataState } from './enum/data.state.enum';
import { Server } from './interface/server';
import { Status } from './enum/status.enum';
import { StudentService } from './service/student.service';
import { Student } from './interface/student';
import { Levels } from './enum/levels.enum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  selectedData$: Server = null;
  constructor(private serverService: ServerService, private studentService: StudentService) {}

  readonly columns: string[] = ['name', 'isPreply', 'level', 'progressInfo', 'objectivesInfo', 'nextClassInfo', 'hobbiesInfo', 'numPaidClasses'];

  columnDefs = this.columns.map((columnId) => ({ field: columnId, editable: true, sortable: true, filter: true, onCellValueChanged: this.onCellValueChanged.bind(this) }));

  public onCellValueChanged(params) {
    const { data } = params;
    const dataUpdated: Student = data;
    this.appState$ = this.studentService.update$(dataUpdated).pipe(map(response => {
      this.dataSubject.next({ ...response, data: { students: this.dataSubject.value.data.students.map((row) => row.id === data.id ? data : row) } });
      return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
    }), catchError((error: string) => {
      return of({ dataState: DataState.ERROR_STATE, error });
    }));
  }

  onSelectionChanged(param1) {
    this.selectedData$ = param1.api.getSelectedNodes()[0].data;
  }

  ngOnInit(): void {
    this.appState$ = this.studentService.students$.pipe(map(response => {
      this.dataSubject.next(response);
      return { dataState: DataState.LOADED_STATE, appData: response };
    }), startWith({ dataState: DataState.LOADING_STATE }), catchError((error: string) => {
      return of({ dataState: DataState.ERROR_STATE, error });
    }));
  }

  public delete(): void {
    const { id } = this.selectedData$;
    this.appState$ = this.studentService.delete$(id).pipe(map(response => {
      this.dataSubject.next({ ...response, data: { students: this.dataSubject.value.data.students.filter((student) => student.id !== id) } });
      return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
    }));
  }

  save(): void {
    const initialStudentInfo: Student = {
      name: '',
      isPreply: true,
      level: Levels.A1,
      progressInfo: '',
      objectivesInfo: '',
      nextClassInfo: '',
      hobbiesInfo: '',
      numPaidClasses: 0
    };
    this.appState$ = this.studentService.save$(initialStudentInfo).pipe(map(response => {
      this.dataSubject.next({ ...response, data: { students: [response.data.student, ...this.dataSubject.value.data.students] } });
      return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
    }));
  }
}
