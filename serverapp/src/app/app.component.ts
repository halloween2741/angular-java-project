import { Component, OnInit } from '@angular/core';
import { ServerService } from './service/server.service';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { DataState } from './enum/data.state.enum';
import { Server } from './interface/server';
import { StudentService } from './service/student.service';
import { Student } from './interface/student';
import { Levels } from './enum/levels.enum';
import { SelectComponent } from './components/select/select.component';
import { InputNumberComponent } from './components/input-number/input-number.component';
import { ButtonComponent } from './components/button/button.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  appState$: Observable<AppState<CustomResponse>>;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  selectedData$: Server = null;
  constructor(private serverService: ServerService, private studentService: StudentService) {}

  readonly framework: object = {
    isPreply: ButtonComponent,
    level: SelectComponent,
    numPaidClasses: InputNumberComponent,
  };

  readonly columns: string[] = ['name', 'isPreply', 'level', 'progressInfo', 'objectivesInfo', 'nextClassInfo', 'hobbiesInfo', 'numPaidClasses'];

  columnDefs = this.columns.map((columnId) => (
    { field: columnId,
      cellRenderer: this.framework[columnId] ? columnId : null,
      editable: !this.framework[columnId],
      flex: 1,
      sortable: true,
      filter: true,
      onCellValueChanged: this.onCellValueChanged.bind(this),
      tooltipField: columnId,
    }
  ));

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
    const selectedNode = param1.api.getSelectedNodes()[0];
    this.selectedData$ = selectedNode ? selectedNode.data : null;
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
    this.selectedData$ = null;
    this.appState$ = this.studentService.delete$(id).pipe(map(response => {
      this.dataSubject.next({ ...response, data: { students: this.dataSubject.value.data.students.filter((student) => student.id !== id) } });
      return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
    }));
  }

  save(): void {
    this.selectedData$ = null;
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
      const students: Student[] = [...this.dataSubject.value.data.students];
      students.push(response.data.student);
      this.dataSubject.next({ ...response, data: { students } });
      return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value };
    }));
  }
}
