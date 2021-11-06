import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CustomResponse } from '../interface/custom-response';
import { catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Server } from '../interface/server';
import { Status } from '../enum/status.enum';
import { Student } from '../interface/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private readonly apiUrl = 'https://students-java-server.herokuapp.com';

  constructor(private http: HttpClient) { }

  students$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/server/listStudents`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  save$ = (student: Student) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/server/saveStudent`, student).pipe(
      tap(console.log),
      catchError(this.handleError)
    )

  update$ = (student: Student) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/server/updateStudent`, student).pipe(
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
