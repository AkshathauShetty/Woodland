import React, { useState } from "react"
import { Card, Col, Row } from "react-bootstrap"
import "../../assets/styles/addressContainer.css"
import useAuthGetrequests from "../../components/customRequests/getreqAuth"

const AddressContainer=(props)=>{

    return(
        <React.Fragment>
        {
            props.address!=null?
            <div className="addres_holder">
                <Row>
                    <Col xs={12}>
                        <Row className="address_top">
                            <Col className="address_type" xs={4}>
                            <span>{
                                props.address.type!=null?
                                props.address.type
                                :
                                ''
                                }
                            </span>
                            </Col>
                            <Col className="address_edit" xs={7}>
                                <span
                                onClick={()=>{
                                    props.setAddress(props.address)
                                    props.display(false)
                                }}
                                >Edit</span>
                            </Col>
                        </Row>
                    </Col>
                   
                    <Col xs={12}>
                            <span>{props.address.addressLine},</span>
                            <span>{props.address.city},</span>
                            <span>{props.address.state},</span>
                    </Col>
                    <Col xs={12}>
                            <span>Pincode:</span>
                            <span>{props.address.pincode}</span>
                    </Col>
                    <Col className="addressRemove" xs={12}>
                        Remove
                    </Col>
                  
                </Row>
            </div>
            :
            ''
        }
         
        </React.Fragment>
    )
}

export default AddressContainer