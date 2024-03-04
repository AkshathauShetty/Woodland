import React, { useEffect, useRef, useState } from "react"
import { useLocation } from "react-router-dom"
import { Button, Card, Col, Form, Row } from "react-bootstrap"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { ToastContainer, toast } from "react-toastify"
import Woodlandheader from "../../components/Header/Header"
import Footer from "../../components/footer/footer"
import useWindowsizes from "../../components/Windowsize"
import Displayproducst from "../../components/components/displayProducts"
import ProductimportInfo from "./productImportinfo"
import ProdinfoShippingdet, { ProdinforProddet } from "./prodinfoShippingDet"
import ProdinfoSizeGuide from "./prodinfoSizeGuide"
import Addtocart from "../Cart/addTocart"
import '../../assets/styles/prodinfo.css'
import useIsvisible from "../../components/components/useIsvisible"
import { errorMessage } from "../../components/methods/computations"

/**
 * Product details page
 * @param {product information} props 
 * @returns Page containg the product details
 */
const ProductInfo=(props)=>{

    const {state} = useLocation()
    const {isSmall,setSmall} = useWindowsizes()
    const [imageIndex,setImageIndex]=useState(0);
    const [productinfoShow,setProductinfoShow]=useState(false)
    const[hideMinus,setHideMinus]=useState(true)
    const [hideShipping,setHideShipping] = useState(true)
    const [sizeGuide,setSizeGuide] = useState(false)
    const [showCart,setShowCart] = useState(false)
    const [productSelection,setProductSelection] = useState({
                                                            size:null,
                                                            qty:1,
                                                           pictureId:state.product.images[0].productPicId,
                                                            sizeId:null
                                                        })

    const [invalidPin,setInvalidPin]=useState(false)
    const pinRegex = /[1-9][0-9]{5,5}/;
    const ref = useRef()
    const isVisible = useIsvisible(ref)

    useEffect(()=>{
      setShowCart(false)
    },[])

    const clearSelection=(pid)=>{
      setProductSelection(
        {
          size:null,
          qty:1,
          pictureId:pid,
          sizeId:null
      }
      )
    }

    const setCustomValidity=(inputValue)=>{
       if(pinRegex.test(inputValue)){
            setInvalidPin(false);
        }
        else{
            setInvalidPin(true);
        }

    }


    const addToCart=()=>{
        if(productSelection.size==null){
          errorMessage('Please select the size!')
        }
        else if(!localStorage.getItem("isLoggedin")){
          errorMessage('Please Login to continue!')
        }
        else{
            setShowCart(true);
        }
    }

    return (
      <React.Fragment>
        <header id="pinfo_header">
          <Woodlandheader />
        </header>
        {
          !isVisible && isSmall>=900 && window.scrollY>100?
          <div className="top_container">
            <Row className="top_container" >
            <Col className="backtotop_button" sm={2} lg={1}>
                <div onClick={()=>{
                  window.scrollTo(0,0)
                }}>
                 <Row >
                  <Col style={{width:"fit-content",textAlign:"end",padding:"0px",paddingTop:"10px"}} lg={6}>
                  <span>⇡</span>&nbsp;
                  </Col>
                  <Col style={{width:"fit-content",padding:"0px",paddingTop:"5px"}} lg={6}>
                    <p>Back <br/>to top</p>
                  </Col>
                 </Row>
                </div>
              </Col>
              <Col sm={2} lg={1}>
                <div className="imgcol_container" >
                <img
                    src={state.product.images[imageIndex].productPicUrl}
                    className="prodinfo-img top_img img-fluid"
                  />
                </div>
              </Col>
              <Col sm={4} lg={3}>
                  <div className="prdinfo_product_name_top">
                    <span>{state.product.description}</span>
                  </div>
                  <p className="prdinfo_product_priceInfo_top">
                        <span className="prdinfo_product_price_top">
                          ₹{state.product.finalPrice}
                        </span>
                        <span className="prdinfo_product_oldprice_top">
                          <del>₹{state.product.price} </del>
                        </span>
                        <span className="prdinfo_product_offer_top">
                          ₹{state.product.offer}% off
                        </span>
                  </p>
              </Col>
              <Col className="size_col_top" lg={2}>
                <p className="sizes_col_top">Size :<span className="size_val_top"> {productSelection.size!=null? productSelection.size : "Select size"} </span></p>    
                <p className="color_col_top">Color : <span className="size_val_top"> {state.product.images[imageIndex].productColor}</span></p>    
              </Col>
              <Col className="addto_cart_top" style={{textAlign:"right"}} lg={5}>
                <span 
                        onClick={() => {
                          addToCart();
                        }}
                        className="addcart_top form_field"
                      >
                        {/* <FontAwesomeIcon icon="fa-solid fa-cart-shopping" style={{color: "#3b6f44",}} /> */}
                        ADD TO CART
                </span>
                &nbsp; &nbsp;
                <span
                      style={{
                        backgroundColor: "#edededd9",
                        padding: "15px",
                        paddingLeft:"25px",
                        "paddingRight":"25px",
                        textAlign: "center",
                        cursor: "pointer",
                      }}
                      className="form_field"
                    >
                      {/* <FontAwesomeIcon icon="fa-regular fa-heart" /> */}
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        height="10"
                        width="10"
                        viewBox="0 0 512 512"
                      >
                        <path d="M225.8 468.2l-2.5-2.3L48.1 303.2C17.4 274.7 0 234.7 0 192.8v-3.3c0-70.4 50-130.8 119.2-144C158.6 37.9 198.9 47 231 69.6c9 6.4 17.4 13.8 25 22.3c4.2-4.8 8.7-9.2 13.5-13.3c3.7-3.2 7.5-6.2 11.5-9c0 0 0 0 0 0C313.1 47 353.4 37.9 392.8 45.4C462 58.6 512 119.1 512 189.5v3.3c0 41.9-17.4 81.9-48.1 110.4L288.7 465.9l-2.5 2.3c-8.2 7.6-19 11.9-30.2 11.9s-22-4.2-30.2-11.9zM239.1 145c-.4-.3-.7-.7-1-1.1l-17.8-20c0 0-.1-.1-.1-.1c0 0 0 0 0 0c-23.1-25.9-58-37.7-92-31.2C81.6 101.5 48 142.1 48 189.5v3.3c0 28.5 11.9 55.8 32.8 75.2L256 430.7 431.2 268c20.9-19.4 32.8-46.7 32.8-75.2v-3.3c0-47.3-33.6-88-80.1-96.9c-34-6.5-69 5.4-92 31.2c0 0 0 0-.1 .1s0 0-.1 .1l-17.8 20c-.3 .4-.7 .7-1 1.1c-4.5 4.5-10.6 7-16.9 7s-12.4-2.5-16.9-7z" />
                      </svg>
                </span>
              </Col>


            </Row>
          </div>
          :
          ''
        }
        <body
          style={{
            paddingLeft: "3%",
            paddingRight: "2%",
          }}
        >       
          <div style={{ textAlign: "left" }}>
            <Row>
              <Col xs={12} sm={6} lg={7}>
                <img
                  src={state.product.images[imageIndex].productPicUrl}
                  height="25%"
                  width="100%"
                  className="img-fluid"
                />
              </Col>

              <Col xs={12} lg={5}>
                <Row>
                  <Col xs={12} sm={12}>
                    <div className="prdinfo_product_name">
                      <p>{state.product.images[imageIndex].productPicName}</p>
                    </div>
                  </Col>
                </Row>
                <Row>
                  <Col xs={12} sm={12}>
                    <p className="prdinfo_product_priceInfo">
                      <span className="prdinfo_product_price">
                        ₹{state.product.finalPrice}
                      </span>
                      <span className="prdinfo_product_oldprice">
                        MRP <del>₹{state.product.price} </del>
                      </span>
                      <span className="prdinfo_product_offer">
                        ₹{state.product.offer}% off
                      </span>
                    </p>
                  </Col>
                </Row>

                <Row>
                  <Col xs={12} sm={12}>
                    <p className="prdinfo_product_taxDetails">
                      <span>Prices include taxes</span>
                    </p>
                  </Col>
                </Row>
                <Row>
                  <Col xs={12} sm={12}>
                    <p style={{ fontWeight: "600" }}>Color</p>
                  </Col>
                </Row>

                <Row>
                  {/* <Col xs={12} sm={12}> */}
                  <p>{state.product.images[imageIndex].productColor}</p>
                  <Row
                    style={{
                      marginLeft: "1%",
                    }}
                  >
                    {
                      state.product.images.map((image, index) => {
                      return (
                        <React.Fragment>
                          <div
                            style={{
                              display: "inline-block",
                              padding: "1%",
                              backgroundColor: "#e0e0e0",
                              marginRight: "1%",
                              height: "80px",
                              width: "80px",
                              border:
                                index == imageIndex
                                  ? "2px solid #0c5423"
                                  : "none",
                            }}
                          >
                            <img
                              onClick={() => {
                                setImageIndex(index);
                                clearSelection(image.productPicId);
                              }}
                              style={{
                                mixBlendMode: "multiply",
                              }}
                              className="prodinfo-img img-fluid"
                              src={image.productPicUrl}
                              width="100%"
                            />
                          </div>
                        </React.Fragment>
                      );
                    })}
                  </Row>
                  {/* </Col>                         */}
                </Row>
                <hr />

                <Row>
                  <Col className="prodinfo_size" xs={6} sm={6}>
                    Size
                  </Col>

                  <Col
                    onClick={() => {
                      setSizeGuide(true);
                    }}
                    style={{
                      textAlign: "end",
                      color: "#3b6f44",
                      cursor: "pointer",
                      "font-size": "13px",
                    }}
                    xs={6}
                    sm={6}
                  >
                    Size Guide
                  </Col>
                </Row>

                <Row className="prodInfo-sizes">
                  {state.product.images[imageIndex].productPicSizesdto.length ==
                  0 ? (
                    <Col
                      xs={2}
                      lg={2}
                      className="prodInfo-sizes"
                      style={{
                        textAlign: "center",
                      }}
                    >
                      <div className="prodinfo-size-notavl">M</div>
                      <p
                        style={{
                          color: "#4f4f4f",
                          fontSize: "10px",
                          fontFamily: "Inter",
                          width: "70px",
                        }}
                      >
                        Sold out
                      </p>
                    </Col>
                  ) : (
                    state.product.images[imageIndex].productPicSizesdto.map(
                      (item, index) => {
                        return item.productPicSizeCount < 1 ? (
                          <Col
                            xs={2}
                            lg={2}
                            className="prodInfo-sizes"
                            style={{
                              textAlign: "center",
                            }}
                          >
                            <div className="prodinfo-size-notavl">
                              {item.productPicSize}
                            </div>
                            <p
                              style={{
                                color: "#4f4f4f",
                                fontSize: "10px",
                                fontFamily: "Inter",
                                width: "70px",
                              }}
                            >
                              Sold out
                            </p>
                          </Col>
                        ) : (
                          <Col
                            xs={2}
                            lg={2}
                            className="prodInfo-sizes"
                            style={{
                              textAlign: "center",
                            }}
                          >
                            <div
                              className="prodinfo-size-avl"
                              style={{
                                backgroundColor:
                                  productSelection.size == item.productPicSize
                                    ? "#3b6f44"
                                    : "white",
                              }}
                            >
                              <p
                                onClick={() => {
                                  setProductSelection({
                                    ...productSelection,
                                    size: item.productPicSize,
                                    sizeId: item.productPicSizeId,
                                  });
                                }}
                              >
                                {item.productPicSize}
                              </p>
                            </div>
                          </Col>
                        );
                      }
                    )
                  )}
                </Row>
                <hr />
                <Row>
                  <Col xs={12}>
                    <p className="prodinfo-freedel-head">Free Delivery</p>
                  </Col>
                  <Col xs={12}>
                    <p className="prodinfo-freedel-det">
                      For orders above ₹1000. Delivery in 3-7 working days.
                    </p>
                  </Col>

                  <Col xs={8}>
                    <Form
                      onSubmit={(e) => {
                        e.preventDefault();
                        setCustomValidity(e.target[0].value);
                      }}
                      className="d-flex search_area"
                    >
                      <Form.Control
                        onChange={(e) => {
                          e.preventDefault();
                        }}
                        type="text"
                        placeholder="Enter Pin"
                        className="me-2 form_field"
                        maxLength="6"
                        aria-label="Search"
                        // onInvalid={()=>{setCustomValidity(' ')}}
                      />

                      <Button
                        type="submit"
                        className="prodinfo_check_button"
                        variant="outline-success"
                      >
                        Check
                      </Button>
                    </Form>
                  </Col>
                  {invalidPin ? (
                    <Col
                      style={{
                        color: "#c10000",
                        fontSize: "14px",
                        fontFamily: "Inter",
                      }}
                      xs={12}
                    >
                      <p>
                        ⚠ Invalid pincode. Please enter a valid 6-digit pincode.
                      </p>
                    </Col>
                  ) : (
                    ""
                  )}
                </Row>
                <br></br>

                {/* 
                    Row containing the Quantity,
                    Add to cart,
                    Wishlist functionality
                 */}
                 {

                 }
                <Row ref={ref}>
                  <Col xs={3}>
                    <div
                      style={{
                        backgroundColor: "#edededd9",
                        paddingLeft: "2%",
                        paddingTop: "9px",
                        paddingBottom: "9px",
                      }}
                      className="form_field"
                    >
                      <span>Qty</span>&nbsp;&nbsp;&nbsp;&nbsp;
                      <select
                        style={{
                          border: "none",
                          backgroundColor: "#edededd9",
                        }}
                      >
                        <option value="1"> 1&nbsp;&nbsp;</option>
                      </select>
                    </div>
                  </Col>

                  <Col xs={5}>
                    <div
                      onClick={() => {
                        addToCart();
                      }}
                      style={{
                        backgroundColor: "#edededd9",
                        padding: "9px",
                        textAlign: "center",
                        cursor: "pointer",
                      }}
                      className="form_field"
                    >
                      {/* <FontAwesomeIcon icon="fa-solid fa-cart-shopping" style={{color: "#3b6f44",}} /> */}
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        height="16"
                        width="18"
                        viewBox="0 0 576 512"
                      >
                        <path
                          fill="#3b6f44"
                          d="M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z"
                        />
                      </svg>
                      ADD TO CART
                    </div>
                  </Col>

                  <Col xs={2}>
                    <div
                      style={{
                        backgroundColor: "#edededd9",
                        padding: "9px",
                        textAlign: "center",
                        cursor: "pointer",
                      }}
                      className="form_field"
                    >
                      {/* <FontAwesomeIcon icon="fa-regular fa-heart" /> */}
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        height="10"
                        width="10"
                        viewBox="0 0 512 512"
                      >
                        <path d="M225.8 468.2l-2.5-2.3L48.1 303.2C17.4 274.7 0 234.7 0 192.8v-3.3c0-70.4 50-130.8 119.2-144C158.6 37.9 198.9 47 231 69.6c9 6.4 17.4 13.8 25 22.3c4.2-4.8 8.7-9.2 13.5-13.3c3.7-3.2 7.5-6.2 11.5-9c0 0 0 0 0 0C313.1 47 353.4 37.9 392.8 45.4C462 58.6 512 119.1 512 189.5v3.3c0 41.9-17.4 81.9-48.1 110.4L288.7 465.9l-2.5 2.3c-8.2 7.6-19 11.9-30.2 11.9s-22-4.2-30.2-11.9zM239.1 145c-.4-.3-.7-.7-1-1.1l-17.8-20c0 0-.1-.1-.1-.1c0 0 0 0 0 0c-23.1-25.9-58-37.7-92-31.2C81.6 101.5 48 142.1 48 189.5v3.3c0 28.5 11.9 55.8 32.8 75.2L256 430.7 431.2 268c20.9-19.4 32.8-46.7 32.8-75.2v-3.3c0-47.3-33.6-88-80.1-96.9c-34-6.5-69 5.4-92 31.2c0 0 0 0-.1 .1s0 0-.1 .1l-17.8 20c-.3 .4-.7 .7-1 1.1c-4.5 4.5-10.6 7-16.9 7s-12.4-2.5-16.9-7z" />
                      </svg>
                    </div>
                  </Col>
                </Row>

                <br></br>

                <Row
                  onClick={() => {
                    setHideMinus(!hideMinus);
                  }}
                  className="prodinfo_details_show"
                >
                  <Col xs={11}>MORE ABOUT THE PRODUCT</Col>
                  {hideMinus ? <Col>+</Col> : <Col>-</Col>}
                </Row>
                <hr />
                {hideMinus ? (
                  ""
                ) : (
                  <>
                    <ProdinforProddet />
                    <hr />
                  </>
                )}
                <Row
                  onClick={() => {
                    setHideShipping(!hideShipping);
                  }}
                  className="prodinfo_details_show"
                >
                  <Col xs={11}>SHIPPING AND RETURNS</Col>
                  {hideShipping ? <Col>+</Col> : <Col>-</Col>}
                </Row>

                <hr></hr>

                {hideShipping ? "" : <ProdinfoShippingdet />}

                <Row>
                  <Col style={{}}>
                    <a
                      className="produinfo_manufacture_details"
                      onClick={() => {
                        setProductinfoShow(true);
                      }}
                    >
                      IMPORT, MANUFACTURING & PACKAGING INFO
                    </a>
                  </Col>
                </Row>

                <br />
              </Col>
            </Row>
          </div>

          <div
            style={{
              paddingLeft: "8%",
              paddingRight: "10%",
            }}
          >
            <Row>
              <Col
                style={{
                  fontSize: "26px",
                  color: "rgb(59,111,68)",
                  marginBottom: "24px",
                  fontWeight: "600",
                  textAlign: "left",
                }}
              >
                Top sellers recommended for you
              </Col>
            </Row>
            <Row>
            {
              state.product.category!=undefined && state.product.for !=undefined?
              <>
                <Displayproducst
                  url={`getProductsByCategory/${state.product.category}/${state.product.for}`}
                />

              </>
                  :
                  <Displayproducst
                  url='getProductsByCategory/Footwear/Men'
                  />

            }
            </Row>
          </div>

          <ProductimportInfo
            show={productinfoShow}
            onHide={() => {
              setProductinfoShow(false);
            }}
          />
          <ProdinfoSizeGuide
            show={sizeGuide}
            onHide={() => {
              setSizeGuide(false);
            }}
          />

          {showCart ? (
            <Addtocart
              productInfo={{
                product: state.product,
                selection: productSelection,
              }}
              show={showCart}
              onHide={() => {
                setShowCart(false);
              }}
            />
          ) : (
            ""
          )}
        </body>

        <footer>
          <Footer />
        </footer>
      </React.Fragment>
    );
}


export default ProductInfo