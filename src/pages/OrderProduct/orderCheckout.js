import { Button, Col, Form, Row } from "react-bootstrap"
import React, { useCallback, useEffect, useMemo, useRef, useState } from "react"
import '../../assets/styles/orderCheckut.css'
import { ACTION } from "../../components/variables/variables"
import CartProduct from "../Cart/cartProduct"
import useGetcart from "../../components/customRequests/getCart"

import {computeTotal, computeItems, errorMessage, successMessage, updatedPrice, validateInput } from "../../components/methods/computations"
import useSaveaddressrequest from "../../components/customRequests/saveAddressRequest"
import usePostorders from "../../components/customRequests/postOrders"
import { useNavigate } from "react-router-dom"
import useDeleteCart from "../../components/customRequests/deletecart"
import SuccessModal from "./successModal"
import Woodlandheader from "../../components/Header/Header"
import Footer from "../../components/footer/footer"
import PaymentModal from "./paymentModal"

/**
 * Check out page
 * @returns 
 * Functions : save address, save order, delet orders
 */

const OrderCheckout=()=>{
    const navigate = useNavigate()
    const [thirty,setThirty] = useState(false)
    const [isloading,setIsLoading] = useState(true)

    const  [cartdatas,setCartdatas] = useGetcart(localStorage.getItem('cartId'),setIsLoading)

    const [address,setAddress] = useState(null);
    const [saveadd,setSaveadd] = useState(false)
    const [savedAddress,setSavedAddress] = usePostorders('/saveAddress',address,saveadd,setSaveadd);
   
    const [finalOrder,setFinalOrder] = useState(null)
    const [saveOrders,setSaveOrders] = useState(false)
    const [savedOrder,setSavedOrder] = usePostorders('/saveOrder',finalOrder,saveOrders,setSaveOrders)


    const [deleteCart,setDeleteCart] = useState(false)
    const  [deletedCart,setDeletedCart]  = useDeleteCart(deleteCart)

    const [showModal,setShowModal]=useState(false);
    const [showPayment,setShowPayment] = useState(false);
    const [paymentType,setPaymentype] = useState(null);

    const [paymentData,setPaymentData] = useState('')
   const [addPayment,setAddPayment] = useState(false)
   const [savedPayment,setSavedPayment] = usePostorders('/savePayment',paymentData,addPayment,setAddPayment)
   
   

    const closePayment=()=>{
        setShowPayment(false)
    }

    const handleClose = () => {
        setShowModal(false);
    }
    const handleShow = () => {
        setShowModal(true);
    }

    const loadScript=(src)=>{
        return new Promise ((resolve)=>{
            const script = document.createElement('script')
            script.src = src

            script.onload=()=>{
                resolve(true)
            }

            script.onerror=()=>{
                resolve(false)
            }

            document.body.appendChild(script)
        })
    }



    const displayRazorPay=async(amount)=>{
        const res = await loadScript('https://checkout.razorpay.com/v1/checkout.js')
        if(!res){
            errorMessage("Failed Razorpay")
            return
        }


        const options = {
            key : "rzp_test_WW0PEsODcYY89K",
            currency : "INR", 
            amount : amount * 100, 
            name : "Pay with Razorpay",
            description :"Thanks for the purchase", 
            image:"https://www.woodlandworldwide.com/images/h-logo.png",
            handler:function(response){
                // successMessage(response.razorpay_payment_id)
                if(response.razorpay_payment_id){
                    saveAddress();
                    // insert into payment table
                }
                // successMessage("Payment success");          
            },
            prefill : {
                name:"Pay with razorpay"
            }
            };
        const paymentObject = new window.Razorpay(options)
        paymentObject.open()
    }

    useEffect(()=>{
        if(savedPayment!=null && addPayment){
            setAddPayment(false)
        }

    },[savedPayment])

    useEffect(()=>{
        // console.log('dcdceeee',deletedCart,deletedCart)
        if(deleteCart && deletedCart){
            // console.log(deletedCart)
            if(deletedCart){
                // console.log("cart succeess")
                setShowModal(true)
                setDeletedCart(false)
            }
            else{
                // console.log(deletedCart)
                errorMessage("Failed to delete the cart!");
            }
        }
    },[deleteCart,deletedCart])

    useEffect(()=>{
        if(savedOrder!=null && saveOrders){
            if(savedOrder.hasOwnProperty('addressId')){
                setSaveOrders(false)
                setDeleteCart(true)
                // console.log("Order saved !",savedAddress,saveOrder,savedOrder)
                // successMessage("Order Success!")
                // setCartdatas(null);
                if(savedOrder.orderId!=null && savedOrder.orderId>0 && paymentType=="online"){
                    setPaymentData({
                        "paymentStatus":"success",
                        "OrderId":{
                            "orderId":savedOrder.orderId
                        },
                        "orderid":savedOrder.orderId
                    })
                    setAddPayment(true)
                }
            }
            else{
                errorMessage('Failed to place order! try again')
            }
          
        }
    },[savedOrder])
   
    useEffect(()=>{
        if(address!=null){
            // console.log('SA:',savedAddress)
            if(savedAddress!=null){
                if(savedAddress.hasOwnProperty('addressId')){
                    setSaveadd(false)
                    //after the address is saved , save the products now 
                    // console.log(cartdatas,savedAddress)
                    
                    getOrderData()
                }
                else{
                    errorMessage('Failed to save address');

                }
            }
           
        }
       
    },[address,savedAddress])
    
    const firstnameInput = useRef(null)
    const lastnameInput = useRef(null)
    const emailnameInput = useRef(null)
    const addressLineInput = useRef(null)
    const pincodeInput = useRef(null)
    const cityInput = useRef(null)
    const stateInput = useRef(null)
    const landmarkInput = useRef(null)
    const addTypeInput = useState(null)

  
    const getOrderData=()=>{
         let cart=[]
       cart= cartdatas.map((item)=>{
            return(
                {
                    "prodQuantity":item.productQuantity,
                    "newPrice":updatedPrice(item.productPrice,item.productOffers),
                    "size":item.productSize,
                    "prodPicId":{
                        "productPicId":item.productPicId
                    },
                    "prodPicSizeId":{
                      "productPicSizeId":item.productPicSizeId
                    }
                } )
        })
        let month = new Date().getMonth()+1
        if(month<10){
            month=0+month.toString();
        }
        let year = new Date().getFullYear();
        let day = new Date().getDate();
        if(day<10){
            day=0+day.toString();
        }
        let date=year.toString()+"-"+month.toString()+"-"+day.toString()

        let finalOrder =  {
                "orderTotalAmount":computeValue,
                "orderTotalItems":computeItem,
                "orderDonationAmount":thirty? 30:0,
                "orderDeliveryAmount":0,
                "orderDate":date,
                "addressId":savedAddress.addressId,
                "cartDto":cart
        }
        // console.log('OOOOOPPPPPSSSS',cart,finalOrder)
        setFinalOrder(finalOrder)
        setSaveOrders(true)
        // setAddress(null)
        // setSaveadd(false)
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

    const computeValue=useMemo(()=>{
        return parseFloat(computeTotal(cartdatas))
    },[cartdatas])

    const computeItem=useMemo(()=>{
        return computeItems(cartdatas)
    },[cartdatas])

   
    useEffect(()=>{
        if(paymentType!=null && paymentType=="online"){
            displayRazorPay(parseFloat(computeValue) + parseInt((thirty? 30:0)))                                     
        }
        else if(paymentType=="cash"){
            saveAddress()
        }
        
    },[paymentType])
    const handlePayment=()=>{
        setShowPayment(true)
    }
    const validateFiels=()=>{
        if(!validateInput("name",firstnameInput.current.value)){
            errorMessage("Invalid name entered");
        }
        else  if(!validateInput("email",emailnameInput.current.value)){
            errorMessage("Invalid email entered");
        }
        else  if(!validateInput("pincode",pincodeInput.current.value)){
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
            // console.log(addTypeInput.current.value)
            // saveAddress()
            handlePayment()
        }
    }



    const saveAddress=()=>{
        const newAddress= {
                "addressLine":addressLineInput.current.value,
                "pincode":pincodeInput.current.value,
                "city":cityInput.current.value,
                "state":stateInput.current.value,
                "landmark":landmarkInput.current.value,
                "type":addTypeInput.current.value
            }
        // console.log(newAddress)
        setAddress(newAddress);
        setSaveadd(true)  
    }

    const saveOrder=()=>{
        
    }

    const  cartEmpty=()=>{
       if(!isloading){
        errorMessage("Add items to the cart! ")
        navigate('/home')
       }
       
    }


    return (
      <React.Fragment>
        <header>
          <Woodlandheader />
        </header>
        <body>
          {cartdatas != null && cartdatas.length > 0 && !isloading ? (
            <div className="orderCheckout_container">
              <Row className="orderCheckout_header_row">
                <Col className="orderCheckout_header" xs={12}>
                  Checkout
                </Col>
                <Col className="orderCheckout_below" xs={12}>
                  You are almost there!
                </Col>
              </Row>
              <form>
                <Row className="orderdet_address_container">
                  <Col className="orderCheckout_shiiping_header" xs={12} lg={8}>
                    <Row>
                      <Col xs={12} lg={8}>
                        Shipping Address
                      </Col>
                    </Row>

                    <Row>
                      <Col
                        xs={12}
                        lg={12}
                        className="orderCheckout_profile_details"
                      >
                        <Row className="orderChk_input">
                          <Col className="profile_fields" xs={12} lg={3}>
                            <input
                              placeholder="First Name*"
                              onChange={(e) => {
                                updateInputs(e, "firstname");
                              }}
                              required
                              ref={firstnameInput}
                              className="order_chekout_input profile_input"
                            />
                          </Col>

                          <Col className="profile_fields" xs={12} lg={3}>
                            <input
                              onChange={(e) => {
                                updateInputs(e, "lastname");
                              }}
                              placeholder="Last Name"
                              ref={lastnameInput}
                              className="profile_input"
                            />
                          </Col>

                          <Col className="profile_fields" xs={12} lg={6}>
                            <input
                              onChange={(e) => {
                                updateInputs(e, "email");
                              }}
                              placeholder="Email"
                              ref={emailnameInput}
                              className="profile_input"
                            />
                          </Col>

                          <Col xs={12} lg={12}>
                            <Row>
                              <Col className="profile_fields" xs={12} lg={6}>
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

                              <Col xs={12} lg={6}>
                                <Row>
                                  <Col
                                    className="profile_fields"
                                    xs={12}
                                    lg={6}
                                  >
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

                                  <Col
                                    className="profile_fields"
                                    xs={12}
                                    lg={6}
                                  >
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
                                </Row>
                              </Col>
                            </Row>
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

                        <Row className="order_check_box">
                          <Col xs={12} sm={5} md={3}>
                            <input
                              className="add_type_chekbox"
                              onClick={(e) => {
                                // console.log(
                                //   addTypeInput,
                                //   addTypeInput.current.value
                                // );
                                updateInputs(
                                  e,
                                  "addTypeInput",
                                  e.target.checked
                                );
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
                              Home (All day delivery)
                            </label>
                            &nbsp;
                          </Col>
                          <Col xs={12} sm={7} md={5}>
                            <input
                              className="add_type_chekbox"
                              onClick={(e) => {
                                updateInputs(
                                  e,
                                  "addTypeInput",
                                  e.target.checked
                                );
                              }}
                              ref={addTypeInput}
                              type="radio"
                              id="work"
                              name="addType"
                              value="work"
                            />{" "}
                            &nbsp;
                            <label className="add_type_label" for="work">
                              Work (Delivery between 10AM - 6PM)
                            </label>
                            &nbsp;
                          </Col>
                          <Col xs={12} md={8}>
                            <p
                              style={{
                                marginTop: "1%",
                                fontSize: "12px",
                              }}
                            >
                              <input
                                style={{
                                  marginRight: "1em",
                                  textAlign: "center",
                                  accentColor: "rgb(59,111,68)",
                                }}
                                type="checkbox"
                              />
                              <span>
                                I would like to receive updates on the latest
                                products and promotions via email or other
                                channels.
                              </span>
                            </p>
                          </Col>

                          <Col xs={12} md={8}>
                            <Row>
                              <Col xs={9}>
                                <p
                                  style={{
                                    marginTop: "3%",
                                    fontSize: "12px",
                                  }}
                                >
                                  <input
                                    onClick={(e) => {
                                      if (e.target.checked) {
                                        setThirty(true);
                                      } else {
                                        setThirty(false);
                                      }
                                    }}
                                    style={{
                                      marginRight: "1em",
                                      textAlign: "center",
                                      accentColor: "rgb(59,111,68)",
                                    }}
                                    type="checkbox"
                                  />
                                  <span>
                                    ₹ 30 will be added to your transaction as a
                                    Donation
                                  </span>
                                </p>
                              </Col>
                              <Col className="profile_fields" xs={3}>
                                <input
                                  style={{
                                    padding: "1px",
                                    height: "30px",
                                  }}
                                  required
                                  value={thirty ? 30 : 0}
                                  className="order_chekout_input profile_input"
                                />
                              </Col>
                            </Row>
                          </Col>

                          <Col xs={12}>
                            <Row>
                              <Col
                                style={{
                                  paddingLeft: "15px",
                                }}
                                xs={12}
                                sm={3}
                                md={2}
                              >
                                <img src="https://www.woodlandworldwide.com/images/donation.png"></img>
                              </Col>
                              <Col
                                style={{
                                  fontSize: "10px",
                                  textAlign: "left",
                                  justifyContent: "start",
                                  paddingLeft: "15px",
                                }}
                                xs={12}
                                sm={9}
                                md={10}
                              >
                                Your support can help conserve and protect the
                                snow leopard in the Himalaya's.
                                <br></br> Donate to the "High on Himalaya's"
                                campaign by adding ₹ 30 (or more) to your bill.
                                <a
                                  style={{
                                    textDecoration: "noone",
                                  }}
                                  href=""
                                >
                                  Readmore
                                </a>
                              </Col>
                            </Row>
                          </Col>
                        </Row>
                        <br></br>
                        <Row>
                          <Col xs={12}>
                            {cartdatas != null && cartdatas.length > 0
                              ? cartdatas.map((item, index) => {
                                  return (
                                    <CartProduct
                                      product={item}
                                      updateFunction={setCartdatas}
                                      cartDataf={cartdatas}
                                      cartIndex={index}
                                      disableCount={true}
                                    />
                                  );
                                })
                              : ""}
                          </Col>
                        </Row>
                      </Col>
                    </Row>
                  </Col>

                  <Col xs={12} lg={4}>
                    <Row>
                      <Col className="order_summary_header" xs={12}>
                        Order Summary
                      </Col>
                    </Row>
                    <hr />

                    <Row className="order_summary_details">
                      <Col className="order_summary_detailsname" xs={8}>
                        Subtotal
                      </Col>
                      <Col className="order_summary_detailsvalue" xs={4}>
                        ₹{computeTotal(cartdatas)}
                      </Col>
                    </Row>

                    <Row className="order_summary_details">
                      <Col className="order_summary_detailsname" xs={8}>
                        Shipping
                      </Col>
                      <Col className="order_summary_detailsvalue" xs={4}>
                        ₹{0}
                      </Col>
                    </Row>
                    <hr />
                    <Row className="order_summary_details">
                      <Col className="order_summary_detailsname" xs={8}>
                        Donation
                      </Col>
                      <Col className="order_summary_detailsvalue" xs={4}>
                        ₹{thirty ? 30 : 0}
                      </Col>
                    </Row>
                    <hr />
                    <Row className="order_summary_details">
                      <Col xs={12}>Got a promo code?</Col>

                      <Col xs={12}>
                        <Form
                          style={{
                            backgroundColor: "#e0e0e0",
                          }}
                          onSubmit={(e) => {
                            e.preventDefault();
                            // console.log(e);
                          }}
                          className="d-flex search_area"
                        >
                          <Form.Control
                            style={{
                              border: "0px",
                              backgroundColor: "#e0e0e0",
                              textTransform: "uppercase",
                              color: "gray",
                              fontSize: "11px",
                              fontWeight: "500",
                            }}
                            onChange={(e) => {
                              e.preventDefault();
                              // console.log(e);
                            }}
                            placeholder="ENTER PROMO CODE"
                            // onInvalid={()=>{setCustomValidity(' ')}}
                          />

                          <Button
                            style={{
                              border: "0px",
                              backgroundColor: "#e0e0e0",
                            }}
                            type="submit"
                            className="prodinfo_check_button"
                            variant="outline-success"
                          >
                            Apply
                          </Button>
                        </Form>
                      </Col>
                    </Row>
                    <hr />
                    <Row className="orderdert_total">
                      <Col className="orderdert_totalname" xs={8}>
                        Total
                      </Col>
                      <Col className="orderdert_totalvalue" xs={4}>
                        {parseFloat(computeValue) + parseInt(thirty ? 30 : 0)}
                      </Col>
                      <Col className="orderdert_tasdet" xs={12}>
                        inclusive of all gst and other taxes
                      </Col>
                    </Row>
                    <hr />
                    <Row>
                      <Col xs={12}>
                        <Button
                          onClick={(e) => {
                            // console.log(e);
                            // saveOrder();
                            validateFiels();
                          }}
                          style={{
                            backgroundColor: "rgb(59,111,68)",
                            width: "100%",
                            padding: "2%",
                            fontWeight: "590",
                            textTransform: "capitalize",
                          }}
                        >
                          CONTINUE TO PAYMENT
                        </Button>
                      </Col>
                    </Row>
                  </Col>
                </Row>
              </form>
            </div>
          ) : (
            <>{cartEmpty()}</>
          )}
          <SuccessModal show={showModal} handleClose={handleClose} />
          <PaymentModal
            show={showPayment}
            handleClose={closePayment}
            paymentType={setPaymentype}
          />
        </body>
        <footer>
          <Footer />
        </footer>
      </React.Fragment>
    );
}

export default OrderCheckout