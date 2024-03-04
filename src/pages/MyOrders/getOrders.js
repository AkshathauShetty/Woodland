import React, { useState } from "react"
import { Col, Row } from "react-bootstrap"
import useAuthGetrequests from "../../components/customRequests/getreqAuth";
import CartProduct from "../Cart/cartProduct";
import OrderDetails from "./orderDetails";


const Getorders =()=>{

    const [getOrders, setGetorders]= useState(true)
    const [orderedproducts, setOrderedProducts] =useAuthGetrequests('getRecentOrders',getOrders);
    const [orderedPrevproducts, setOrderedPrevProducts] =useAuthGetrequests('getPrevOrders',getOrders);
  
    
    const [state,setState]=useState({
        'recentOrders':true,
        'prevOrders':false
        
    })

    const styles={
        "text-decoration": "underline",
        "color": "green",
        "text-underline-offset": "8px"
        }

    const recentStyle=state.recentOrders? styles:{}
    const prevStyle = state.prevOrders?styles:{}

    return(
        <React.Fragment>
           {
            console.log('op...',orderedproducts)
           }
            <Row>
                <Col xs={4}>
                    <p 
                    onClick={()=>{
                        setState({
                            'recentOrders':true,
                            'prevOrders':false
                        })
                    }}
                     style={recentStyle} className="recent_orders">
                    RECENT ORDERS 
                    </p>
                </Col>
                <Col xs={4}>
                    <p 
                      onClick={()=>{
                        setState({
                            'recentOrders':false,
                            'prevOrders':true
                        })
                    }}
                    style={prevStyle} className="prev_orders">
                    PREVIOUS ORDERS 
                    </p>
                </Col>
            </Row>
            <Row>
                <Col xs={12}>

                    {
                        orderedproducts!=null && orderedproducts.length>0  && state.recentOrders ?                 
                        orderedproducts.map((product)=>{
                            return(
                                <>
                                    <CartProduct
                                        disableRemove={true}
                                        product={product}
                                        disableCount={true}
                                    />
                                    <OrderDetails
                                        product={product}
                                    />   
                                </>
                            )
                         
                        })        
                        :
                        orderedPrevproducts!=null && orderedPrevproducts.length>0  && state.prevOrders? 
                        orderedPrevproducts.map((product)=>{
                            return(
                                <>
                                <CartProduct
                                    disableRemove={true}
                                    product={product}
                                    disableCount={true}
                                />
                                <OrderDetails
                                    product={product}
                                /> 
                                </>
                                
                            )
                        }) 
                        :
                        ''
                    }
                </Col>
            </Row>

        </React.Fragment>
    )
}

export default Getorders