import { Button, Modal } from "react-bootstrap"
import { useNavigate } from "react-router-dom"


const SuccessModal = (props)=>{

    const navigate = useNavigate()
    return(
        <>
        
             <Modal show={props.show} >
             {/* {
                console.log(props,"insie the modal")
             } */}
                <Modal.Header closeButton>
                <Modal.Title>ORDER Status</Modal.Title>
                </Modal.Header>
                <Modal.Body> Order placed Successfully</Modal.Body>
                <Modal.Footer>
                <Button 
                variant="secondary" 
                onClick={()=>{
                    props.handleClose()
                    navigate('/myOrders')
                    window.scrollTo(0,0)
                    }
                }>
                    OK
                </Button>
               
            </Modal.Footer>
      </Modal>
        </>
    )

}

export default SuccessModal