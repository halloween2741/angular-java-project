import { Component, OnInit } from '@angular/core';
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
import { CustomResponseAGGrid } from './interface/custom-response-aggrid';
import { AGGrid } from './interface/aggrid';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  agGridInfo$: Observable<AppState<CustomResponseAGGrid>>;
  appState$: Observable<AppState<CustomResponse>>;
  private dataSubjectAGGridInfo = new BehaviorSubject<CustomResponseAGGrid>(null);
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  selectedData$: Server = null;
  constructor(private studentService: StudentService) {}

  readonly framework: object = {
    isPreply: ButtonComponent,
    level: SelectComponent,
    numPaidClasses: InputNumberComponent,
  };

  columns$: object[] = null;

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

  onSelectionChanged(aggrid) {
    const selectedNode = aggrid.api.getSelectedNodes()[0];
    this.selectedData$ = selectedNode ? selectedNode.data : null;
  }

  onColumnMoved(agGrid) {
    let columnsString = '';
    agGrid.api.getColumnDefs().forEach(data => {
      if (columnsString.length > 0) { columnsString += ','; }
      columnsString += data.field;
    });

    const newAGGrid: AGGrid = {
      id: 0,
      columns: columnsString
    };
    newAGGrid.columns = columnsString;


    this.agGridInfo$ = this.studentService.saveAGGridInfo$(newAGGrid).pipe(map(response => {
      this.dataSubjectAGGridInfo.next(response);
      this.columns$ = this.getColumns(response.data.agGrid.columns);
      return { dataState: DataState.LOADED_STATE, appData: response };
    }));
  }

  getColumns(columnsBack: string) {
    return columnsBack.split(',').map((columnId) => (
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

  }

  ngOnInit(): void {
    this.agGridInfo$ = this.studentService.agGridInfo$().pipe(map(response => {
      this.dataSubjectAGGridInfo.next(response);
      this.columns$ = this.getColumns(response.data.agGrid.columns);
      return { dataState: DataState.LOADED_STATE, appData: response };
    }), catchError((error: string) => {
      return of({ dataState: DataState.ERROR_STATE, error });
    }));

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
