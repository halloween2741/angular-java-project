import { Student } from './student';

export interface CustomResponseAGGrid {
  timeStamp: Date;
  statusCode: number;
  status: string;
  reason: string;
  message: string;
  developerMessage: string;
  data: { agGrid: { columns: string } };
}
