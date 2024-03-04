import React, { useEffect, useRef, useState } from "react"
import { ACTION } from "../variables/variables"
import { Button, Col, Row } from "react-bootstrap"
import { errorMessage, successMessage, validateInput } from "../methods/computations"
import useSaveaddressrequest from "../customRequests/saveAddressRequest"
import { useNavigate } from "react-router-dom"


const Address=(props)=>{
    
    const [inputs, setinputDisable]=useState(true)
    const [address,setAddress] = useState(null);
    const [saveadd,setSaveadd] = useState(false)
    const [savedAddress,setSavedAddress] = useSaveaddressrequest(address,saveadd,'/saveAddress');
    const [editAddress,setEditAddress] = useState(false)
    const [editedAddress,setEditedAddress] = useSaveaddressrequest(address,editAddress,'/editAddress');
    const navigate = useNavigate();

    useEffect(()=>{
      if(editedAddress!=null){
        props.displayAddress(true)
      }
    })

    useEffect(()=>{
      if(props.address!=null){
        setFields();
      }
      },[props])

    useEffect(()=>{
        if(savedAddress!=null){
         //   console.log('',saveAddress)
            successMessage('address saved successfully')
            props.cancel(false);
        }
    })

    const firstnameInput = useRef(null)
    const lastnameInput = useRef(null)
    const emailnameInput = useRef(null)
    const addressLineInput = useRef(null)
    const pincodeInput = useRef(null)
    const cityInput = useRef(null)
    const stateInput = useRef(null)
    const landmarkInput = useRef(null)
    const addTypeInput = useState(null)

    const disableInputs=(value)=>{
        setinputDisable(value)
    }

    const updateInputs=(e,toUpdate)=>{

        switch(toUpdate){
            case ACTION.FIRST_NAME:firstnameInput.current.value=e.target.value;break;
            case ACTION.LAST_NAME:lastnameInput.current.value=e.target.value;break; 
            case ACTION.EMAIL:emailnameInput.current.value=e.target.value;break; 
            case ACTION.ADDLINE : addressLineInput.current.value=e.target.value;break;
            case ACTION.PINCODE:pincodeInput.current.value=e.target.value;break; 
            case ACTION.CITY :cityInput.current.value=e.target.value;break; 
            case ACTION.STATE : stateInput.current.value=e.target.value;break;
            case ACTION.LAND :landmarkInput.current.value=e.target.value;break; 
            case ACTION.ADDTYPE:addTypeInput.current.value=e.target.value;break; 
            default:return;
        }
    }

    const validateFiels=()=>{
         if(!validateInput("pincode",pincodeInput.current.value)){
            errorMessage("Invalid pincode entered"+pincodeInput.current.value);
        }
        else  if(!validateInput("name",stateInput.current.value)){
            errorMessage("Invalid state entered");
        }
        else  if(!validateInput("name",cityInput.current.value)){
            errorMessage("Invalid city entered");
        }
        else  if(addressLineInput.current.value==""){
            errorMessage("Invalid address entered");
        }
        else{
            //.log(addTypeInput.current.value)
            saveAddress()
        }
    }

    const saveAddress=()=>{
      if(props.address!=null){
        const newAddress= {
          "addressId":props.address.addressId,
          "addressLine":addressLineInput.current.value,
          "pincode":pincodeInput.current.value,
          "city":cityInput.current.value,
          "state":stateInput.current.value,
          "landmark":landmarkInput.current.value,
          "type":addTypeInput.current.value
         }
         setAddress(newAddress);
         setEditAddress(true)
      }
      else{
        const newAddress= {
          "addressLine":addressLineInput.current.value,
          "pincode":pincodeInput.current.value,
          "city":cityInput.current.value,
          "state":stateInput.current.value,
          "landmark":landmarkInput.current.value,
          "type":addTypeInput.current.value
         }
       // console.log('new address printing....',newAddress)
        setAddress(newAddress);
        setSaveadd(true)  
        }      
    }

    const setShow=()=>{
      if(props.address!=null){
        props.displayAddress(true)
      }
      else{
        props.cancel(false);
      }
    }

    const setFields=()=>{
      addressLineInput.current.value=props.address.addressLine
      pincodeInput.current.value = props.address.pincode
      cityInput.current.value = props.address.city
      stateInput.current.value = props.address.state 
      landmarkInput.current.value = props.address.landmark
      addTypeInput.current.value = props.address.type
    }

    return (
      <React.Fragment>
        <Row>
          <Col xs={12} lg={12}>
            <Row>
              <Col className="profile_fields" xs={12} lg={12}>
                <input
                  style={{
                    height: "110px",
                  }}
                  onChange={(e) => {
                    updateInputs(e, "addressLine");
                  }}
                  type="text"
                  required
                  placeholder="Address*"
                  ref={addressLineInput}
                  className="profile_input"
                />
              </Col>

              <Col xs={12} lg={12}>
                <Row>
                  <Col className="profile_fields" xs={12} lg={6}>
                    <input
                      style={{
                        paddingBottom: "5%",
                        paddingTop: "5%",
                      }}
                      onChange={(e) => {
                        updateInputs(e, "pincode");
                      }}
                      type="text"
                      maxLength={6}
                      required
                      placeholder="Pincode*"
                      ref={pincodeInput}
                      className="profile_input"
                    />
                  </Col>

                  <Col className="profile_fields" xs={12} lg={6}>
                    <input
                      onChange={(e) => {
                        updateInputs(e, "city");
                      }}
                      type="text"
                      required
                      placeholder="City/District/Town*"
                      ref={cityInput}
                      className="profile_input"
                    />
                  </Col>
                </Row>
              </Col>

              <Col className="profile_fields" xs={12}>
                <input
                  onChange={(e) => {
                    updateInputs(e, "state");
                  }}
                  type="text"
                  required
                  placeholder="State*"
                  ref={stateInput}
                  className="profile_input"
                />
              </Col>

              <Col className="profile_fields" xs={12} lg={6}>
                <input
                  onChange={(e) => {
                    updateInputs(e, "landmark");
                  }}
                  type="text"
                  required
                  placeholder="Landmark (Optional)"
                  ref={landmarkInput}
                  className="profile_input"
                />
              </Col>
            </Row>
          </Col>
        </Row>

        <Row className="order_check_box">
          <Col xs={12} sm={5} md={3}>
            <input
              className="add_type_chekbox"
              onClick={(e) => {
              //  console.log(addTypeInput, addTypeInput.current.value);
                updateInputs(e, "addTypeInput", e.target.checked);
              }}
              ref={addTypeInput}
              type="radio"
              id="home"
              name="addType"
              value="home"
              checked
            />
            &nbsp;
            <label className="add_type_label" for="home">
              Home
            </label>
            &nbsp;
          </Col>
          <Col xs={12} sm={7} md={5}>
            <input
              className="add_type_chekbox"
              onClick={(e) => {
                updateInputs(e, "addTypeInput", e.target.checked);
              }}
              ref={addTypeInput}
              type="radio"
              id="work"
              name="addType"
              value="work"
            />{" "}
            &nbsp;
            <label className="add_type_label" for="work">
              Work
            </label>
            &nbsp;
          </Col>
        </Row>
        
        <Row style={{paddingTop:"10px"}}>
          <Row className="justify-content-end">
            <Col xs={3}>
              <Button
                onClick={() => {

                  setShow()
                }}
                style={{
                  backgroundColor: "rgb(48,48,48)",
                }}
                className="profile_buttons cancel_Button"
              >
                Cancel
              </Button>
            </Col>
            <Col xs={3}>
              <Button
                onClick={() => {
                  validateFiels();
                }}
                style={{
                  backgroundColor: "rgb(59,111,68)",
                }}
                className="profile_buttons save_Button"
              >
                Save
              </Button>
            </Col>
          </Row>
        </Row>
      </React.Fragment>
    );
}

export default Address