import React, { useCallback, useEffect, useReducer, useState } from "react"
import { Col, Modal, Offcanvas, OffcanvasBody, OffcanvasHeader, OffcanvasTitle, Row } from "react-bootstrap"
import {Button} from "react-bootstrap"
import { request } from "../../components/variables/variables";
import CartProduct from "./cartProduct";
import { useNavigate } from "react-router-dom";


export const deleteItem=(item)=>{
    const token = localStorage.getItem(localStorage.getItem('phone'))

    request
    .delete('/deletecart',
        {
            
            headers:{
                "Content-Type":"application/json",
                "Access-Control-Allow-Header":"Content-Type",
                "Access-Control-Allow-Origin": "*",
                "Authorization" : `Bearer ${token}`
            },data: {
                cartId:localStorage.getItem('cartId'),
                prodQuantity:item.productQuantity,
                size:item.productSize,
                prodPicId:{
                    productPicId:item.productPicId
                },
                "prodPicSizeId":{
                  "productPicSizeId":item.productPicSizeId
                }
            
            }
        })
    .then((response)=>{
        if(response.status==204){
          //  successMessage("Item deleted from cart successfullly");
        }        
    })
    .catch((error)=>{

    });

}

const reducer =(state,action) =>{

    switch(action.function){
        case "save":return action.data;break;
        case "filter": 
        //console.log('inside the reducer function',action.index);
                        return state.filter((item,index)=> {
                            if(index!=action.index){
                                return item;
                            }
                            else{
                                //console.log("dleeting the item---->",item)
                                deleteItem(item)
                            }
                        }
                  );break;
        case  "incQty": //console.log("inside the increment  updatecount"); 
                        return state.map((item,index)=>{
                            if(index==action.index){
                               // console.log(item)
                              
                            return {...item, 
                                        productQuantity:item.productQuantity+1};
                            }
                            else{
                                return item
                            }
                        });break;
        case  "decQty": //console.log("inside the  updatecount"); 
                        return state.map((item,index)=>{
                            if(index==action.index){
                               // console.log(item)
                            return {...item, 
                                        productQuantity:item.productQuantity-1};
                            }
                            else{
                                return item
                            }
                        });break;
        // case "remove":return state.filter((item,index)=>deleteItem(item));break;
        default: return state;
    }

}


const Addtocart=(props)=>{


    const [productInfo,setProductInfo] = useState(props.productInfo);
    const token =  localStorage.getItem(localStorage.getItem('phone'))
    const [cartdata,setCartdata] = useState([])
    const [cartdatas,setCartdatas] = useReducer(reducer,[])
    const navigate=useNavigate()

    const computeTotal=()=>{
        //console.log(" cart is changing now...',totalAmount.toFixed(2)")
        let totalAmount = 0
        cartdatas.map((item)=>{
            totalAmount=totalAmount+(item.productFinalPrice*item.productQuantity)
        })
        //console.log('ttl mtt',totalAmount.toFixed(2))
        return totalAmount.toFixed(2);

    }
    
    const computeItems=useCallback(()=>{
        let total=0
        cartdatas.map((item)=>{
            total=total+item.productQuantity
        })
        return total;
        
    },[cartdatas])
    
   
    useEffect(()=>{
       // console.log("inside ue of cart")
        if(props.hasOwnProperty('productInfo')){
        //    console.log('yes present',props.productInfo)
            addtoCarts();
        }
        if(localStorage.getItem('cartId')>0){
            getCartDetails(localStorage.getItem('cartId'))
        }
        else{
        //    console.log('not present---- add items to create your cart')
        }
    },[props])

    const updateCart=()=>{
        
        const postData=cartdatas.map((item)=>{
            return {
                    "cartId":localStorage.getItem('cartId'),
                    "prodQuantity":item.productQuantity,
                    "size":item.productSize,
                    "prodPicId":{
                        "productPicId":item.productPicId
                    },  
                "prodPicSizeId":{
                  "productPicSizeId":item.productPicSizeId
              }
            }
        })
        request
        .put('/updateCart',postData,
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",
                    "Authorization" : `Bearer ${token}`
                }
            })
        .then((response)=>{
            if(response.status==200){

            }        
        })
        .catch((error)=>{
          
        });

    }

  

    const getCartDetails=(cartId)=>{
        request
        .get(
            `/displayCart/${cartId}`,
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",
                    "Authorization" : `Bearer ${token}`
                }

            })
        .then((response)=>{
                if(response.status===200){
                  
                    setCartdata(response.data)
                    setCartdatas(
                        {
                            function:"save",
                            data:response.data
                        }
                    )
                }
            })
        .catch(error=>{
            // setError(error)
          //  console.log(error)
            // errorMessage(error)
        });
    }

    const addtoCarts=()=>{
     // console.log('loogging',productInfo)
        request
        .post('/addtoCart', 
            {
                "prodQuantity":1,
                "size":productInfo.selection.size,
                "prodPicId":{
                    "productPicId":productInfo.selection.pictureId
                },
                "prodPicSizeId":{
                  "productPicSizeId":productInfo.selection.sizeId
              },
                "customerid":{
                    "phone":localStorage.getItem('phone')
                }              
            },
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",
                    "Authorization" : `Bearer ${token}`
                }
            })
        .then((response)=>{
            if(response.status==200){
                localStorage.setItem("cartId",response.data.cartId)
                getCartDetails(response.data.cartId)
            }        
        })
        .catch((error)=>{
          // console.log("error----->",error);
        });
    }

    const offcanvastyle={
        "--bs-offcanvas-width":"600px"
    }

    const doCheckout=()=>{
        navigate('/checkout')
        window.scrollTo(0,0)
    }

    return (
      <React.Fragment>
        <Offcanvas style={offcanvastyle} {...props} placement="end">
          <Row>
            <Col xs={2}>
              <OffcanvasHeader
                onClick={(e) => {
                 // console.log("done playing with cart90909");
                 // console.log(cartdatas);
                  updateCart();
                }}
                closeButton
              ></OffcanvasHeader>
            </Col>
            <Col xs={10}>
              <OffcanvasTitle>Your Cart</OffcanvasTitle>
              <p
                style={{
                  fontSize: "12px",
                  paddingBottom: "4%",
                }}
              >
                Never leave your cart empty!
              </p>
            </Col>
          </Row>
          <br />
          <OffcanvasBody
            style={{
              paddingBottom: "20%",
            }}
          >
            {cartdatas.length != 0 ? (
              <div>
                {/* {console.log("++", cartdata)} */}
                {cartdatas.map((item, index) => {
                  return (
                    <>
                      <CartProduct
                        product={item}
                        updateFunction={setCartdatas}
                        cartDataf={cartdatas}
                        cartIndex={index}
                        disableCount={false}
                      />
                      {/* <hr/> */}
                    </>
                    
                  );
                })}

                <Row
                  style={{
                    position: "fixed",
                    bottom: "0px",
                    backgroundColor: "white",
                    paddingBottom: "1%",
                    paddingTop: "2%",
                  }}
                >
                  <Col
                    style={{
                      textAlign: "left",
                      justifyContent: "start",
                    }}
                    xs={8}
                  >
                    SubTotal &nbsp;
                    {computeItems()} Items
                  </Col>

                  <Col
                    style={{
                      justifyContent: "end",
                      textAlign: "end",
                    }}
                    xs={4}
                  >
                    {" "}
                    ₹{computeTotal()}
                  </Col>

                  <Col xs={12}>
                    <Button
                      style={{
                        backgroundColor: "rgb(59,111,68)",
                        width: "100%",
                      }}
                      onClick={() => {
                        updateCart();
                        doCheckout();
                      }}
                    >
                      Checkout
                    </Button>
                  </Col>

                  <Row>
                    <p
                      style={{
                        fontSize: "16px",
                        paddingTop: "10px",
                        paddingBottom: "10px",
                        textAlign: "center",
                        fontWeight: "500",
                      }}
                    >
                      Free shipping on this order!
                    </p>
                  </Row>
                </Row>
              </div>
            ) : (
              <div
                style={{
                  textAlign: "center",
                  fontSize:"26px",
                  "fontWeight":700,
                  color:"rgb(48, 48, 48)"}}
              >
                Your Shopping Cart is Empty
            
              </div>
            )}
          
          </OffcanvasBody>

        </Offcanvas>

       
      </React.Fragment>
    );
}

export default Addtocart