import { Component } from '@angular/core';
import { AgRendererComponent } from '@ag-grid-community/angular';
import { ICellRendererParams } from '@ag-grid-community/all-modules';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements AgRendererComponent {

  private agGridParams: any;
  value: string;
  private field: string;
  constructor() { }

  onClick() {
    this.agGridParams.setValue(!this.value);
  }

  agInit(params: ICellRendererParams): void {
    this.agGridParams = params;
    const { colDef: { field }, value } = params;
    this.field = field;
    this.value = value;
  }

  refresh(params: ICellRendererParams): boolean {
    return false;
  }

}
