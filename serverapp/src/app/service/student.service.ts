import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CustomResponse } from '../interface/custom-response';
import { catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Student } from '../interface/student';
import { environment } from '../../environments/environment';
import { CustomResponseAGGrid } from '../interface/custom-response-aggrid';
import { AGGrid } from '../interface/aggrid';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private readonly apiUrl = environment.production ? 'https://students-java-server.herokuapp.com' : 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  students$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/server/listStudents`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  agGridInfo$ = () => <Observable<CustomResponseAGGrid>>
    this.http.get<CustomResponseAGGrid>(`${this.apiUrl}/server/agGridInfo`).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  saveAGGridInfo$ = (aggrid: AGGrid) => <Observable<CustomResponseAGGrid>>
    this.http.patch<CustomResponseAGGrid>(`${this.apiUrl}/server/updateAGGridInfo`, aggrid).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  save$ = (student: Student) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/server/saveStudent`, student).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  update$ = (student: Student) => <Observable<CustomResponse>>
    this.http.patch<CustomResponse>(`${this.apiUrl}/server/updateStudent`, student).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  delete$ = (studentId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/server/deleteStudent/${studentId}`).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError (`An error occurred - Error code: ${error.status}`);
  }
}
