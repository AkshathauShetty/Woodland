import { AgGridReact } from 'ag-grid-react'; // React Grid Logic
import "ag-grid-community/styles/ag-grid.css"; // Core CSS
import "ag-grid-community/styles/ag-theme-quartz.css";
import { useState } from 'react';
import { Modal } from 'react-bootstrap';

const LabsGridModal=()=>{

  const header_names =['type','field_name','old_value','new_value','modified_by','modified_on']

  const [date,setDate] = useState(new Date());
    
    const [rowData, setRowData] = useState([
        { type: "Tesla", field_name: "old_value", new_value: 64950, modified_by: 'robin',modified_on:date },
        { type: "Ford", field_name: "F-Series", new_value: 33850, modified_by: 'akshatha' ,modified_on:''},
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'emma',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'daniel',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'dan',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'sam',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'troy',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'sivan',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'james',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'clear',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'eddie',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'edward',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'sam',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'joe',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'malfoy',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'lewis',modified_on:'' },
        { type: "Toyota", field_name: "Corolla", new_value: 29600, modified_by: 'abv',modified_on:'' }
      ]);
      
      // Column Definitions: Defines & controls grid columns.
      const [colDefs, setColDefs] = useState([
        { field: "type" },
        { field: "field_name" },
        { field: "old_value" },
        { field: "new_value" },
        { field: "modified_by" },
        { field: "modified_on" }
      ]);
    
    return(
        <React.Fragment>
        <Modal>
        
          <AgGridReact rowData={rowData} columnDefs={colDefs} />
        </Modal>
            
        </React.Fragment>
    )
}

export default LabsGridModal