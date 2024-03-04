import React, { useEffect, useState } from "react"
import { Button, Col, Row } from "react-bootstrap"
import useWindowsizes from "../../components/Windowsize";
import Address from "../../components/components/adress";
import useAuthGetrequests from "../../components/customRequests/getreqAuth";
import AddressContainer from "./addressContainer";


const MyAddress=()=>{

    const {isSmall,setSmall}=useWindowsizes();
    const [showInputs,setShowInputs]=useState(false);
    const [displayAddress,setDisplayAddress]=useState(true);
    const [savedAddress,setSavedAddress] = useAuthGetrequests('getAddress',displayAddress)
    const [addressid,setAddressId]=useState(0);
    const [address,setAddress]=useState(null);

    useEffect(()=>{
      if(showInputs==true){
        setDisplayAddress(true)
      }
    },[showInputs])



    return (
      <React.Fragment>
        <Row>
          <Col xs={12} style={{ paddingBottom: "10px" }}>
            <b>Address</b>
          </Col>
          <Col sm={12} lg={6}>
            <Row>
              <Col xs={12}>
                <Button
                  onClick={() => setShowInputs(!showInputs)}
                  style={{
                    backgroundColor: "transparent",
                    border: "1px solid #ccc",
                    color: "black",
                    borderRadius: "0px",
                    padding: "2%",
                  }}
                  className="profile_buttons"
                >
                  + ADD A NEW ADDRESS
                </Button>
              </Col>
              <Col>
                {showInputs ? (
                  <React.Fragment>
                    <Row>
                      <Col
                        style={{
                          fontSize: "16px",
                          fontWeight: "600",
                          paddingTop:"3%",
                          paddingBottom: "3%",
                        }}
                        className="shipping_address_header"
                        xs={12}
                        lg={8}
                      >
                        New Shipping Address
                      </Col>
                    </Row>
                    <Row>
                      <Address cancel={setShowInputs} />
                    </Row>
                  </React.Fragment>
                ) : (
                  ""
                )}
              </Col>
            </Row>
          </Col>

          <Col sm={12} lg={6}>
            {console.log("savedaddinadd", savedAddress)}
            <Row>
              {savedAddress != null && savedAddress.length > 0 && displayAddress
                ? savedAddress.map((address) => {
                    return (
                      <Col lg={6} sm={6}>
                        <AddressContainer
                          address={address}
                          display={setDisplayAddress}
                          setAddress={setAddress}
                        />
                      </Col>
                    );
                  })
                : ""}

                {
                  !displayAddress && address!=null?
                  <Address
                    address={address}
                    displayAddress={setDisplayAddress}
                  />
                  :
                  ''
                }
            </Row>
          </Col>
        </Row>
      </React.Fragment>
    );
}
   
export default  MyAddress