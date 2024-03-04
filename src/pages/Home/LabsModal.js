import React from "react"
import { Col, Container, Modal, ModalFooter, Row } from "react-bootstrap"
import '../../assets/styles/labsModal.css'

const LabsModal=(props)=>{

    const header_names =['type','field_name','old_value','new_value','modified_by','modified_on']

    const data ={
        
    }

    return(        
    <React.Fragment>
        <Modal
        className="labsmodal_container"
        {...props}
        backdrop="static"
        size="lg"
        >
            <Modal.Header closeButton>
            <Modal.Title className="labsmodal_title">
                <input  type="checkbox"/> I Agree
                {/* <a  className ="labsmodal_headerlink" href="#">Click here</a> */}
            </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Container>
                    <Row className="labsmodal_row">
                        {
                            header_names.map((item)=>{
                            return(
                                <Col className="labsmodal_header">
                                    {item}
                                </Col>
                                )
                            })
                        }
                    </Row>
                </Container>
            </Modal.Body>
            <ModalFooter 
            className="labsmodal_footer"
            onClick={()=>{
                props.handleModal(false)
            }}>
                OK
            </ModalFooter>
        </Modal>
        </React.Fragment>
    )
}


export default LabsModal