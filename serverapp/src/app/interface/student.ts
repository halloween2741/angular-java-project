import { Levels } from '../enum/levels.enum';

export interface Student {
  id?: number;
  name: string;
  isPreply: boolean;
  level: Levels;
  progressInfo: string;
  objectivesInfo: string;
  nextClassInfo: string;
  hobbiesInfo: string;
  numPaidClasses: number;
}
