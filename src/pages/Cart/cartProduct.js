import { useCallback, useEffect, useReducer, useRef, useState } from "react";
import { Row,Col, Image } from "react-bootstrap"
import '../../assets/styles/cartStyles.css'

// export const token =  localStorage.getItem(localStorage.getItem('phone'))


const CartProduct=(props)=>{
    const [productInfo,setProdinfo] =useState(props.product.productQuantity);
    const setCatdatas=useRef(props.updateFunction);
    // const [cartdatas,setCartdatas] = useReducer(reducer,props.cartDataf)
    const index=useRef(props.cartIndex);


    
    const offer=useCallback((price,offer)=>{
        return price-(price*offer/100);
    },[props])

    const minusCountSTyle={
        cursor:productInfo<=1? "not-allowed":"pointer"
    }

    const plusCountSTyle={
        cursor:productInfo>=5 || productInfo>=props.product.productOriginalQuantity? "not-allowed":"pointer"
    }

    const updateCount=(e,value)=>{
        switch(value){
            case "minus":
                // {console.log(productInfo)};
                if(productInfo>1){
                    setProdinfo((pval)=>pval-1)  
                    setCatdatas.current({
                        function:"decQty",
                        index:index.current
                    })
                };break;
            case "plus":
                if(productInfo<5 && productInfo<props.product.productOriginalQuantity){
                   
                    setProdinfo((pval)=>pval+1);
                    setCatdatas.current({
                        function:"incQty",
                        index:index.current
                    })
                } else{
                    // console.log('not enough items!')
                }
                break;
            default:return;
        }

    }

    const removeProduct=(e)=>{
        // setCatdata.current(cartdatas)
    //    console.log(setCatdatas)
       setCatdatas.current(
        {
            function:"filter",
            index:index.current
        }
       )
    }

    return(
        <>
            <Row style={{margin:"0px"}}>
                <Col 
                className=" mx-auto mx-sm-2 carousal_products_display"
                    style={{                     
                        "border":"none",
                        "textAlign":"center",
                        "backgroundColor":"#e0e0e0"
                    }}
                xs={3}
                sm={2}
                >
                    <div  style={{"backgroundColor":"#e0e0e0","textAlign":"center",verticalAlign: "middle","lineHeight":"120px"}}>
                            <Image  style={{"mixBlendMode":"darken","height":props.disableCount?"120px":"100px","width":props.disableCount?"120px":""}} className="img img-fluid" src={props.product.productpicUrl}/>
                    </div>
                </Col>
                <Col xs={props.disableCount?9:12} sm={props.disableCount?8:9}>
                    <Row className="cart_infos">
                        <Col
                            className="cart_prodname"
                            style={{
                                fontSize:props.disableCount?"13px":'',
                                textAlign:"start",
                                letterSpacing:props.disableCount?"0.005pt":''
                            }}
                            xs={8} sm={6}>
                                {props.product.productName}
                        </Col>

                        <Col className="cart_price_info"
                            style={{
                                "textAlign":"end",
                                "justifyContent":"end",
                                "fontSize":props.disableCount?"12px":''
                            }}
                            xs={4} sm={6}>
                                {props.disableCount?'':
                                <span className="prdinfo_product_oldprice">
                                ₹{props.product.productPrice}</span>
                                }

                                &nbsp;<span className="prdinfo_product_offer">
                                ₹{offer(props.product.productPrice,props.product.productOffers)}
                                </span>
                        
                            
                        </Col>
                </Row>    

                <Row style={{
                    textAlign:"left",
                    paddingBottom:  props.disableCount?"0px":'',
                    paddingTop: props.disableCount? "0px":'',
                    lineHeight: props.disableCount?"90%":''
                }}>
                    <Col  style={{marginTop:props.disableRemove?"10px":'', marginBottom:props.disableRemove?"5px":''}} xs={12}>
                        <span className="cart_prop_header">Color : </span> 
                        <span className="cart_color_props">{props.product.productColor}</span>
                    </Col>
                    <Col style={{marginTop:props.disableRemove?"10px":'', marginBottom:props.disableRemove?"5px":''}} xs={12}>
                            <span className="cart_prop_header">Size : </span>
                            <span style={{fontSize:"12px", marginRight:"10px", fontFamily:"Raleway"}}>{props.product.productSize}</span>
                            <span className="cart_prop_header">Qty : </span>
                            <span style={{fontSize:"13px",fontFamily:"Raleway"}}>{props.product.productQuantity}</span>
                    </Col>
                </Row>   
               
                {/* <Row style={{
                    textAlign:"left",
                    marginTop:"0px"
                }}>
                        <Col xs={12}>
                            <span className="cart_prop_header">Size : </span>
                            <span style={{fontSize:"12px", marginRight:"10px", fontFamily:"Raleway"}}>{props.product.productSize}</span>
                            <span className="cart_prop_header">Qty : </span>
                            <span style={{fontSize:"13px",fontFamily:"Raleway"}}>{props.product.productQuantity}</span>
                        </Col>
                </Row>  */}
                {/* <br/> */}
                <Row style={{
                    marginTop:"2%"
                }}>
                    <Col xs={8} sm={7} md={props.disableCount?6:5}>

                        {
                            !props.disableCount?
                            <div
                        style={{
                            padding: "5px",
                            fontSize:"14px",
                            fontWeight:"500",
                            "justifyContent":"start",
                            "backgroundColor":"#e0e0e0",
                            "width":"fit-content"
                        }}
                        className="form_field"
                        >
                                <span style={{
                                    "marginRight":"5px"
                                }}> QTY </span> 
                                <span
                                onClick={(e)=>{
                                    updateCount(e,"minus")
                                }
                                }
                                style={minusCountSTyle}
                                > - </span> 
                                <span
                                style={{
                                    "marginRight":"15px",
                                    "marginLeft":"15px"
                                }}>
                                        {/* {productInfo[0].productQuantity} */}
                                        {/* {console.log(productInfo)} */}
                                        {productInfo}

                                </span>
                                <span
                                onClick={(e)=>{
                                    // console.log("+ clicked",e)
                                    updateCount(e,"plus")
                                }
                                }
                                style={plusCountSTyle}>
                                    +
                                </span>

                            </div>
                            :
                            props.disableRemove?
                            ''
                            :
                            <>
                            <Col className="profile_fields" xs={12} >
                                          
                                          <p
                                            style={{
                                              height:"30px",
                                              fontSize:"12px",
                                              verticalAlign:"middle",
                                              lineHeight:"30px",
                                              "padding":"0px",
                                              textAlign:"center"
                                            }} 
                                            >
                                                <img src="https://www.woodlandworldwide.com/images/fast-delivery.png"></img>
                                                &nbsp; <span>Deliver : 3-7 Days</span>
                                            </p>
                                         
                            </Col>
                            </>
                        }
                       
                    </Col>
                    
                    {
                        props.disableRemove?
                        ''
                        :
                        <Col
                        onClick={(e)=>{
                            removeProduct(e)
                        }}
                       style={{
                            "textAlign":"end",
                            "justifyContent":"end",
                            "fontSize":props.disableCount? "12px":'',
                            "lineHeight": "38px",
                            "verticalAlign": "middle",
                            cursor:"pointer"
                        }}
                        xs={4} sm={5}  md={props.disableCount?6:7}>
                        Remove
                    </Col>
                    }
                   
                </Row>


            </Col>
            {
                props.disableRemove?
                ''
                :
                <Col xs={12}>
                    <hr/>
                </Col>
            }
            
            </Row>
            
            
           
        </>
    )

}

export default CartProduct