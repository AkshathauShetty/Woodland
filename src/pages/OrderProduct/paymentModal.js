import { Col, Modal, Row } from "react-bootstrap"
import { errorMessage, successMessage } from "../../components/methods/computations"
import { useNavigate } from "react-router-dom"
import '../../assets/styles/paymentModal.css'

const PaymentModal=(props)=>{
    const navigate = useNavigate()

    return(
        <>
            <Modal 
            show={props.show}
            >
                <Modal.Header>
                <Modal.Title className="payment_modal">Select Payment Method</Modal.Title>
                </Modal.Header>
                <Modal.Body style={{padding:"0px"}}> 
                <Row style={{margin:"0px"}}>
                    <Col className="col_style" xs={12} sm={6}>
                    <div
                     className="cash-del"
                    onClick={()=>{
                        props.paymentType("cash")
                        props.handleClose()
                    }}
                    >Pay Cash On Delivery</div>
                    </Col>
                    <Col className="col_style col_bg" xs={12} sm={6}
                    >
                    <div
                     className="online-del"
                    onClick={()=>{
                        props.paymentType("online")
                        props.handleClose()
                    }
                    }
                    > Pay online </div>
                    </Col>
                </Row>
                </Modal.Body>
                <Modal.Footer>
                </Modal.Footer>
      </Modal>
        </>
    )
}

export default PaymentModal