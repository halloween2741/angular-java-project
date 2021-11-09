import { Component, Output, EventEmitter } from '@angular/core';
import { AgRendererComponent } from '@ag-grid-community/angular';
import { ICellRendererParams } from '@ag-grid-community/all-modules';
import { Levels } from '../../enum/levels.enum';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})

export class SelectComponent implements AgRendererComponent {
  private agGridParams: any;
  value: string;
  private field: string;
  options: string[];
  private optionsFields = {
    isPreply: [{ value: true, viewValue: 'yes' }, { value: false, viewValue: 'no' }],
    level:  Object.values(Levels)
  };

  selectValueUpdated(selectElem) {
    this.agGridParams.setValue(selectElem.value);
  }

  agInit(params: ICellRendererParams): void {
    this.agGridParams = params;
    const { colDef: { field }, value } = params;
    this.field = field;
    this.value = value;
    this.options = this.optionsFields[field];
  }

  refresh(params: ICellRendererParams): boolean {
    return false;
  }
}
